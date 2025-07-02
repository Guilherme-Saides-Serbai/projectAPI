console.log("antes de carregar o DOM \n ");

window.document.body.onload = function () {
    // Seu código JavaScript aqui
    let dive = document.querySelector("#dive");
    console.log("Todos os recursos foram carregados!");

    dive.innerHTML = "Todos os recursos foram carregados!";

    try {
        //Usando Fetch API
        async function fetchUsers() {
            try {
                const response = await fetch("http://localhost:8087/users/");
                const data = await response.json();
                console.log("Dados recebidos:", data);
                let jsonzudo = document.querySelector("#json");
                jsonzudo.innerHTML = ` 
                <br>
                <hr>
                <h5>
                ${JSON.stringify(data)}
                </h5>
                `;
            } catch (error) {
                console.error("Erro na requisição:", error);
            }
        }

        fetchUsers();
    } catch (error) {
        console.log("Erro pois:    ", error);
    }
};
