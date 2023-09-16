document.addEventListener('DOMContentLoaded', function () {
    const btnRegistrarse = document.getElementById('btn-registrarse');
    const btnIniciarSesion = document.getElementById('btn-iniciar-sesion');
    const loginForm = document.getElementById('login-form');
    const registroForm = document.getElementById('registro-form');

    btnRegistrarse.addEventListener('click', () => {
        loginForm.classList.remove('formulario-visible');
        loginForm.classList.add('formulario-oculto');
        registroForm.classList.remove('formulario-oculto');
        registroForm.classList.add('formulario-visible');
    });

    btnIniciarSesion.addEventListener('click', () => {
        registroForm.classList.remove('formulario-visible');
        registroForm.classList.add('formulario-oculto');
        loginForm.classList.remove('formulario-oculto');
        loginForm.classList.add('formulario-visible');
    });
});