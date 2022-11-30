document.addEventListener("DOMContentLoaded", function(event) {
    if(localStorage.token == undefined){
        window.location.href = "/TpAgiles/static/login.html";
    }
});