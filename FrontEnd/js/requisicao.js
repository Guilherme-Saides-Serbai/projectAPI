console.log("antes de carregar o DOM \n ");

window.document.body.onload = function () {
    // Seu código JavaScript aqui
    let dive = document.querySelector("#dive");
    console.log("Todos os recursos foram carregados!");

    if (dive) {
        dive.innerHTML =
            "Todos os recursos foram carregados! (em window.document.body.onload)";
    } else {
        console.info("a div dive não existe nesse html !!");
    }

    try {
        //Usando Fetch API
        async function fetchUsers() {
            try {
                const response = await fetch("http://localhost:8087/users/");
                const data = await response.json();
                console.log("Dados recebidos:", data);
                let jsonzudo = document.querySelector("#json");
                if (jsonzudo) {
                    jsonzudo.innerHTML = `
                    User Diz:
                    ${JSON.stringify(data)}
                    `;
                    // texto puro
                    // jsonzudo.innerHTML = `
                    // <br>
                    // <hr>
                    // <h5>
                    // ${JSON.stringify(data)}
                    // </h5>
                    // `;
                } else {
                    console.info("a div jsonzudo não existe nesse html !!");
                }
            } catch (error) {
                console.error("Erro na requisição:", error);
            }
        }

        fetchUsers();
    } catch (error) {
        console.log("Erro pois:    ", error);
    }
};
