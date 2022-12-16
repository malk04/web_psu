document.addEventListener( "DOMContentLoaded", function(){
    get_info();
    if (sessionStorage.getItem('token') != '' && sessionStorage.getItem('token') != null){
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

function get_info(){
    const news_url = 'http://localhost:8081/api/news/all'
    fetch(news_url, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    }).then(async response => {
        if (response.ok) {
            let data = await response.json();
            console.log(data);
            for (let i = data.length - 1; i >= 0; i--){
                document.querySelector(".news").innerHTML += createNews(data[i]);
            }
        }
    })
}

function createNews(one_news) {
    let news_html = "";
    if (one_news["filePath"] != null) {
        news_html = `
        <div class="block">
            <img class="image-div" src="${one_news["filePath"]}"/>
            <div class="title">${one_news["title"]}</div>
            <div class="text">${one_news["text"]}</div>
            <div class="footer">
                <div class="fraza">Дата создания/редактирования: </div>
                <div class="date">${one_news["date"]}</div>
            </div>
        </div>`
    } else {
        news_html = `
        <div class="block">
            <img class="image-div"/>
            <div class="title">${one_news["title"]}</div>
            <div class="text">${one_news["text"]}</div>
            <div class="footer">
                <div class="fraza">Дата создания/редактирования: </div>
                <div class="date">${one_news["date"]}</div>
            </div>
        </div>`
    }
    return news_html
}