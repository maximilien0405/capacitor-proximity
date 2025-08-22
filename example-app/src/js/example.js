import { CapacitorProximity } from '@maximilien0405/capacitor-proximity';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    CapacitorProximity.echo({ value: inputValue })
}
