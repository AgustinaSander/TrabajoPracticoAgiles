document.addEventListener("DOMContentLoaded", function(event) {
    if(!(localStorage.token != undefined && localStorage.idUser != undefined)){
        window.location.href = "/TpAgiles/static/login.html";
    }
    updateStates();
    loadLicenseHolder();
});

async function loadLicenseHolder(){
    let idLicenseHolder = new URLSearchParams(window.location.search).get("id");
    if(localStorage.idUser != null){
        const request = await fetch("http://localhost:8080/api/licenseholder/"+idLicenseHolder,{
            method: 'GET',
            headers: {
            'Content-Type':'application/json; charset=UTF-8',
            'Authorization': localStorage.token
            }
        });

        if(request.ok){
                let licenseholder = await request.json();
                loadLicenseHolderData(licenseholder);
        } else {
            window.location.href = "/TpAgiles/static/login.html";
        }
    } else {
        window.location.href = "/TpAgiles/static/login.html";
    }
}

function loadLicenseHolderData(licenseholder){
    document.getElementById("name").innerHTML = licenseholder.name;
    document.getElementById("surname").innerHTML = licenseholder.surname;
    document.getElementById("identification").innerHTML = licenseholder.identification;
    document.getElementById("type").innerHTML = licenseholder.type;
}

function goToUpdateLicenseHolder(){
    window.location.href = "/TpAgiles/static/modificarTitularUI.html?id="+new URLSearchParams(window.location.search).get("id");
}