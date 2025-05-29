const ANSWERS = {
    X_ERROR: "Пожалуйста, выберите X",
    Y_ERROR: "Неверный Y или Y не введён!",
    R_ERROR: "Неверный R или R не введён!",
    HIT_COLOUR: {
        "да": "green",
        "нет": "red"
    }
};


function validateInputFromMouse(event) {
    const svg = event.currentTarget;
    const rect = svg.getBoundingClientRect();
    const svgWidth = rect.width;
    const svgHeight = rect.height;
    const r = parseFloat(document.getElementById('j_idt7:textR').value);
    const range = 5
    if (r == null) {
        alert("Пожалуйста, выберите значение R");
        return;
    }

    if (!checkR(r)) {
        alert("Неверное значение R");
        return;
    }

    const x = event.clientX - rect.left - svgWidth / 2;
    const y = svgHeight / 2 - (event.clientY - rect.top);

    const scaledX = Math.round((x / (svgWidth / 2)) * range);
    const scaledY = (y / (svgHeight / 2)) * range;

    console.log(`Clicked coordinates: x = ${scaledX}, y = ${scaledY}`);

    if (!checkX(scaledX)) {
        alert("Координаты находятся за пределами диапазона X");
        return;
    }
    if (!checkY(scaledY)) {
        alert("Координаты находятся за пределами диапазона Y");
        return;
    }
    document.getElementById('j_idt7:textY').value = scaledY.toFixed(2);
    document.getElementById(`j_idt7:x${scaledX}`).click();
    setTimeout(() => {
        document.getElementById('j_idt7:submitButton').click();
    }, 200);
}


function validateButtons() {
    const xValue = parseFloat(document.getElementById('j_idt7:Xval').textContent);
    const yValue = parseFloat(document.getElementById('j_idt7:textY').value);
    const rValue = parseFloat(document.getElementById('j_idt7:textR').value);

    if (checkX(xValue) && checkY(yValue) && checkR(rValue)) {
        setTimeout(() => {
            const tableRows = document.querySelectorAll("#table tbody tr");
            const lastRow = tableRows[0];
            const cell = lastRow.querySelectorAll("td")[3];
            const status = cell.textContent.trim();
            writeDotResult(xValue, yValue, rValue, status);
        }, 400);
    } else {
        alert("Данные не валидны");
    }
}


function checkY(value) {
    return (value >= -4 && value <= 4);
}

function checkR(value) {
    return (value >= 1 && value <= 3);
}

function checkX(value) {
    return [-3, -2, -1, 0, 1, 2, 3, -4, -5].includes(parseFloat(value));
}

function writeDotResult(xResult, yResult, rResult, hit) {
    const plot = document.getElementById('pic');
    const xValue = 250 + (xResult * 50);
    const yValue = 250 - (yResult * 50);
    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    point.setAttribute("cx", xValue);
    point.setAttribute("cy", yValue);
    point.setAttribute("r", 3);
    point.setAttribute("fill", ANSWERS.HIT_COLOUR[hit]);

    plot.appendChild(point);

}
document.querySelector("svg")
    .addEventListener("click", (event) => validateInputFromMouse(event));