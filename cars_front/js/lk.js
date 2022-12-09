$('document').ready(function(){
    get_login();
    $(document.zakaz).submit(function(){
        check_ord();
    });
    document.getElementById("tema").addEventListener("input", function(){
        check_tema();
    });
    document.getElementById("wishes").addEventListener("input", function(){
        check_wishes();
    });
    document.getElementById("guests").addEventListener("input", function(){
        check_guests();
    });
});

function check_ord(){
    let tema = document.getElementById("tema").value;
    let guests = document.getElementById("guests").value;
    let date = document.getElementById("date").value;
    let pay1 = document.getElementById("pay1").checked;
    let pay2 = document.getElementById("pay2").checked;
    let wishes = document.getElementById("wishes").value;
    let xnr = new XMLHttpRequest();
    xnr.open("POST", "../php/check_order.php");
    xnr.onload = function(){
        console.log(xnr.responseText);
        errors = JSON.parse(xnr.responseText);
        err(errors);
        if (errors['successful']){
            document.getElementById("order-error").innerHTML = "Ваша заявка принята!";
        };
    };
    let zakaz_data = {
        tema: tema,
        guests: guests,
        date: date,
        pay1: pay1,
        pay2: pay2,
        wishes: wishes
    };
    xnr.send(JSON.stringify(zakaz_data));
}

function err(data){
    let g_err = document.getElementById("guests-error");
    g_err.innerHTML = data["guests_err"];
    let d_err = document.getElementById("date-error");
    d_err.innerHTML = data["date_err"];
    let o_err = document.getElementById("order-error");
    o_err.innerHTML = data["order_err"];
};

function check_tema(){
    let input = document.getElementById("tema");
    let proverka = new RegExp("^[^A-z]+$");
    let res = "";
    for (element of input.value){
        if (proverka.test(element) && (res.length <= 50)){
            res += element;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};

function check_wishes(){
     let input = document.getElementById("wishes");
    let proverka = new RegExp("^[^A-z]+$");
    let res = "";
    for (element of input.value){
        if (proverka.test(element) && (res.length <= 100)){
            res += element;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};

function check_guests(){
    let input = document.getElementById("guests");
    let proverka = new RegExp("^[0-9]$");
    let res = "";
    for (element of input.value){
        if (proverka.test(element) && (res.length <= 10)){
            res += element;
        };
    };
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};

function get_login(){
    let xnr = new XMLHttpRequest();
    xnr.open("POST", "../php/lk.php");
    xnr.onload = function(){
        let data = JSON.parse(xnr.responseText);
        if (!data["successful"]){
            window.location.replace('../index.html')
        } else {
            document.getElementById("login_lk").innerHTML = '<span id="log">'+data["login"]+'</span>';
        };
    };
    xnr.send();
};