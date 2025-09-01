package com.maximilien0405.capacitorproximity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.Bridge;
import com.getcapacitor.Logger;

public class CapacitorProximity {
    private static final String TAG = "CapacitorProximity";
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    private boolean isProximityMonitoringEnabled = false;
    private Activity activity;
    private PowerManager.WakeLock wakeLock;
    private float originalBrightness = -1f;
    private boolean screenWasOn = true;
    private boolean originalKeepScreenOn = false;

    public CapacitorProximity(Activity activity, Bridge bridge) {
        this.activity = activity;
        
        if (activity != null) {
            sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            }
        }
    }

    // Enable proximity mode & dimm screen when close
    public void enable(PluginCall call) {
        if (activity == null) {
            call.reject("Activity not available, cannot enable proximity sensor");
            return;
        }

        if (proximitySensor == null) {
            call.reject("Proximity sensor not available on this device");
            return;
        }

        if (isProximityMonitoringEnabled) {
            call.resolve();
            return;
        }

        try {
            Window window = activity.getWindow();
            originalBrightness = window.getAttributes().screenBrightness;
            if (originalBrightness < 0) {
                originalBrightness = 0.5f;
            }
            
            // Store original keep screen on state
            originalKeepScreenOn = (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0;
            
            PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            
            // Use different wake lock types based on Android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Android 5.0+ (API 21+) - use proximity wake lock
                try {
                    wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
                } catch (Exception e) {
                    // Fallback to partial wake lock if proximity wake lock fails
                    Logger.warn(TAG, "Proximity wake lock not available, using partial wake lock");
                    wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
                }
            } else {
                // Android 6.0 (API 23) - fallback to basic wake lock
                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
            }
            
            proximitySensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                        float distance = event.values[0];
                        boolean isNear = distance < proximitySensor.getMaximumRange();
                        
                        if (isNear) {
                            dimScreen();
                        } else {
                            restoreScreen();
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Required for older Android versions
                }
            };

            boolean registered = sensorManager.registerListener(
                proximitySensorListener, 
                proximitySensor, 
                SensorManager.SENSOR_DELAY_NORMAL
            );

            if (registered) {
                isProximityMonitoringEnabled = true;
                call.resolve();
            } else {
                call.reject("Failed to register proximity sensor listener");
            }
        } catch (Exception e) {
            Logger.error(TAG, "Error enabling proximity sensor", e);
            call.reject("Failed to enable proximity sensor", e);
        }
    }

    // Disable proximity mode
    public void disable(PluginCall call) {
        if (activity == null) {
            call.reject("Activity not available, cannot disable proximity sensor");
            return;
        }

        if (!isProximityMonitoringEnabled) {
            call.resolve();
            return;
        }

        try {
            restoreScreen();
            if (proximitySensorListener != null && sensorManager != null) {
                sensorManager.unregisterListener(proximitySensorListener, proximitySensor);
                proximitySensorListener = null;
            }
            
            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
                wakeLock = null;
            }
            
            isProximityMonitoringEnabled = false;
            call.resolve();
        } catch (Exception e) {
            Logger.error(TAG, "Error disabling proximity sensor", e);
            call.reject("Failed to disable proximity sensor", e);
        }
    }

    private void dimScreen() {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            
            screenWasOn = (params.flags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0;
            params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Android 6.0+ (API 23+) - use screen brightness
                params.screenBrightness = 0.0f;
            } else {
                // Older versions - use alpha for dimming effect
                params.alpha = 0.1f;
            }
            
            window.setAttributes(params);
        } catch (Exception e) {
            Logger.error(TAG, "Error dimming screen", e);
        }
    }

    private void restoreScreen() {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            
            if (originalBrightness >= 0) {
                params.screenBrightness = originalBrightness;
            }
            
            if (!originalKeepScreenOn) {
                params.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            }
            
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                params.alpha = 1.0f;
            }
            
            window.setAttributes(params);
        } catch (Exception e) {
            Logger.error(TAG, "Error restoring screen", e);
        }
    }
}
