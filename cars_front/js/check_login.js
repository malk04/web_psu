$('document').ready(function(){
    //get_info_from_server();
    $(document.log).submit(function(){
        check_login();
    });
    document.getElementById("login").addEventListener("input", function(){
        check_vvod("login");
    });
    document.getElementById("password").addEventListener("input", function(){
        check_vvod("password");
    });
});

function check_login(){
    let login = document.getElementById("login").value;
    let password = document.getElementById("password").value;
    let xnr = new XMLHttpRequest();
    xnr.open("POST", "php/check_login.php");
    xnr.onload = function(){
        console.log(xnr.responseText);
        errors = JSON.parse(xnr.responseText);
        error(errors);
        if (errors['successful']){
            window.location.replace('html/lk.html');
        };
    };
    let login_data = {
        login: login,
        password: password
    };
    xnr.send(JSON.stringify(login_data));
};

function error(data){
    let log_err = document.getElementById("log-error");
    if (data['login_err'] != ""){
        log_err.innerHTML = data['login_err'];
    };
    if (data['password_err'] != ""){
        log_err.innerHTML = data['password_err'];
    }; 
};

function check_vvod(id){
    let input = document.getElementById(id);
    let proverka = new RegExp("^([a-z,A-Z,0-9])$");
    let res = "";
    for (element of input.value){
        if (proverka.test(element) && res.length <= 20){
            res += element;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};

function get_info_from_server(){
    let xnr = new XMLHttpRequest();
    xnr.open("POST", "php/login.php");
    xnr.onload = function(){
        let values = JSON.parse(xnr.responseText);
        document.getElementById("login").setAttribute("value", values["login_val"]);
        document.getElementById("password").setAttribute("value", values["password_val"]);
    };
    xnr.send();
};

