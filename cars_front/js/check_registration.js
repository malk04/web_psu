document.addEventListener( "DOMContentLoaded", function(){
    document.getElementById("reg").addEventListener("submit",function(){
        check_registration();
    });
    document.getElementById("username").addEventListener("input", function(){
        check_vvod("username");
    });
    document.getElementById("password").addEventListener("input", function(){
        check_vvod("password");
    });
    document.getElementById("email").addEventListener("input", function(){
        check_simvols();
    });
});

function check_registration(){
    document.getElementById("username-error").innerHTML = "";
    document.getElementById("email-error").innerHTML = "";
    document.getElementById("password-error").innerHTML = "";
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let email = document.getElementById("email").value;
    let registration_data = {
        username: username,
        password: password,
        email: email
    };
    console.log(JSON.stringify(registration_data))
    const url = 'http://localhost:8081/api/auth/signup';

    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(registration_data)
    }).then(async response => {
        if (response.ok) {
            document.getElementById("successful").innerHTML = `<h2>Регистрация прошла успешно!</h2><button id="button" class="bb" onclick="document.location='../index.html'">Войти</button>`;
        } else if (response.status == 400) {
            let data = await response.json()
            let fields = Object.keys(data)
            let errors = Object.values(data)
            for (let i = 0; i < fields.length; i++) {
                check_err(`${fields[i]}-error`, `${errors[i]}`);
            }
        } else if (response.status == 406) {
            let data = await response.json()
            let error = document.getElementById(`${Object.keys(data)[0]}-error`)
            error.innerHTML = Object.values(data)[0];
        }
    })
};

function check_err(p_id, error_text){
    let log_err = document.getElementById(p_id);
    log_err.innerHTML = error_text;
};

function check_vvod(id){
    let input = document.getElementById(id);
    let proverka = new RegExp("^([a-z,A-Z,0-9])$");
    let res = "";
    for (elem of input.value){
        if (proverka.test(elem) && res.length <= 20){
            res += elem;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};

function check_simvols(){
    let input = document.getElementById("email");
    let res = "";
    for (elem of input.value){
        if (res.length <= 20){
            res += elem;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};