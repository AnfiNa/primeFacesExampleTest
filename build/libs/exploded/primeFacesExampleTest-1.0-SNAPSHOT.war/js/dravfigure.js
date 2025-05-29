function updateShapes(rValue){

    let r = parseFloat(rValue);


    if (isNaN(r) || r < 1 || r > 4) r = 0;
    const rect = document.getElementById('rect');
    rect.setAttribute('width', r * 50);
    rect.setAttribute('height', r * 25);
    rect.setAttribute('y', 250 - r*25);
    rect.setAttribute('x', 250);


    const arc = document.getElementById('arc');
    arc.setAttribute(
        'd',
        `M${250},${250 + r*25} A ${r*25} ${r*25} 90, 0,1 ${250 - r*25} 250 V250 H250`
    );

    const triangle = document.querySelector('[data-name="triangle"]');
    triangle.setAttribute(
        'points',
        `250,250 ${250},${250+r*50} ${250+r*50},${250}`
    );
}

document.addEventListener('DOMContentLoaded', function () {
    const textR = document.getElementById('j_idt7:textR'); // Замените ID на реальный ID сгенерированный JSF
    if (textR) {
        updateShapes(textR.value);
    }
});

