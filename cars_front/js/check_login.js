document.addEventListener( "DOMContentLoaded", function(){
    //get_info_from_server();
    document.getElementById("log").addEventListener("submit",function(){
        check_login();
    });
    document.getElementById("username").addEventListener("input", function(){
        check_vvod("username");
    });
    document.getElementById("password").addEventListener("input", function(){
        check_vvod("password");
    });
});

function check_login(){
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let login_data = {
        username: username,
        password: password
    };

    const url = 'http://localhost:8081/api/auth/signin'

    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(login_data)
    }).then (async response => {
        if (response.ok) {
            let data = await response.json()
            sessionStorage.setItem('token', data.message)
            document.location.replace('../html/lk.html')
        } else if (response.status == 401) {
            let log_err = document.getElementById("log-error");
            log_err.innerHTML = 'Неверное имя пользователя или пароль';
        }
        });
};

function check_vvod(id){
    let input = document.getElementById(id);
    let proverka = new RegExp("^([a-z,A-Z,0-9])$");
    let res = "";
    for (element of input.value){
        if (proverka.test(element) && res.length <= 40){
            res += element;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};
