document.addEventListener("DOMContentLoaded", function(event) {
    loadUser();
});

const fields = document.querySelector("#fields_to_complete");
const inputs = document.querySelectorAll("#fields_to_complete input");


const expressions =  {
    name: /^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    surname:/^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    dni: /^\d{8,10}$/,
    email:/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{0,})$/i,
    password: /^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{6,15}$/
}

/*Agregar tipo de documento */
const complete_fields = {
    name: true,
    surname: true,
    dni: true,
    email: true,
    password: true
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
        case "password":
            validation_field(expressions.password, e.target, 'password')
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

document.getElementById("button_cancel").addEventListener('click', (e) =>{
   window.location.href = "/TpAgiles/static/buscarUsuarioUI.html";
});


document.getElementById("button_save").addEventListener('click', (e) =>{
    e.preventDefault();
    if(complete_fields.name && complete_fields.surname && complete_fields.dni && complete_fields.email && complete_fields.password  && type_document){
            document.getElementById('incomplete_field').classList.remove('message_active');
            //UPDATE USER
            updateUser();
    }else{
        document.getElementById('incomplete_field').classList.add('message_active');
    }
});

async function updateUser(){
    let userId = new URLSearchParams(window.location.search).get("id");
    let userInfo = {
            name: inputs[0].value,
            surname: inputs[1].value,
            type: document.getElementById("type_document").value,
            identification: inputs[2].value,
            email: inputs[3].value,
            password: inputs[4].value,
    }

    const request = await fetch("http://localhost:8080/api/user/"+userId,{
        method: 'POST',
        headers: {
            'Content-Type':'application/json; charset=UTF-8'
        },
        body: JSON.stringify(userInfo)
    });

    if(request.ok){
        document.getElementById('success_message').classList.add('message_active');
        setTimeout(() =>{
            document.getElementById('success_message').classList.remove('message_active');
        },5000);
        fields.reset();
        loadUser();
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

async function loadUser(){
    let userId = new URLSearchParams(window.location.search).get("id");
    if(localStorage.idUser != null){
        const request = await fetch("http://localhost:8080/api/user/"+userId,{
            method: 'GET',
            headers: {
            'Content-Type':'application/json; charset=UTF-8',
            'Authorization': localStorage.token
            }
        });
        if(request.ok){
                let user = await request.json();
                loadUserData(user);
        } else {
            window.location.href = "/TpAgiles/static/login.html";
        }
    } else {
        window.location.href = "/TpAgiles/static/login.html";
    }
}

function loadUserData(user){
    inputs[0].value = user.name;
    inputs[1].value = user.surname;
    inputs[2].value = user.identification;
    inputs[3].value = user.email;
    document.getElementById("type_document").value = user.type;
}

document.getElementById("logout").addEventListener("click",(e)=>{
    localStorage.clear();
    window.location.href = "/TpAgiles/static/login.html";
});