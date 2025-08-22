export interface CapacitorProximityPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
