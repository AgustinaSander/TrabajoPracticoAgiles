document.getElementById("button").addEventListener('click', (e) =>{
    e.preventDefault();
    showResult();
});

const showResult = () =>{
    document.querySelector('#container_filter').classList.add('container_filter_active');
    document.querySelector('#show_results').classList.add('showResults_active');
}
