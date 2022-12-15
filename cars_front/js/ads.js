document.addEventListener( "DOMContentLoaded", function(){
    get_info();

    // popup close
    const popupCloseIcon = document.querySelector('.close_popup');
    if (popupCloseIcon){
        popupCloseIcon.addEventListener('click', function (e){
            popupClose(popupCloseIcon.closest('.popup'));
            location.reload();
            e.preventDefault();
        })
    }
});

let ads_arr = [];

function get_info(){
    const user_ads_url = 'http://localhost:8081/api/ads/getMyAllAds'
    fetch(user_ads_url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    }).then(async response => {
        if (response.ok) {
            let data = await response.json();
            if (data.length === 0){
                document.getElementById("all_orders").innerHTML = "Вы не отправили ни одной заявки";
            } else {
                ads_arr = data;
                let table = "<table class='ord' id='ord'><tr><th>Тема заявки</th><th>Текст заявки</th><th>Файл</th><th>Дата подачи</th><th>*</th><th>*</th></tr>";
                for (let i = 0; i < data.length; i++){
                    table += '<tr>';
                    table += '<td>'+data[i]["theme"]+'</td>';
                    table += '<td>'+data[i]["text"]+'</td>';
                    table += '<td>'+data[i]["fileName"]+'</td>';
                    table += '<td>'+data[i]["create_date"]+'</td>';
                    table += '<td><button class="delete" id="'+data[i]["id"]+'">удалить</button></td>';
                    table += '<td>'+'<button class="edit" id="'+data[i]["id"]+'">редактировать</button>'+'</td>';
                    table += '</tr>'
                }
                table += '</table>';
                document.getElementById("orders_table").innerHTML = table;
            };
            AddEventForDelete();
            AddEventForEdit();
        } else if (response.status === 403) {
            document.location.replace('../index.html')
        }
    })
};

function AddEventForDelete(){
    let del_buttons = document.querySelectorAll(".delete");
    del_buttons.forEach(button => {
        button.addEventListener("click", function (e){
            let id = e.target.id;
            let delete_url = `http://localhost:8081/api/ads/delete/${id}`
            fetch(delete_url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${sessionStorage.getItem('token')}`
                }
            }).then(async response => {
                if (response.ok) {
                    alert(`Заявка удалена`)
                    location.reload();
                }
            })
        })
    })
}

function AddEventForEdit(){
    let ed_buttons = document.querySelectorAll(".edit");
    ed_buttons.forEach(button => {
        button.classList.add("popup-link");
        button.addEventListener("click", function (e){
            popupOpen(document.querySelector('.popup'));
            e.preventDefault();
            let id = e.target.id;
            let edit_url = `http://localhost:8081/api/ads/edit/${id}`;
            reestablish_form(id, edit_url);
        })
    })
}

function popupOpen(currentPopup){
    currentPopup.classList.add('open')
}

function popupClose(popupActive){
    popupActive.classList.remove('open');
    document.ad.reset();
    document.getElementById("successful").innerHTML = "";
}

function reestablish_form(id, edit_url){
    let ad = ads_arr.find(a => a.id == id);
    document.querySelector("#theme").value = ad.theme;
    document.querySelector("#text").value = ad.text;
    if (ad["fileName"] != ""){
        document.querySelector("#file-error").innerHTML = 'Вами ранее был добавлен файл "'+ad["fileName"]+'" Если хотите обновить его, прикрепите новый';
    }
    document.querySelector("#ad").addEventListener("submit",function(){
        sent_for_edit(edit_url);
    });
}

function sent_for_edit(edit_url){
    let theme = document.getElementById("theme").value;
    let text = document.getElementById("text").value;
    let file = document.getElementById("file_ad").files[0];
    let formData = new FormData();
    formData.append('theme', theme);
    formData.append('text', text);
    if (file){
        formData.append('file', file);
    }

    fetch(edit_url, {
        method: 'PUT',
        headers: {'Authorization': `Bearer ${sessionStorage.getItem('token')}`},
        body: formData
    }).then(async response => {
        if (response.ok) {
            let data = await response.json()
            document.getElementById('successful').innerHTML = data.message;
        } else if (response.status == 400) {
            let data = await response.json();
            let fields = Object.keys(data);
            let errors = Object.values(data);
            for (let i = 0; i < fields.length; i++) {
                let errE = document.getElementById(`${fields[i]}-error`);
                errE.innerHTML = errors[i].toString();
            }
        }
    })
}