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
    //POR AHORA SOLO QUE ME TRAIGA TODOS
    // FALTA: Pasar de alguna forma los filtros, ver que pasa si no encuentra ninguno (que salga algun mensaje)
    const request = await fetch("http://localhost:8080/api/users",{
            method: 'GET',
            headers: {'Content-Type':'application/json'},
    });

    let users = await request.json();

    updateUsersList(users);
}

function updateUsersList(users){
    //FALTA : Identificar los li de alguna forma, ver de ponerles como value el id del user (no pude por las comillas)
    let ul = document.getElementById('list-users');
    ul.innerHTML = '';
    for(u of users){
        ul.innerHTML += `<li class="list-group-item">
            <input class="form-check-input me-1" type="radio" name="listGroupRadio" value="" checked>
            <label class="form-check-label" for="firstRadio">${u.name} ${u.surname}</label>
        </li>`;
    }
}