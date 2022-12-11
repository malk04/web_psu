document.addEventListener( "DOMContentLoaded", function(){
    if (sessionStorage.getItem('token') != 'null'){
        console.log('hjkhl')
        document.querySelector('header').innerHTML =
            '<div id="auth">\n' +
            '    <button class="auth-button" onclick="document.location=\'html/lk.html\'">Личный кабинет</button>\n' +
            '    <button class="auth-button" onclick="document.location=\'html/logout.html\'">Выйти</button>\n' +
            '</div>'
    } else {
        document.querySelector('header').innerHTML =
            '<div id="auth">\n' +
            '    <button class="auth-button" onclick="document.location=\'html/login.html\'">Войти</button>\n' +
            '    <button class="auth-button" onclick="document.location=\'html/registration.html\'">Зарегистрироваться</button>\n' +
            '</div>'
    }
})