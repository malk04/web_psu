document.addEventListener( "DOMContentLoaded",function(){
    remove_session();
});

function remove_session(){
    const logout_url = 'http://localhost:8081/api/auth/logout'
    fetch(logout_url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    }).then(response => {
        if (response.ok) {
            sessionStorage.setItem('token', '');
            document.location.replace('../index.html');
        }
    })
};