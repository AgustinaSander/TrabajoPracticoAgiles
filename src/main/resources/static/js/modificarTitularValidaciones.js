document.addEventListener("DOMContentLoaded", function(event) {
    if(localStorage.token == undefined){
        window.location.href = "/TpAgiles/static/login.html";
    } else {
        loadLicenseHolder();
    }
});

const fields = document.querySelector("#fields_to_complete");
const inputs = document.querySelectorAll("#fields_to_complete input");


const expressions =  {
    name: /^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    surname:/^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    dni: /^\d{8,10}$/,
    email:/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{0,})$/i,
    address_street: /^[a-zA-Z0-9À-ÿ\s]{0,30}$/,
    address_number: /^\d{0,5}$/
}

/*Agregar tipo de documento */
const complete_fields = {
    name: true,
    surname: true,
    dni: true,
    email: true,
    address_street: true,
    address_number: true
}

const validation = (e) => {
    switch(e.target.name){
        case "name":
            validation_field(expressions.name, e.target, 'name');
            break;
        case "surname":
            validation_field(expressions.surname, e.target, 'surname');
            break;
        case "dni":
            validation_field(expressions.dni, e.target, 'dni');
            break;
        case "email":
            validation_field(expressions.email, e.target, 'email')
            break;
        case "address_street":
            validation_field(expressions.address_street, e.target, 'address_street')
            break;
        case "address_number":
            validation_field(expressions.address_number, e.target, 'address_number')
            break;
    }
}

const validation_field = (expressions, input, field) =>{
    if(expressions.test(input.value)){
        document.querySelector(`#field_${field} .error_message`).classList.remove('message_active');
        complete_fields[field] = true;
    }else{
        document.querySelector(`#field_${field} .error_message`).classList.add('message_active');
        complete_fields[field] = false;
    }
}

inputs.forEach((input) => {
    input.addEventListener('blur', validation)
});

fields.addEventListener('submit', (e) =>{
    e.preventDefault();

    let type_document;
    let type_blood;
    let type_rh;
    let donor;
    type_document = document.form.type_document.selectedIndex != 0;
    type_blood = document.form.type_blood.selectedIndex != 0;
    rh = document.form.type_rh.selectedIndex != 0;
    donor = document.form.donor.selectedIndex != 0;

    let birthday = document.getElementById("birthday");
    let field_birthday;
    field_birthday = validation_date(birthday.value);

    function validation_date(date){
        //Se valida que se encuentre entre el 01/01/1870 y el dia actual incluidos
        let partes = date.split('-');
        let fechaDate = new Date(partes[0], partes[1] - 1, partes[2]);
        if(fechaDate >= new Date(1920,0,01) && fechaDate < new Date()){
            document.querySelector("#field_birthday .error_message").classList.remove('message_active');
            return true;
        }
        document.querySelector("#field_birthday .error_message").classList.add('message_active');
        return false;
    }

    if(complete_fields.name && complete_fields.surname && complete_fields.dni && complete_fields.email && type_document &&
        complete_fields.address_street && complete_fields.address_number && type_blood && rh && donor && field_birthday){

        document.getElementById('incomplete_field').classList.remove('message_active');
        //MODIFICAR TITULAR
        updateLicenseHolder();
        loadLicenseHolder();
    }else{
        document.getElementById('incomplete_field').classList.add('message_active');
    }

});

async function updateLicenseHolder(){
    let idLicenseHolder = new URLSearchParams(window.location.search).get("id");
    let licenseHolderInfo = {
        name: inputs[0].value,
        surname: inputs[1].value,
        type: document.getElementById("type_document").value,
        identification: inputs[2].value,
        email: inputs[3].value,
        birthDate: inputs[4].value,
        bloodType: document.getElementById("type_blood").value,
        rhFactor: document.getElementById("type_rh").value,
        isOrganDonor: document.getElementById("donor").value =="SI" ? true : false,
        addressDto: {
            id: localStorage.idAddress,
            street: inputs[5].value,
            number: inputs[6].value
        }
    }
    if(inputs[7].value != undefined) licenseHolderInfo.addressDto.floor = inputs[7].value;
    if(inputs[8].value != undefined) licenseHolderInfo.addressDto.apartment = inputs[8].value;
    if(inputs[9].value != undefined) licenseHolderInfo.addressDto.city = inputs[9].value;
    if(inputs[10].value != undefined) licenseHolderInfo.addressDto.province = inputs[10].value;

    const request = await fetch("http://localhost:8080/api/licenseholder/"+idLicenseHolder,{
        method: 'POST',
        headers: {
            'Content-Type':'application/json; charset=UTF-8'
        },
        body: JSON.stringify(licenseHolderInfo)
    });

    if(request.ok){
        document.getElementById('success_message').classList.add('message_active');
        setTimeout(() =>{
            document.getElementById('success_message').classList.remove('message_active');
        },5000);
        fields.reset();
        loadLicenseHolder();
    } else {
        request.text().then(text => {
            let errorMessage = document.getElementById('error_message');
            errorMessage.innerHTML = '<i class="fa-solid fa-circle-exclamation"></i>' + text;
            errorMessage.classList.add('message_active');
            setTimeout(() =>{
                errorMessage.classList.remove('message_active');
                errorMessage.innerHTML = '';
            },5000);
        });
    }
}

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
    localStorage.idAddress = licenseholder.address.id;
    inputs[0].value = licenseholder.name;
    inputs[1].value = licenseholder.surname;
    inputs[2].value = licenseholder.identification;
    inputs[3].value = licenseholder.email;
    inputs[4].value = licenseholder.birthDate;
    inputs[5].value = licenseholder.address.street;
    inputs[6].value = licenseholder.address.number;
    inputs[7].value = licenseholder.address.floor != undefined ? licenseholder.address.floor : "";
    inputs[8].value = licenseholder.address.apartment != undefined ? licenseholder.address.apartment : "";
    inputs[9].value = licenseholder.address.city != undefined ? licenseholder.address.city : "";
    inputs[10].value = licenseholder.address.province != undefined ? licenseholder.address.province : "";
    document.getElementById("type_blood").value = licenseholder.bloodType;
    document.getElementById("type_rh").value = licenseholder.rhFactor;
    document.getElementById("donor").value = licenseholder.isOrganDonor ? "SI" : "NO";
    document.getElementById("type_document").value = licenseholder.type;
}

document.getElementById("logout").addEventListener("click",(e)=>{
    localStorage.clear();
    window.location.href = "/TpAgiles/static/login.html";
});