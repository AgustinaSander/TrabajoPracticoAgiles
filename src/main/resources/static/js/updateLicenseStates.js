async function updateStates(){
     let request = await fetch("http://localhost:8080/api/update",{
            method: 'POST',
            headers: {'Content-Type':'application/json; charset=UTF-8',
                      'Authorization': localStorage.token
            }
     });
}