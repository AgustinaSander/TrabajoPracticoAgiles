const fieldsLogin = document.getElementById("fields_to_complete_login");
const inputsLogin = document.querySelectorAll("#fields_to_complete_login input");

fieldsLogin.addEventListener('submit', (e) =>{
    e.preventDefault();

    let type_document;
    type_document = document.form.type_document.selectedIndex != 0;
    //INICIAR SESION
    login();
});

async function login(){
    let userInfo = {
        type: document.getElementById("type_document_login").value,
        identification: inputsLogin[0].value,
        password: inputsLogin[1].value,
    }

    const request = await fetch("http://localhost:8080/api/login",{
        method: 'POST',
        headers: {
            'Content-Type':'application/json; charset=UTF-8'
        },
        body: JSON.stringify(userInfo)
    });

    if(request.ok){
        let data = await request.json();
        localStorage.token = data[0];
        localStorage.idUser = data[1];
        window.location.href = "/TpAgiles/static/buscarUsuarioUI.html";
    }
    else{
        request.json().then(text => {
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