import { WebPlugin } from '@capacitor/core';
import type { CapacitorProximityPlugin } from './definitions';

export class CapacitorProximityWeb extends WebPlugin implements CapacitorProximityPlugin {
  async enable(): Promise<void> {
    console.warn('Proximity sensor not available on web');
  }

  async disable(): Promise<void> {
    console.warn('Proximity sensor not available on web');
  }
}