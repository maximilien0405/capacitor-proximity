import { WebPlugin } from '@capacitor/core';
import type { CapacitorProximityPlugin } from './definitions';

export class CapacitorProximityWeb extends WebPlugin implements CapacitorProximityPlugin {
  async enable(): Promise<void> {
    throw new Error('Proximity sensor is not supported on the web.');
  }

  async disable(): Promise<void> {
    throw new Error('Proximity sensor is not supported on the web.');
  }
}