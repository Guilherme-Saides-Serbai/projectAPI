# projectAPI

Quando quiser pular para a próxima linha, digite um espaço seguido por uma barra invertida \ e pressione Enter

This is a simple Java Spring Boot API project with a PostgreSQL database. It is well-suited for beginners learning Java web development.

POST /pessoas (Criar pessoa): ---
curl -X POST -H "Content-Type: application/json" -d '{
"nome": "Maria Oliveira",
"nota": 8.7,
"masculino": false
}' http://localhost:8087/pessoas

GET /pessoas (Listar todas): ---
curl http://localhost:8087/pessoas

GET /pessoas/1 (Buscar por ID): ---
curl http://localhost:8087/pessoas/1

DELETE /pessoas/1 (DELETAR PESSOA) ---
curl -X DELETE http://localhost:8087/pessoas/1

PUT /pessoas/1 (ALTERAR PESSOA) ---
curl -X PUT -H "Content-Type: application/json" -d '{
"nome": "Novo Nome",
"nota": 10.0,
"masculino": true
}' http://localhost:8087/pessoas/1

GET http://localhost:8080/users → lista todos os usuários

POST http://localhost:8080/users → cria um novo

GET http://localhost:8080/users/{id}

PUT http://localhost:8080/users/{id}

DELETE http://localhost:8080/users/{id}

// REQUISIÇÃO PELO BD
curl -X POST http://localhost:8087/users -H "Content-Type: application/json; charset=utf-8" -d '{
"email": "lucazzzs@example.com",
"senha": "abc123",
"slug": "lucas-marqueszz",
"admin": false,
"nomeUser": "Lucas zzz Marques"
}'
