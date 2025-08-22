import Foundation
import UIKit

@objc public class CapacitorProximity: NSObject {
    @objc public func enable() {
        UIDevice.current.isProximityMonitoringEnabled = true
    }

    @objc public func disable() {
        UIDevice.current.isProximityMonitoringEnabled = false
    }
}