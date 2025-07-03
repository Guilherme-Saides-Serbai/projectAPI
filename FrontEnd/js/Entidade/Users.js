class User {
    admin = false;
    dataInsercao = new Date();

    constructor({ idUser, email, senha, slug, admin, nomeUser, dataInsercao }) {
        this.idUser = idUser;
        this.email = email;
        this.senha = senha || null;
        this.slug = slug || null;
        this.admin = admin || this.admin; // Mantém padrão se não fornecido
        this.nomeUser = nomeUser || null;
        this.dataInsercao = dataInsercao || this.dataInsercao; //admin || this.admin; // Valor padrão = data/hora atual
    }

    // Getters
    get getIdUser() {
        return this.idUser;
    }

    get getEmail() {
        return this.email;
    }

    get getSenha() {
        return this.senha;
    }

    get getSlug() {
        return this.slug;
    }

    get getAdmin() {
        return this.admin;
    }

    get getNomeUser() {
        return this.nomeUser;
    }

    get getDataInsercao() {
        return this.dataInsercao;
    }

    // Setters
    set setIdUser(idUser) {
        this.idUser = idUser;
    }

    set setEmail(email) {
        this.email = email;
    }

    set setSenha(senha) {
        this.senha = senha;
    }

    set setSlug(slug) {
        this.slug = slug;
    }

    set setAdmin(admin) {
        this.admin = admin;
    }

    set setNomeUser(nomeUser) {
        this.nomeUser = nomeUser;
    }

    // Métodos adicionais
    updateDataInsercao() {
        this.dataInsercao = new Date();
        return this;
    }

    toJSON() {
        return {
            idUser: this.idUser,
            email: this.email,
            slug: this.slug,
            admin: this.admin,
            nomeUser: this.nomeUser,
            dataInsercao: this.dataInsercao.toISOString(),
        };
    }

    userValido() {
        const errors = [];
        if (!this.email) errors.push("Email é obrigatório");
        if (!this.senha) errors.push("Senha é obrigatória");
        if (!this.slug) errors.push("Slug é obrigatório");
        return errors.length ? errors : true;
    }
}

/*
Valores padrão seguros para todos os campos
Exemplo de uso:
*/
const novoUser = new User({
    idUser: 999,
    email: "usuario@exemplo.com",
    senha: "senha123",
    slug: "usuario-teste",
    nomeUser: "Lucas Marcão",
});

if (novoUser.userValido() === true) {
    console.log("if novoUSER validado: \n", novoUser.userValido());
} else {
    console.log("else novoUSER validado: \n", novoUser.userValido());
}

console.log("novoUSER (não é do BD, é do js esse): \n\n", novoUser.toJSON());
