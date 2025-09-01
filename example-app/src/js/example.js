import { CapacitorProximity } from '@maximilien0405/capacitor-proximity';

window.enableProximity = async () => {
    try {
        await CapacitorProximity.enable();
        console.log('Proximity sensor enabled with screen dimming');
        document.getElementById('status').textContent = 'Proximity sensor enabled - Screen will dim when object is near';
    } catch (error) {
        console.error('Failed to enable proximity sensor:', error);
        document.getElementById('status').textContent = 'Failed to enable proximity sensor';
    }
}

window.disableProximity = async () => {
    try {
        await CapacitorProximity.disable();
        console.log('Proximity sensor disabled');
        document.getElementById('status').textContent = 'Proximity sensor disabled - Screen dimming stopped';
    } catch (error) {
        console.error('Failed to disable proximity sensor:', error);
        document.getElementById('status').textContent = 'Failed to disable proximity sensor';
    }
}