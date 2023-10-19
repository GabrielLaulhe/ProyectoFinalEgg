document.addEventListener('DOMContentLoaded', function () {
    const btnRegistrarse = document.getElementById('btn-registrarse');
    const btnIniciarSesion = document.getElementById('btn-iniciar-sesion');
    const loginForm = document.getElementById('login-form');
    const registroForm = document.getElementById('registro-form');

    btnRegistrarse.addEventListener('click', () => {
        loginForm.classList.remove('form');
        loginForm.classList.add('formulario-oculto');
        registroForm.classList.remove('formulario-oculto');
        registroForm.classList.add('form');
    });

    btnIniciarSesion.addEventListener('click', () => {
        registroForm.classList.remove('form');
        registroForm.classList.add('formulario-oculto');
        loginForm.classList.remove('formulario-oculto');
        loginForm.classList.add('form');
    });
});