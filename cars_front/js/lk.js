document.addEventListener( "DOMContentLoaded", function(){
    document.getElementById("text").addEventListener("input", function(){
        check_simvols();
    });

    const get_avatar_url = 'http://localhost:8081/api/avatar/get';
    get_lk_data();
    Timer();
    getAvatar(get_avatar_url);
    document.querySelector(".file-upload").addEventListener('change', upload_avatar);
    document.querySelector(".upload-button").addEventListener('click', () => {
        document.querySelector(".file-upload").click();
    });
    document.querySelector("#ad").addEventListener("submit",function(){
        sent_ad();
    });

    // popup close
    const popupCloseIcon = document.querySelector('.close_popup');
    if (popupCloseIcon){
        popupCloseIcon.addEventListener('click', function (e){
            popupClose(popupCloseIcon.closest('.popup'));
            e.preventDefault();
        })
    }
});


function get_lk_data() {
    const lk_url = 'http://localhost:8081/api/auth/lk';
    fetch(lk_url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    }).then(async response => {
        if (response.ok) {
            let data = await response.json()
            console.log(data)
            document.getElementById('username').innerHTML = data["username"];
            document.getElementById('visits').innerHTML = data["visits"];
            sessionStorage.setItem("name", data["username"]);
            let userAuthorities = data["roles"];
            userAuthorities.forEach(role => {
                switch (role["authority"]) {
                    case ('ROLE_MODERATOR'):
                        if (!document.querySelector('[name="lk-moderator"]')){
                            document.querySelector(".for_buttons").insertAdjacentHTML('afterbegin', '<button class="lk-button" name="lk-moderator">ЛК модератора</button>')
                            document.querySelector('[name="lk-moderator"]').addEventListener("click", () => {document.location='moder_lk.html'});
                        }
                        break
                    case ('ROLE_ADMIN'):
                        document.querySelector(".for_buttons").insertAdjacentHTML('afterbegin', '<button class="lk-button" name="lk-admin">ЛК администратора</button>')
                        document.querySelector('[name="lk-admin"]').addEventListener("click", () => {document.location='admin_lk.html'});
                        if (!document.querySelector('[name="lk-moderator"]')){
                            document.querySelector(".for_buttons").insertAdjacentHTML('afterbegin', '<button class="lk-button" name="lk-moderator">ЛК модератора</button>')
                            document.querySelector('[name="lk-moderator"]').addEventListener("click", () => {document.location='moder_lk.html'});
                        }
                        if (!document.querySelector('[name="create_ad"]')){
                            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button popup-link" name="create_ad">Создать заявку</button>');
                            document.querySelector('[name="create_ad"]').addEventListener('click', function (e) {
                                popupOpen(document.querySelector('.popup'));
                                e.preventDefault();
                            })
                        }
                        if (!document.querySelector('[name="see_ads"]')){
                            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="see_ads">Посмотреть заявки</button>');
                            document.querySelector('[name="see_ads"]').addEventListener("click", () => {document.location='ads.html'});
                        }
                        break
                    case ('ROLE_USER'):
                        if (!document.querySelector('[name="create_ad"]')){
                            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button popup-link" name="create_ad">Создать заявку</button>');
                            document.querySelector('[name="create_ad"]').addEventListener('click', function (e) {
                                popupOpen(document.querySelector('.popup'));
                                e.preventDefault();
                            })
                        }
                        if (!document.querySelector('[name="see_ads"]')){
                            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="see_ads">Посмотреть заявки</button>');
                            document.querySelector('[name="see_ads"]').addEventListener("click", () => {document.location='ads.html'});
                        }
                        break
                }
            })
            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="main">На главную</button>');
            document.querySelector('[name="main"]').addEventListener("click", () => {document.location='../index.html'});
            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="logout">Выйти</button>');
            document.querySelector('[name="logout"]').addEventListener("click", () => {document.location='logout.html'});
        }
    });
}

let timer_id;
function Timer() {
    GetTime();
    timer_id = setInterval(GetTime, 1000);
}

function GetTime() {
    const lk_url = "http://localhost:8081/api/auth/datetime";
    fetch(lk_url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${sessionStorage.getItem("token")}`,
        },
    }).then(async (response) => {
        if (response.ok) {
            let data = await response.json();
            let data_ = JSON.parse(JSON.stringify(data));
            document.querySelector(".now-time").innerHTML = pad(data_["h"]) + ":" + pad(data["m"]) + ":" + pad(data["s"]);
            document.querySelector(".now-date").innerHTML = pad(data_["d"]) + "." + pad(data["mo"]) + "." + pad(data["y"]);
        }
    });
}

function pad(str) {
    str = str.toString();
    return str.length < 2 ? pad("0" + str) : str;
}

function upload_avatar(){
    const upload_avatar_url = 'http://localhost:8081/api/avatar/upload'
    let image = document.querySelector('.file-upload').files[0];
    let formData = new FormData();
    formData.append('image', image);
    fetch(upload_avatar_url, {
        method: 'PUT',
        body: formData,
        headers: {'Authorization': `Bearer ${sessionStorage.getItem('token')}`}
    }).then(async response => {
        if (response.ok) {
            console.log("ok")
            const get_avatar_url = 'http://localhost:8081/api/avatar/get';
            getAvatar(get_avatar_url);
            document.querySelector('.error').innerHTML = "";
        } else if (response.status === 406) {
            let data = await response.json();
            document.querySelector('.error').innerHTML = data.message;
        } else if (response.status === 417) {
            let data = await response.json();
            document.querySelector('.error').innerHTML = data.message;
        } else if (response.status === 400) {
            document.querySelector('.error').innerHTML = "Максимальный объем 10Мб";
        }
    })
}

function getAvatar(get_avatar_url) {
    fetch(get_avatar_url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    }).then(async response => {
        if (response.ok) {
            let data = await response.json();
            document.querySelector('.profile-pic').setAttribute('src', data.message);
        } else {
            let data = await response.json();
            document.querySelector('.error').innerHTML = data.message;
        }
    })
}

function popupOpen(currentPopup){
    currentPopup.classList.add('open')
}

function popupClose(popupActive){
    document.getElementById("theme-error").innerHTML = "";
    document.getElementById("text-error").innerHTML = "";
    document.getElementById("file-error").innerHTML = "";
    popupActive.classList.remove('open');
    document.ad.reset();
}

function sent_ad(){
    let theme = document.getElementById("theme").value;
    let text = document.getElementById("text").value;
    let file = document.getElementById("file_ad").files[0];
    let formData = new FormData();
    formData.append('theme', theme);
    formData.append('text', text);
    if (file){
        formData.append('file', file);
    }

    const create_url = 'http://localhost:8081/api/ads/create'

    fetch(create_url, {
        method: 'POST',
        headers: {'Authorization': `Bearer ${sessionStorage.getItem('token')}`},
        body: formData
    }).then(async response => {
        if (response.ok) {
            popupClose(document.querySelector('.popup'));
            let data = await response.json();
            alert(data.message);
        } else if (response.status === 400) {
            let data = await response.json();
            let fields = Object.keys(data);
            let errors = Object.values(data);
            for (let i = 0; i < fields.length; i++) {
                let errE = document.getElementById(`${fields[i]}-error`);
                errE.innerHTML = errors[i].toString();
            }
        } else if (response.status === 406) {
            let data = await response.json();
            let errE = document.getElementById('file-error');
            errE.innerHTML = data.error;
        }
    });
}

function check_simvols(){
    let input = document.getElementById("text");
    let res = "";
    for (elem of input.value){
        if (res.length <= 4000){
            res += elem;
        }
    }
    if (res === ""){
        input.value = "";
    } else {
        input.value = res;
    }
};