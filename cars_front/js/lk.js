document.addEventListener( "DOMContentLoaded", function(){
    const get_avatar_url = 'http://localhost:8081/api/avatar/get';
    get_lk_data();
    Timer();
    getAvatar(get_avatar_url);
    document.querySelector(".file-upload").addEventListener('change', upload_avatar);
    document.querySelector(".upload-button").addEventListener('click', () => {
        document.querySelector(".file-upload").click();
    })
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
            let userAuthorities = data["roles"];
            userAuthorities.forEach(role => {
                switch (role["authority"]) {
                    case ('ROLE_MODERATOR'):
                        document.querySelector(".for_buttons").insertAdjacentHTML('afterbegin', '<button class="lk-button" name="lk-moderator">ЛК модератора</button>')
                        document.querySelector('[name="lk-moderator"]').addEventListener("click", () => {document.location=''});
                        break
                    case ('ROLE_ADMIN'):
                        document.querySelector(".for_buttons").insertAdjacentHTML('afterbegin', '<button class="lk-button" name="lk-admin">ЛК администратора</button>')
                        document.querySelector('[name="lk-admin"]').addEventListener("click", () => {document.location=''});
                        break
                    case ('ROLE_USER'):
                        document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="create_ad">Создать заявку</button>');
                        document.querySelector('[name="create_ad"]').addEventListener("click", () => {document.location=''});
                        document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="see_ads">Посмотреть заявки</button>');
                        document.querySelector('[name="see_ads"]').addEventListener("click", () => {document.location=''});
                        break
                }
            })
            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="main">На главную</button>');
            document.querySelector('[name="main"]').addEventListener("click", () => {document.location='../index.html'});
            document.querySelector(".for_buttons").insertAdjacentHTML('beforeend', '<button class="lk-button" name="logout">Выйти</button>');
            document.querySelector('[name="logout"]').addEventListener("click", () => {document.location='logout.html'});
        }
        ;
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
        } else if (response.status == 406) {
            let data = await response.json();
            document.querySelector('.error').innerHTML = data.message;
        } else if (response.status == 417) {
            let data = await response.json();
            document.querySelector('.error').innerHTML = data.message;
        } else if (response.status == 400) {
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