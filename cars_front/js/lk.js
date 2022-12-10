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
            document.getElementById('username').innerHTML = data["username"];
            document.getElementById('visits').innerHTML = data["visits"];
            let userAuthorities = data["roles"];
            switch (userAuthorities) {
                case ('[ROLE_MODERATOR]'):
                    document.querySelector("lk-moderator").removeAttribute('id');
                    document.querySelector("lk-moderator").addEventListener("click", () => {document.location=''});
                    break
                case ('[ROLE_ADMIN]'):
                    document.querySelector("lk-moderator").removeAttribute('id');
                    document.querySelector("lk-moderator").addEventListener("click", () => {document.location=''});
                    document.querySelector("lk-admin").removeAttribute('id');
                    document.querySelector("lk-admin").addEventListener("click", () => {document.location=''});
                    break
            }
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
    console.log(formData);
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