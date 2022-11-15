const fields = document.querySelector("#fields_to_complete");
const inputs = document.querySelectorAll("#fields_to_complete input");


const expressions =  {
    name: /^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    surname:/^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    dni: /^\d{8,10}$/,
    email:/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{0,})$/i, 
    address_street: /^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    address_number: /^\d{0,5}$/
}

/*Agregar tipo de documento */
const complete_fields = {
    name: false,
    surname: false,
    dni: false,
    email: false,
    address_street: false,
    address_number: false
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
            validation_field(expressions.street, e.target, 'address_street')
            break;
        case "address_number":
            validation_field(expressions.number, e.target, 'address_number')
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
    type_document = document.form.type_document.selectedIndex != 0;
    
    if(complete_fields.name && complete_fields.surname && complete_fields.dni && complete_fields.email && type_document){
        document.getElementById('incomplete_field').classList.remove('message_active');

        //GUARDAR TITULAR
        saveLicenseHolder();
    }else{
        document.getElementById('incomplete_field').classList.add('message_active');
    }

});

async function saveLicenseHolder(){
    let licenseHolderInfo = {
        name: inputs[0].value,
        surname: inputs[1].value,
        type: document.getElementById("type_document").value,
        identification: inputs[2].value,
        email: inputs[3].value,
        birthDate: inputs[4].value,
        addressDto: {
            street: inputs[5].value,
            number: inputs[6].value,
            floor: inputs[7].value,
            department: inputs[8].value,
            locality: inputs[9].value,
            province: inputs[10].value
            },
        bloodType: document.getElementById("type_blood").value,
        rhFactor: document.getElementById("type_rh").value,
        isOrganDonor: document.getElementById("donor").value
    };

    const request = await fetch("http://localhost:8080/api/licenseholder",{
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
};