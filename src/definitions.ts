export interface CapacitorProximityPlugin {
  /**
   * Enable the proximity plugin
   */
  enable(): Promise<void>;

  /**
   * Disable the proximity plugin
   */
  disable(): Promise<void>;
}