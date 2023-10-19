function mostrarVentana(id) {
    const publicacion = document.getElementById(id);
    const ventanaDifuminada = publicacion.querySelector('#ventana-difuminada');
    ventanaDifuminada.style.display = 'flex';
}

function ocultarVentana(id) {
    const publicacion = document.getElementById(id);
    const ventanaDifuminada = publicacion.querySelector('#ventana-difuminada');
    ventanaDifuminada.style.display = 'none';
}

function mostrarVentana2(id) {
    const publicacion = document.getElementById(id);
    const ventanaDifuminada2 = publicacion.querySelector('#ventana-difuminada2');
    ventanaDifuminada2.style.display = 'flex';
}

function ocultarVentana2(id) {
    const publicacion = document.getElementById(id);
    const ventanaDifuminada2 = publicacion.querySelector('#ventana-difuminada2');
    ventanaDifuminada2.style.display = 'none';
}

function mostrarVentana4() {
    const ventanaDifuminada4 = document.getElementById("ventana-difuminada4");
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'none';
    }
    ventanaDifuminada4.style.display = 'flex';

    localStorage.setItem('ventanaModalAbierta4', 'true');
}

function ocultarVentana4() {
    const ventanaDifuminada4 = document.getElementById('ventana-difuminada4');
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'flex';
    }
    ventanaDifuminada4.style.display = 'none';
    localStorage.removeItem('ventanaModalAbierta4');
}

function mostrarVentana5() {
    const ventanaDifuminada5 = document.getElementById("ventana-difuminada5");
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'none';
    }
    ventanaDifuminada5.style.display = 'flex';

    localStorage.setItem('ventanaModalAbierta', 'true');
}

function ocultarVentana5() {
    const ventanaDifuminada5 = document.getElementById('ventana-difuminada5');
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'flex';
    }
    ventanaDifuminada5.style.display = 'none';
    localStorage.removeItem('ventanaModalAbierta');
}

function mostrarVentana4() {
    const ventanaDifuminada4 = document.getElementById("ventana-difuminada4");
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'none';
    }
    ventanaDifuminada4.style.display = 'flex';

    localStorage.setItem('ventanaModalAbierta4', 'true');
}

function ocultarVentana4() {
    const ventanaDifuminada4 = document.getElementById('ventana-difuminada4');
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'flex';
    }
    ventanaDifuminada4.style.display = 'none';
    localStorage.removeItem('ventanaModalAbierta4');
}

function mostrarVentana5() {
    const ventanaDifuminada5 = document.getElementById("ventana-difuminada5");
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'none';
    }
    ventanaDifuminada5.style.display = 'flex';

    localStorage.setItem('ventanaModalAbierta', 'true');
}

function ocultarVentana5() {
    const ventanaDifuminada5 = document.getElementById('ventana-difuminada5');
    const navBar = document.querySelector(".navbar");
    if (navBar) {
        navBar.style.display = 'flex';
    }
    ventanaDifuminada5.style.display = 'none';
    localStorage.removeItem('ventanaModalAbierta');
}

window.onload = function () {
    const ventanaModalAbierta = localStorage.getItem('ventanaModalAbierta');
    const ventanaModalAbierta4 = localStorage.getItem('ventanaModalAbierta4');

    if (ventanaModalAbierta4 === 'true') {
        mostrarVentana4();
    } else if (ventanaModalAbierta === 'true') {
        mostrarVentana5();
    }
};

