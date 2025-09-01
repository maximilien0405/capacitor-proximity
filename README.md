# Capacitor Proximity Plugin

The Capacitor Proximity Plugin allows you to enable and disable the proximity sensor on mobile devices. 

When enabled, this plugin will automatically dim or turn off the screen when an object is close to the device (typically used during phone calls to prevent accidental touches).

## Installation

To install the plugin, run the following commands:

```bash
npm install @maximilien0405/capacitor-proximity
npx cap sync
```

And import it just like this:
```ts
import { CapacitorProximity } from '@maximilien0405/capacitor-proximity';
```

## Usage

### Enable Proximity Sensor

This method enables the proximity sensor and starts monitoring for nearby objects. When an object is detected close to the device, the screen will automatically dim or turn off.

```ts
await CapacitorProximity.enable();
```

### Disable Proximity Sensor

This method disables the proximity sensor and stops monitoring. The screen will be restored to its normal brightness and behavior.

```ts
await CapacitorProximity.disable();
```

## Support

For issues or feature requests, please open an issue on the [GitHub repository](https://github.com/maximilien0405/capacitor-proximity).

## License

This project is licensed under the MIT License. See the LICENSE file for details.
