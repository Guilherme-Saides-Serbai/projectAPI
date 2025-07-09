// Configurações iniciais
window.onload = function () {
    try {
        // Botão de inserir comentário
        document
            .getElementById("btnInserirComentario")
            .addEventListener("click", createComentario);

        // Botão de atualizar comentário
        document
            .getElementById("btnAlterarComentario")
            .addEventListener("click", updateComentario);

        // Botão de buscar comentário
        document
            .getElementById("btnBuscarComentario")
            .addEventListener("click", getComentarioById);

        // Carrega a lista de comentários
        listComentarios();

        // Renderiza a lista de comentários para exclusão
        renderDeleteList();

        // Adiciona o listener para os botões de exclusão
        const deleteListDiv = document.getElementById("listaComentarioExcluir");
        deleteListDiv.addEventListener("click", function (event) {
            if (
                event.target &&
                event.target.classList.contains("btn-excluir-comentario")
            ) {
                const comentarioId = event.target.getAttribute("data-id");
                deleteComentario(comentarioId);
            }
        });
    } catch (error) {
        console.log("Erro ao inicializar: ", error);
    }
};

// Função para listar comentários
async function listComentarios() {
    try {
        const res = await fetch("http://localhost:8087/comentarios/");
        const comentarios = await res.json();
        const tbody = document.querySelector("#tabelaComentarios tbody");
        tbody.innerHTML = "";

        comentarios.forEach((c) => {
            const dataFormatada = c.dataInsercao
                ? new Date(c.dataInsercao).toLocaleString()
                : "";
            const dataFormatada2 = c.dataAlteracao
                ? new Date(c.dataAlteracao).toLocaleString()
                : "";
            tbody.innerHTML += `
                    <tr>
                        <td>${c.idComentario}</td>
                        <td>${c.idPostagem}</td>
                        <td>${c.user.idUser} - ${c.user.nomeUser}</td>
                        <td>${c.textoComentario}</td>
                        <td>${dataFormatada}</td>
                        <td>${dataFormatada2}</td>
                    </tr>
                `;
        });
    } catch (error) {
        console.error("Erro ao listar comentários:", error);
    }
}

// Função para criar um comentário
async function createComentario() {
    const payload = {
        idPostagem: parseInt(document.getElementById("postagemIdI").value),
        idUser: parseInt(document.getElementById("userIdI").value),
        textoComentario: document.getElementById("textoComentarioI").value,
    };

    // Validação básica
    if (!payload.idPostagem || !payload.idUser || !payload.textoComentario) {
        document.getElementById("errorDireto").innerHTML =
            "Preencha todos os campos obrigatórios.";
        return;
    }

    try {
        const response = await fetch("http://localhost:8087/comentarios/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                idPostagem: payload.idPostagem,
                user: { idUser: payload.idUser },
                textoComentario: payload.textoComentario,
            }),
        });

        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(errorMessage);
        }

        // Limpa o formulário e atualiza a lista
        document.getElementById("postagemIdI").value = "";
        document.getElementById("userIdI").value = "";
        document.getElementById("textoComentarioI").value = "";
        document.getElementById("errorDireto").innerHTML = "";
        listComentarios();
        renderDeleteList(); // Atualiza a lista de exclusão
    } catch (error) {
        console.error("Erro ao criar comentário:", error);
        document.getElementById("errorDireto").innerHTML =
            "Erro: " + error.message;
    }
}

// Função para atualizar um comentário
async function updateComentario() {
    const id = document.getElementById("comentarioIdU").value;
    const texto = document.getElementById("textoComentarioU").value;

    if (!id || !texto) {
        document.getElementById("errorDireto").innerHTML =
            "Preencha o ID e o novo texto.";
        return;
    }

    try {
        const response = await fetch(
            `http://localhost:8087/comentarios/${id}/`,
            {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    textoComentario: texto,
                }),
            }
        );

        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(errorMessage);
        }

        // Limpa o formulário e atualiza a lista
        document.getElementById("comentarioIdU").value = "";
        document.getElementById("textoComentarioU").value = "";
        document.getElementById("errorDireto").innerHTML = "";
        listComentarios();
    } catch (error) {
        console.error("Erro ao atualizar comentário:", error);
        document.getElementById("errorDireto").innerHTML =
            "Erro: " + error.message;
    }
}

// Função para buscar um comentário por ID
async function getComentarioById() {
    const id = document.getElementById("comentarioIdS").value;
    if (!id) {
        document.getElementById("errorDireto").innerHTML =
            "Informe o ID do comentário.";
        return;
    }

    try {
        const res = await fetch(`http://localhost:8087/comentarios/${id}/`);
        if (!res.ok) {
            throw new Error("Comentário não encontrado.");
        }
        const comentario = await res.json();
        document.getElementById(
            "resultadoBuscaComentario"
        ).innerHTML = `<pre>${JSON.stringify(comentario, null, 2)}</pre>`;
    } catch (error) {
        console.error("Erro ao buscar comentário:", error);
        document.getElementById("resultadoBuscaComentario").innerHTML =
            "Erro: " + error.message;
    }
}

// Função para renderizar a lista de comentários para exclusão
async function renderDeleteList() {
    try {
        const res = await fetch("http://localhost:8087/comentarios/");
        if (!res.ok) throw new Error("Falha ao carregar comentários.");

        const comentarios = await res.json();
        const deleteListDiv = document.getElementById("listaComentarioExcluir");

        if (comentarios.length === 0) {
            deleteListDiv.innerHTML = "<p>Nenhum comentário para excluir.</p>";
            return;
        }

        // Cria uma tabela para melhor alinhamento
        let tableHTML = `

                    <table style="width: 80%; border-collapse: collapse;">
                        <thead>
                            <tr>
                                <th style="text-align: center;">ID</th>
                                <th style="text-align: center;">ID Postagem</th>
                                <th style="text-align: center;">Texto</th>
                                <th style="text-align: center;">Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                `;

        comentarios.forEach((c) => {
            tableHTML += `
                        <tr>
                            <td style="text-align: center;">${
                                c.idComentario
                            }</td>
                            <td style="text-align: center;">${c.idPostagem}</td>
                            <td>${c.textoComentario.substring(0, 50)}${
                c.textoComentario.length > 50 ? "..." : ""
            }</td>
                            <td class="centro">
                                <button class="btn btn-danger btn-excluir-comentario btn-excluir" data-id="${
                                    c.idComentario
                                }">
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
        deleteListDiv.innerHTML = "Erro ao carregar lista para exclusão.";
    }
}

// Função para excluir um comentário
async function deleteComentario(comentarioId) {
    // Confirmação antes de excluir
    if (
        !confirm(
            `Tem certeza que deseja excluir o comentário com ID ${comentarioId}?`
        )
    ) {
        return;
    }

    try {
        const response = await fetch(
            `http://localhost:8087/comentarios/${comentarioId}/`,
            {
                method: "DELETE",
            }
        );

        const resultMessage = await response.text();

        if (!response.ok) {
            throw new Error(resultMessage);
        }

        alert(resultMessage); // Exibe a mensagem de sucesso do backend

        // Atualiza ambas as listas na tela
        listComentarios();
        renderDeleteList();
    } catch (error) {
        console.error("Erro ao excluir comentário:", error);
        alert(`Erro: ${error.message}`);
    }
}
