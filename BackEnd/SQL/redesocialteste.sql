-- Database: redesocialbd

-- DROP DATABASE IF EXISTS redesocialbd;
-- conta POSTGRESQL: marcaozitos ou localhost ou postgres
-- senha POSTGRESQL: Enquebravel11


-- CREATE DATABASE redesocialbd
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'Portuguese_Brazil.1252'
--     LC_CTYPE = 'Portuguese_Brazil.1252'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS users (
  id_user SERIAL PRIMARY KEY,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  admin BOOLEAN NOT NULL,
  nome_user VARCHAR(100),
  data_insercao TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- INSERT INTO users (email, senha, slug, admin, nome_user)
-- VALUES 
--   ('alice@example.com', 'senha123', 'alice-santos', true,  'Alice Santos'),
--   ('bruno@example.com', 'brunopass', 'bruno-souza', false, 'Bruno Souza'),
--   ('carla@example.com', 'carla321', 'carla-martins', false, 'Carla Martins'),
--   ('diego@example.com', 'diegopass', 'diego-lima', true,  'Diego Lima');


-- SELECT * from users;


-- outros comandos

-- inserir
-- INSERT INTO users (email, senha, slug, admin, nome_user)
-- VALUES ('novo.email@example.com', 'nova_senha', 'novo-usuario', false, 'Novo Usuário');

-- alterar por email
-- UPDATE users
-- SET
--     senha = 'nova_senha_segura',
--     slug = 'novo-slug-do-usuario',
--     admin = true,
--     nome_user = 'Nome do Usuário Atualizado'
-- WHERE id_user = ['email.atualizado@example.com'];

-- deletar pelo id
-- DELETE FROM users
-- WHERE id_user = 7;

-- deletar pelo email
-- DELETE FROM users
-- WHERE email = 'usuario.para.deletar@example.com';

-- selecionar os adm
-- SELECT *
-- FROM users
-- WHERE admin = true;


SELECT * from users;
