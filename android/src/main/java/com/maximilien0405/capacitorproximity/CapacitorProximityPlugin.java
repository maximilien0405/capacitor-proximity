package com.maximilien0405.capacitorproximity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorProximity")
public class CapacitorProximityPlugin extends Plugin {

    private CapacitorProximity capacitorProximity;

    @Override
    public void load() {
        super.load();
        capacitorProximity = new CapacitorProximity(getActivity(), getBridge());
    }

    @PluginMethod
    public void enable(PluginCall call) {
        capacitorProximity.enable(call);
    }

    @PluginMethod
    public void disable(PluginCall call) {
        capacitorProximity.disable(call);
    }
}
