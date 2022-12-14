document.addEventListener("DOMContentLoaded", function(event) {
    if(!(localStorage.token != undefined && localStorage.idUser != undefined)){
        window.location.href = "/TpAgiles/static/login.html";
    }
    updateStates();
    loadLicenseHolder();
});

let licenses;
let idLicenseHolder;

async function loadLicenseHolder(){
    idLicenseHolder = new URLSearchParams(window.location.search).get("id");
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
            window.location.href = "/TpAgiles/static/buscarTitularUI.html";
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

    loadRecords();
}

function goToUpdateLicenseHolder(){
    window.location.href = "/TpAgiles/static/modificarTitularUI.html?id="+idLicenseHolder;
}

async function loadRecords(){
    let request = await fetch("http://localhost:8080/api/licenses/"+idLicenseHolder,{
        method: 'GET',
        headers: {'Content-Type':'application/json; charset=UTF-8',
                  'Authorization': localStorage.token
        }
    });

    if(request.ok){
        licenses = await request.json();
        showLicenses(licenses);
    }
}

function doCopyLicense(idLicense){
    let license = licenses.find(l => l.id == idLicense);
    createPDF(license);
    $('#detailModal').modal('show');
}

async function expireLicense(idLicense){
    const request = await fetch("http://localhost:8080/api/expire",{
        method: 'POST',
        headers: {
            'Content-Type':'application/json; charset=UTF-8'
        },
        body: idLicense
    });
    if(request.ok){
        $('#expireModal').modal('show');
    }
}

function goToRenew(idLicense){
    $('#renewModal').modal('show');
    let license = licenses.find(l => l.id == idLicense);
    if(license.state == "VIGENTE"){
        document.getElementById("modifyBtn").addEventListener('click', ()=>{
            $('#renewModal').modal('hide');
            expireLicense(idLicense);
        })
    } else {
        window.location.href = "/TpAgiles/static/emitirLicenciaUI.html";
    }

}

function showLicenses(licenses){
    let list = document.getElementById("list-licenses");
    list.innerHTML = "";
    for(let [index, l] of licenses.entries()){
        list.innerHTML += `<tr>
                        <td>${l.type.name}</td>
                        <td>${l.state}</td>
                        <td>${l.comments}</td>
                        <td>${l.fromDate}</td>
                        <td>${l.expirationDate}</td>
                        <td>$ ${l.cost}</td>
                        <td style="text-transform: lowercase;">${l.user.email}</td>
                        <td>
                            <button class="btn btn-primary" onclick="goToRenew(`+l.id+`)">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-primary" onclick="doCopyLicense(`+l.id+`)">
                                <i class="fa-solid fa-print"></i>
                            </button>
                        </td>
                    </tr>`;
    }

}

document.getElementById("goProfile").addEventListener("click",(e)=>{
    e.preventDefault();
    let url ="/TpAgiles/static/modificarUsuarioUI.html?id="+localStorage.idUser;
    window.location.href = url;
});
