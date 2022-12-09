document.addEventListener( "DOMContentLoaded", function(){
    get_lk_data();
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
            console.log(data);
            document.getElementById('username').innerHTML = data["username"];
            document.getElementById('visits').innerHTML = data["visits"];
            let userAuthorities = data["roles"];
            switch (userAuthorities) {
                case ('[ROLE_MODERATOR]'):
                    document.querySelector("lk-moderator").removeAttribute('id');
                    break
                case ('[ROLE_ADMIN]'):
                    document.querySelector("lk-moderator").removeAttribute('id');
                    document.querySelector("lk-admin").removeAttribute('id');
                    break
            }
        }
        ;
    });
}