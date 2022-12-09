$('document').ready(function(){
    get_info();
});

function get_info(){
    let xnr = new XMLHttpRequest();
    xnr.open("POST", "../php/orders.php");
    xnr.onload = function(){
        let data = JSON.parse(xnr.responseText);
        if (!data["successful"]){
            window.location.replace('../index.html')
        } else {
            document.getElementById("login_lk").innerHTML = '<span id="log">'+data["login"]+'</span>'; 
            if (data["data"].length == 0){
                document.getElementById("all_orders").innerHTML = "У вас нет заказанных мероприятий";
            } else {
                let table = "<table class='ord' id='ord'><tr><th>Номер заказа</th><th>Тема мероприятия</th><th>Количество гостей</th><th>Дата мероприятия</th><th>Тип оплаты</th><th>Пожелания</th></tr>";
                for (let i = 0; i < data["data"].length; i++){
                    table += '<tr>';
                    table += '<td>'+data["data"][i]["number"]+'</td>';
                    table += '<td>'+data["data"][i]["tema"]+'</td>';
                    table += '<td>'+data["data"][i]["guests"]+'</td>';
                    table += '<td>'+data["data"][i]["date"]+'</td>';
                    if (data["data"][i]["pay1"]){
                        table += '<td>Банковская карта</td>';
                    } else{
                        table += '<td>Наличные</td>';
                    };
                    table += '<td>'+data["data"][i]["wishes"]+'</td>';
                    table += '</tr>'
                }
                table += '</table>';
                document.getElementById("orders_table").innerHTML = table;
            };
        };
    };
    xnr.send();
};