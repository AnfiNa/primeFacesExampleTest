function updateTime() {
    const timeElement = document.getElementById('time');
    const dateElement = document.getElementById('date');

    const now = new Date();
    timeElement.innerHTML =  now.toLocaleTimeString('ru-RU') ;
    dateElement.innerHTML = now.toLocaleDateString('ru-RU', { weekday: 'long', month: 'short', day: 'numeric' });
}


updateTime();

setInterval(updateTime, 11000);