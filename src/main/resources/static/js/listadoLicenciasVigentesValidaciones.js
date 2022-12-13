document.addEventListener("DOMContentLoaded", function(event) {
    updateStates();
});

document.getElementById("button").addEventListener('click', (e) =>{
    e.preventDefault();
    resetSecondaryFilters();
    searchLicenses();
    showResult();
});

const showResult = () =>{
    document.querySelector('#container_filter').classList.add('container_filter_active');
    document.querySelector('#show_results').classList.add('showResults_active');
}

const inputs = document.querySelectorAll("#fields_to_complete input");

function resetSecondaryFilters(){
    document.getElementById("since").value = "";
    document.getElementById("until").value = "";
    document.getElementById("type_license").value = "SELECCIONE";
    filteredLicenses = null;
}

let licenses;
let filteredLicenses;

async function searchLicenses(){
    let filters = {};
    if(inputs[0].value != "") filters.name = inputs[0].value;
    if(inputs[1].value != "") filters.surname = inputs[1].value;
    if(document.getElementById("type_blood").value != "SELECCIONE") filters.typeBlood = document.getElementById("type_blood").value;
    if(document.getElementById("type_rh").value != "SELECCIONE") filters.typeRh = document.getElementById("type_rh").value;
    if(document.getElementById("donor").value != "SELECCIONE") {
        filters.donor = document.getElementById("donor").value == "SI";
    }

    let request = await fetch("http://localhost:8080/api/licenses",{
        method: 'POST',
        headers: {'Content-Type':'application/json; charset=UTF-8',
                  'Authorization': localStorage.token
        },
        body: JSON.stringify(filters)
    });

    if(request.ok){
        licenses = await request.json();
        filteredLicenses = licenses;
        updateLicenses(licenses);
    }
}


function updateLicenses(licenses){
    let tbody = document.getElementById('list-licenses');
    tbody.innerHTML = '';
    for(let [index, l] of licenses.entries()){
        tbody.innerHTML += `<tr>
           <td>${l.expirationDate}</td>
           <td>${l.licenseHolder.name}</td>
           <td>${l.licenseHolder.surname}</td>
           <td>${l.licenseHolder.type} ${l.licenseHolder.identification}</td>
           <td>${l.licenseHolder.address.street} ${l.licenseHolder.address.number}</td>
           <td>${l.fromDate}</td>
           <td>${l.type.name}</td>
           <td>${l.state}</td>
        </tr>`;
    }
}

let filters = {
    since: "",
    until: "",
    type: ""
}

function filterBySince(since, licensesList){
    return licensesList.filter(l => new Date(l.fromDate).getTime() >= new Date(since).getTime());
}

function filterByUntil(until, licensesList){
    return licensesList.filter(l => new Date(l.expirationDate).getTime() <= new Date(until).getTime());
}

function filterByTypeLicense(type, licensesList){
    return licensesList.filter(l => l.type.name == type);
}

function filterLicenses(){
    filteredLicenses = licenses;
    if(filters.since != ""){
        filteredLicenses = filterBySince(filters.since, filteredLicenses);
    }
    if(filters.until != ""){
        filteredLicenses = filterByUntil(filters.until, filteredLicenses);
    }
    if(filters.type != "" && filters.type != "SELECCIONE"){
        filteredLicenses = filterByTypeLicense(filters.type, filteredLicenses);
    }

    updateLicenses(filteredLicenses);
}

document.getElementById("since").addEventListener("change", () => {
    filters.since = document.getElementById("since").value;
    filterLicenses();
})

document.getElementById("until").addEventListener("change", () => {
    filters.until = document.getElementById("until").value;
    filterLicenses();
})

document.getElementById("type_license").addEventListener("change", () => {
    filters.type = document.getElementById("type_license").value;
    filterLicenses();
})

document.getElementById("goProfile").addEventListener("click",(e)=>{
    e.preventDefault();
    let url ="/TpAgiles/static/modificarUsuarioUI.html?id="+localStorage.idUser;
    window.location.href = url;
});
