document.addEventListener("DOMContentLoaded", function(event) {
    if(localStorage.token == undefined){
        window.location.href = "/TpAgiles/static/login.html";
    }
});

const fields = document.querySelector("#fields_to_complete");
const inputs = document.querySelectorAll("#fields_to_complete input");

/*Search fields validation*/
const expressions =  {
    name: /^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    surname:/^[a-zA-ZÀ-ÿ\s]{0,30}$/,
    dni: /^\d{8,10}$/
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
    }
}

const validation_field = (expressions, input, field) =>{
    if(expressions.test(input.value)){
        document.querySelector(`#field_${field} .error_message`).classList.remove('message_active');
    }else{
        document.querySelector(`#field_${field} .error_message`).classList.add('message_active');
    }
}

inputs.forEach((input) => {
    input.addEventListener('blur', validation)
});

document.getElementById("button").addEventListener('click', (e) =>{
    e.preventDefault();
    searchUsers();
});

async function searchUsers(){
    let filters = {};
    if(inputs[0].value != "") filters.name = inputs[0].value;
    if(inputs[1].value != "") filters.surname = inputs[1].value;
    if(document.getElementById("type_document").value != "SELECCIONE") filters.type = document.getElementById("type_document").value;
    if(inputs[2].value != "") filters.identification = inputs[2].value;

    console.log(filters)

    const request = await fetch("http://localhost:8080/api/users",{
            method: 'POST',
            headers: {'Content-Type':'application/json; charset=UTF-8'},
            body: JSON.stringify(filters)
    });

    if(request.ok){
        let users = await request.json();
        updateUsersList(users);
    }
}

function updateUsersList(users){
    let tbody = document.getElementById('list-users');
    tbody.innerHTML = '';
    for(const [index, u] of users.entries()){
        tbody.innerHTML += `<tr>
            <td>${index+1}</td>
            <td>${u.name}</td>
            <td>${u.surname}</td>
            <td>${u.type}</td>
            <td>${u.identification}</td>
            <td><button class="btn btn-primary" style="float:right;" onclick="updateUser(`+u.id+`)"><i class="fa-regular fa-pen-to-square"></i></button></td>
        </tr>`;
    }
};

function updateUser(id){
    window.location.href = "/TpAgiles/static/modificarUsuarioUI.html?id="+id;
}

document.getElementById("logout").addEventListener("click",(e)=>{
    localStorage.clear();
    window.location.href = "/TpAgiles/static/login.html";
});

document.getElementById("goProfile").addEventListener("click",(e)=>{
    e.preventDefault();
    let url ="/TpAgiles/static/modificarUsuarioUI.html?id="+localStorage.idUser;
    window.location.href = url;
});