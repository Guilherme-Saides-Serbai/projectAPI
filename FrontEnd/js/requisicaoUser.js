// inserir
window.onload = function () {
    try {
        // Inserir usuário
        document.getElementById("formNomeUserInserir").innerHTML = `
            <input id="emailI" type="text" placeholder="Email" style="display:block;" minlength="3" maxlength="120" required/>
            <input id="senhaI" type="text" placeholder="Senha Hash" style="display:block;" required/>
            <input id="slugI" type="text" placeholder="Slug" style="display:block;" />
            <select id="adminI" style="display:block;">
                <option value="false">User</option>
                <option value="true">Admin</option>
            </select>
            <input id="nomeI" type="text" placeholder="Nome" style="display:block;"/>
        `;
        document.getElementById(
            "botaoEnviaUserInserir"
        ).innerHTML = `<button id="btnInserir">Inserir</button>`;
        document
            .getElementById("btnInserir")
            .addEventListener("click", createUser);

        // Atualizar usuário
        document.getElementById("formNomeUserAlterar").innerHTML = `
            <input id="idU" type="number" placeholder="ID a alterar" required/>
            <input id="emailU" type="text" placeholder="Email" required/>
            <input id="senhaU" type="text" placeholder="Senha Hash" />
            <input id="slugU" type="text" placeholder="Slug" />
            <select id="adminU">
                <option value="false">User</option>
                <option value="true">Admin</option>
            </select>
            <input id="nomeU" type="text" placeholder="Nome" />
        `;
        document.getElementById(
            "botaoEnviaUserAlterar"
        ).innerHTML = `<button id="btnAlterar">Alterar</button>`;
        document
            .getElementById("btnAlterar")
            .addEventListener("click", updateUser);

        // Buscar usuário
        document.getElementById("formNomeUserSelecionar").innerHTML = `
            <input id="idS" type="number" placeholder="ID a buscar" />
        `;
        document.getElementById(
            "botaoBuscarUser"
        ).innerHTML = `<button id="btnBuscar">Buscar</button>`;
        document
            .getElementById("btnBuscar")
            .addEventListener("click", getUserById);

        // Carrega lista inicial
        listUsers();

        // ichi no kataaa
        renderDeleteList();

        // --- Adiciona o listener para os botões de exclusão ---
        // Usando "event delegation" para capturar cliques na div pai
        const deleteListDiv = document.getElementById("listaUserExcluir");
        deleteListDiv.addEventListener("click", function (event) {
            // Verifica se o elemento clicado é um botão de exclusão
            if (
                event.target &&
                event.target.classList.contains("btn-excluir")
            ) {
                const userId = event.target.getAttribute("data-id");
                deleteUser(userId);
            }
        });
    } catch (error) {
        console.log("Marcao -> deu erro pois: ", error);
    }
};

// Funções async
async function listUsers() {
    try {
        const res = await fetch("http://localhost:8087/users/");
        const users = await res.json();
        const tbody = document.querySelector("#tabelaUsers tbody");
        tbody.innerHTML = "";
        users.forEach((u) => {
            tbody.innerHTML += `
            <tr>
                <td>${u.idUser}</td>
                <td>${u.email}</td>
                <td>${u.slug}</td>
                <td>${u.isAdmin}</td>
                <td>${u.nomeUser}</td>
            </tr>
        `;
        });
    } catch (error) {
        console.log("marcao ->", error);
    }
}

async function createUser() {
    const payload = {
        email: document.getElementById("emailI").value,
        senhaHash: document.getElementById("senhaI").value,
        slug: document.getElementById("slugI").value,
        isAdmin: document.getElementById("adminI").value === "true",
        nomeUser: document.getElementById("nomeI").value,
    };

    if (
        payload.email != "" &&
        payload.email != null &&
        payload.senhaHash != "" &&
        payload.senhaHash != null &&
        payload.slug != "" &&
        payload.slug != null &&
        payload.nomeUser != "" &&
        payload.nomeUser != null
    ) {
        try {
            const response = await fetch("http://localhost:8087/users/", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            // 2. ONDAS CEREBRAISSS !! NIGGA NIGGA NIGGA NIGGA (status 2xx)
            // status 2xx e alguma merda fodida do krl
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(errorMessage);
            }

            listUsers();
        } catch (error) {
            console.log("marcao ->", error);
            let errorDireto = document.getElementById("errorDireto");
            errorDireto.innerHTML = "marcao :  " + error;
        }
    } else {
        let errorDireto = document.getElementById("errorDireto");
        errorDireto.innerHTML =
            "marcao :  " + "aqui não otario, preenche o form direito !!";
        console.info("aqui não otario, preenche o form direito !!");
    }
}

async function updateUser() {
    const id = document.getElementById("idU").value;
    const payload = {
        email: document.getElementById("emailU").value,
        senhaHash: document.getElementById("senhaU").value,
        slug: document.getElementById("slugU").value,
        isAdmin: document.getElementById("adminU").value === "true",
        nomeUser: document.getElementById("nomeU").value,
    };
    await fetch(`http://localhost:8087/users/${id}/`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    });
    listUsers();
}

async function getUserById() {
    const id = document.getElementById("idS").value;
    const res = await fetch(`http://localhost:8087/users/${id}/`);
    const user = await res.json();
    document.getElementById("resultadoBusca").innerHTML = user.idUser
        ? `<pre>${JSON.stringify(user, null, 2)}</pre>`
        : "Usuário não encontrado.";
}

// 5. RENDERIZAR A LISTA PARA EXCLUSÃO (NOVA FUNÇÃO)
async function renderDeleteList() {
    try {
        const res = await fetch("http://localhost:8087/users/");
        if (!res.ok)
            throw new Error("Falha ao carregar a lista para exclusão.");

        const users = await res.json();
        const deleteListDiv = document.getElementById("listaUserExcluir");

        if (users.length === 0) {
            deleteListDiv.innerHTML = "<p>Nenhum usuário para excluir.</p>";
            return;
        }

        // Cria uma tabela para melhor alinhamento
        let tableHTML = `
            <table style="width: 80%; border-collapse: collapse; border:1px solid black;">
                <thead>
                    <tr style="border:1px solid black;">
                        <th style="text-align: center; border-right:1px solid black;">Nome</th>
                        <th style="text-align: center; border-right:1px solid black;">Email</th>
                        <th style="text-align: center; border-right:1px solid black;">Slug</th>
                        <th style="text-align: center; border-right:1px solid black;">Ação</th>
                    </tr>
                </thead>
                <tbody>
        `;

        users.forEach((u) => {
            tableHTML += `
                <tr style="border:1px solid black;">
                    <td style="text-align: center; border-right:1px solid black;">${u.nomeUser}</td>
                    <td style="text-align: center; border-right:1px solid black;">${u.email}</td>
                    <td style="text-align: center; border-right:1px solid black;">${u.slug}</td>
                    <td class="centro">
                        <button class="btn-excluir btn btn-danger" data-id="${u.idUser}" 
                        style="border-right:1px solid black; margin: 5px 0;">
                            Excluir 🗑️
                        </button>
                    </td>
                </tr>
            `;
        });

        tableHTML += "</tbody></table>";
        deleteListDiv.innerHTML = tableHTML;
    } catch (error) {
        console.error("Erro em renderDeleteList:", error);
        document.getElementById("listaUserExcluir").innerHTML =
            "Erro ao carregar lista para exclusão.";
    }
}

// 6. EXCLUIR USUÁRIO (NOVA FUNÇÃO)
async function deleteUser(userId) {
    // Confirmação antes de excluir
    if (
        !confirm(`Tem certeza que deseja excluir o usuário com ID ${userId}?`)
    ) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8087/users/${userId}/`, {
            method: "DELETE",
        });

        const resultMessage = await response.text();

        if (!response.ok) {
            throw new Error(resultMessage);
        }

        alert(resultMessage); // Exibe a mensagem de sucesso do backend ("✅ Usuário ... removido")

        // Atualiza ambas as listas na tela
        listUsers();
        renderDeleteList();
    } catch (error) {
        console.error("Erro ao excluir usuário:", error);
        alert(`Erro: ${error.message}`); // Exibe a mensagem de erro ("⚠️ Usuário ... não encontrado")
    }
}
