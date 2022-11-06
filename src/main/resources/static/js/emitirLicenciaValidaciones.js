const fields = document.querySelector("#fields_to_complete");
const inputs = document.querySelectorAll("#fields_to_complete input");
//const btn_search = document.querySelector("#button");
//const results = document.querySelector("#show_results");


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

/*Show results*/

// btn_search.addEventListener("click", show_titulares)

// function show_titulares(){
//     document.querySelector(`#show_results .show_results`).classList.add('show_results_active');
// }


