document.addEventListener( "DOMContentLoaded", function(){
    AddEventForCreate();
    check_simvols();
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

let news_arr = [];

function get_info(){
    const user_ads_url = 'http://localhost:8081/api/news/all'
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
                document.getElementById("all_orders").innerHTML = "Новостей нет";
            } else {
                news_arr = data;
                let table = "<table class='ord' id='ord'><tr><th>Заголовок</th><th>Содержание</th><th>Изображение</th><th>Дата создания/ редактирования</th><th>*</th><th>*</th></tr>";
                for (let i = data.length - 1; i >= 0; i--){
                    table += '<tr>';
                    table += '<td>'+data[i]["title"]+'</td>';
                    table += '<td>'+data[i]["text"]+'</td>';
                    if (data[i]["fileName"] != null){
                        table += '<td>'+data[i]["fileName"]+'</td>';
                    } else {
                        table += '<td>—</td>';
                    }
                    table += '<td>'+data[i]["date"]+'</td>';
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

function AddEventForDeleteImage(id){

}

function AddEventForDelete(){
    let del_buttons = document.querySelectorAll(".delete");
    del_buttons.forEach(button => {
        button.addEventListener("click", function (e){
            let id = e.target.id;
            let delete_url = `http://localhost:8081/api/news/delete/${id}`
            fetch(delete_url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${sessionStorage.getItem('token')}`
                }
            }).then(async response => {
                if (response.ok) {
                    alert(`Новость удалена`)
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
            let edit_url = `http://localhost:8081/api/news/edit/${id}`;
            reestablish_form(id, edit_url);
        })
    })
}

function AddEventForCreate(){
    document.querySelector(".create-news").addEventListener("click", function (e){
        popupOpen(document.querySelector('.popup'));
        e.preventDefault();
        reestablish_form_for_create();
    })
}

function popupOpen(currentPopup){
    currentPopup.classList.add('open')
}

function popupClose(popupActive){
    document.getElementById("title-error").innerHTML = "";
    document.getElementById("text-error").innerHTML = "";
    document.getElementById("image-error").innerHTML = "";
    popupActive.classList.remove('open');
    document.news.reset();
    reestablish_form_for_create();
}

function reestablish_form_for_create (){
    document.querySelector(".popup_title").innerHTML = "Создание новости"
    document.querySelector("#news").addEventListener("submit",function(){
        sent_for_create();
    });
}

function reestablish_form(id, edit_url){
    document.querySelector(".popup_title").innerHTML = "Редактирование новости"
    let news = news_arr.find(n => n.id == id);
    document.querySelector("#title").value = news.title;
    document.querySelector("#text").value = news.text;
    if (news["fileName"] != null){
        document.querySelector("#image-error").innerHTML = 'Вами ранее было добавлено изображение "'+news["fileName"]+'" Если хотите обновить его, прикрепите новый';
        document.querySelector("#delete-image").classList.remove("none");
        document.querySelector("#delete-image").addEventListener('click', function (e){
            e.preventDefault()
            let delete_image_url = `http://localhost:8081/api/news/delete_image/${id}`

            fetch(delete_image_url, {
                method: 'PUT',
                headers: {'Authorization': `Bearer ${sessionStorage.getItem('token')}`}
            }).then(async response => {
                if (response.ok) {
                    document.querySelector("#delete-image").classList.add("none");
                    document.querySelector("#image-error").innerHTML = "";
                    let data = await response.json()
                    document.getElementById('successful').innerHTML = data.message;
                }
            })
        })
    }
    document.querySelector("#news").addEventListener("submit",function(){
        sent_for_edit(edit_url);
    });
}

function sent_for_edit(edit_url){
    let title = document.getElementById("title").value;
    let text = document.getElementById("text").value;
    let file = document.getElementById("file_image").files[0];
    let formData = new FormData();
    formData.append('title', title);
    formData.append('text', text);
    if (file){
        formData.append('image', file);
    }

    fetch(edit_url, {
        method: 'PUT',
        headers: {'Authorization': `Bearer ${sessionStorage.getItem('token')}`},
        body: formData
    }).then(async response => {
        if (response.ok) {
            popupClose(document.querySelector('.popup'));
            let data = await response.json()
            alert(`${data.message}`);
            location.reload();
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
            let errE = document.getElementById('image-error');
            errE.innerHTML = data.error;
        }
    })
}

function sent_for_create(){
    let create_url = 'http://localhost:8081/api/news/create';

    let title = document.getElementById("title").value;
    let text = document.getElementById("text").value;
    let file = document.getElementById("file_image").files[0];
    let formData = new FormData();
    formData.append('title', title);
    formData.append('text', text);
    if (file){
        formData.append('image', file);
    }

    fetch(create_url, {
        method: 'POST',
        headers: {'Authorization': `Bearer ${sessionStorage.getItem('token')}`},
        body: formData
    }).then(async response => {
        if (response.ok) {
            popupClose(document.querySelector('.popup'));
            let data = await response.json()
            alert(`${data.message}`);
            location.reload();
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
            let errE = document.getElementById('image-error');
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
    if (res == ""){
        input.value = "";
    } else {
        input.value = res;
    }
};