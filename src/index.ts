import { registerPlugin } from '@capacitor/core';

import type { CapacitorProximityPlugin } from './definitions';

const CapacitorProximity = registerPlugin<CapacitorProximityPlugin>('CapacitorProximity', {
  web: () => import('./web').then((m) => new m.CapacitorProximityWeb()),
});

export * from './definitions';
export { CapacitorProximity };
