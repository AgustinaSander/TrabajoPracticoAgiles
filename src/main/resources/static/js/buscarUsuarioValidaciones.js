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
    let ul = document.getElementById('list-users');
    ul.innerHTML = '';
    for(u of users){
        ul.innerHTML += `<li class="list-group-item">
            <input class="form-check-input me-1" type="radio" name="listGroupRadio" value="" checked>
            <label class="form-check-label" for="firstRadio">${u.name} ${u.surname}</label>
            <button class="btn btn-primary" style="float:right;" onclick="updateUser(`+u.id+`)"><i class="fa-regular fa-pen-to-square"></i></button>
        </li>`;
    }
};

function updateUser(id){
    window.location.href = "/TpAgiles/static/modificarUsuarioUI.html?id="+id;
}