import { WebPlugin } from '@capacitor/core';

import type { CapacitorProximityPlugin } from './definitions';

export class CapacitorProximityWeb extends WebPlugin implements CapacitorProximityPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
