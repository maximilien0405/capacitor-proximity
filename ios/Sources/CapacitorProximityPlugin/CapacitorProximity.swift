import Foundation
import UIKit

@objc public class CapacitorProximity: NSObject {
    @objc public func enable() {
        DispatchQueue.main.async {
            UIDevice.current.isProximityMonitoringEnabled = true
            if !UIDevice.current.isProximityMonitoringEnabled {
                print("Proximity sensor not supported on this device")
            }
        }
    }

    @objc public func disable() {
        DispatchQueue.main.async {
            UIDevice.current.isProximityMonitoringEnabled = false
        }
    }
}