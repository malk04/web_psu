$('document').ready(function(){
    get_sess();
});

function get_sess(){
    let xnr = new XMLHttpRequest();
    xnr.open("POST", "../php/logout.php");
    xnr.onload = function(){
        if (!xnr.responseText){
            window.location.replace('index.html')
        } else {
            setTimeout(() => {
                window.location.replace('../index.html');
            }, 100);
        };
    };
    xnr.send();
};