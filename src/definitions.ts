export interface CapacitorProximityPlugin {
  enable(): Promise<void>;
  disable(): Promise<void>;
}