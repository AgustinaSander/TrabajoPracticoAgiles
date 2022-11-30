document.addEventListener("DOMContentLoaded", function(event) {
    if(localStorage.token == undefined){
        window.location.href = "/TpAgiles/static/login.html";
    }
});

const fields = document.querySelector("#fields_to_complete");
const inputs = document.querySelectorAll("#fields_to_complete input");

document.getElementById("button").addEventListener('click', (e) =>{
    e.preventDefault();
    searchLicenseHolders();
});

async function searchLicenseHolders(){
    let filters = {};
    if(inputs[0].value != "") filters.name = inputs[0].value;
    if(inputs[1].value != "") filters.surname = inputs[1].value;
    if(document.getElementById("type_document").value != "SELECCIONE") filters.type = document.getElementById("type_document").value;
    if(inputs[2].value != "") filters.identification = inputs[2].value;

    const request = await fetch("http://localhost:8080/api/licenseholders",{
        method: 'POST',
        headers: {'Content-Type':'application/json; charset=UTF-8'},
        body: JSON.stringify(filters)
    });

    if(request.ok){
        let licenseholders = await request.json();
        updateLicenseHoldersList(licenseholders);
    }
}

function updateLicenseHoldersList(licenseholders){
    let tbody = document.getElementById('list-licenseholders');
    tbody.innerHTML = '';
    for(const [index, l] of licenseholders.entries()){
        tbody.innerHTML += `<tr>
           <td><input type="radio" name="radio_holders" onclick="selectLicenseHolder(`+l.id+`)"></input></td>
           <td>${l.name}</td>
           <td>${l.surname}</td>
           <td>${l.type}</td>
           <td>${l.identification}</td>
       </tr>`;
    }

}

async function selectLicenseHolder(idLicenseHolder){

    //CARGAR OPCIONES
    const request = await fetch("http://localhost:8080/api/licenseholder/types/"+idLicenseHolder,{
            method: 'GET',
            headers: {'Content-Type':'application/json; charset=UTF-8'},
    });

    if(request.ok){
        let licenseTypes = await request.json();
        
        if(licenseTypes.length == 0){
            document.getElementById("emitir-container").style.display = "none";
            document.getElementById("error_message").style.display = "block";
        }
        else{
            document.getElementById("emitir-container").style.display = "block";
            document.getElementById("error_message").style.display = "none";
            let selectTypes = document.getElementById("type_license");
            selectTypes.innerHTML = "";
            for(l of licenseTypes){
                selectTypes.innerHTML += `<option value="${l.id}">CLASE ${l.name}</option>`
            }

            defineEmitEvent(idLicenseHolder);
        }
    }
}

document.getElementById("logout").addEventListener("click",(e)=>{
    localStorage.clear();
    window.location.href = "/TpAgiles/static/login.html";
});

function defineEmitEvent(idLicenseHolder){
    document.getElementById("buttonEmitir").addEventListener("click",(e)=>{
        let checkboxs = document.querySelectorAll(".checkboxGroup input");
        let message = ""
        for(c of checkboxs){
            if(c.checked){
                let msg = "/ "+ c.value;
                message += msg;
            }
        }

        let licenseInfo = {
            idUser: localStorage.idUser,
            idLicenseHolder: idLicenseHolder,
            nameTypeLicense: document.getElementById("type_license").value,
            comments: message
        }

        emitLicense(licenseInfo);

    });
}

async function emitLicense(licenseInfo){
    const request = await fetch("http://localhost:8080/api/license",{
            method: 'POST',
            headers: {
                'Content-Type':'application/json; charset=UTF-8'
            },
            body: JSON.stringify(licenseInfo)
    });

    if(request.ok){
        document.getElementById('success_message').style.display="block";
        setTimeout(() =>{
            document.getElementById('success_message').style.display="none";
        },5000);
        fields.reset();
    }
}

document.getElementById("goProfile").addEventListener("click",(e)=>{
    e.preventDefault();
    let url ="/TpAgiles/static/modificarUsuarioUI.html?id="+localStorage.idUser;
    window.location.href = url;
});