import Foundation
import Capacitor

/**
 * Capacitor Proximity Plugin
 * Provides enable/disable functions for proximity monitoring
 */
@objc(CapacitorProximityPlugin)
public class CapacitorProximityPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorProximityPlugin"
    public let jsName = "CapacitorProximity"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "enable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disable", returnType: CAPPluginReturnPromise)
    ]

    private let implementation = CapacitorProximity()

    @objc func enable(_ call: CAPPluginCall) {
        implementation.enable()
        call.resolve()
    }

    @objc func disable(_ call: CAPPluginCall) {
        implementation.disable()
        call.resolve()
    }
}