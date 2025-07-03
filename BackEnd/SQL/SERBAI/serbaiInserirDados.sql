-- Inserir usuários o meu
-- INSERT INTO users(id_user, email, senha_hash, slug, is_admin, nome_user) 
-- 	VALUES (1, 'teste1@gmail.com', '123456', 'link1', false, 'nome1');
-- INSERT INTO users(id_user, email, senha_hash, slug, is_admin, nome_user) 
-- 	VALUES (2, 'teste2@gmail.com', '123456', 'link2', false, 'nome2');
-- INSERT INTO users(id_user, email, senha_hash, slug, is_admin, nome_user) 
-- 	VALUES (3, 'teste3@gmail.com', '123456', 'link3', false, 'nome3');

-- Inserir usuários (a senha_hash seria gerada por uma lib como bcrypt) o que o serbai fez
-- INSERT INTO users (email , senha_hash , slug , nome_user ) VALUES
-- ('ana.silva@email.com', '$2b$12$abcdefghijklmnopqrstuvwxyABC',
-- 'ana-silva', 'Ana Silva'),
-- ('bruno.costa@email.com', '$2b$12$abcdefghijklmnopqrstuvwxyDEF',
-- 'bruno -costa', 'Bruno Costa'),
-- ('carla.dias@email.com', '$2b$12$abcdefghijklmnopqrstuvwxyGHI',
-- 'carla -dias', 'Carla Dias');

-- Inserir postagens
INSERT INTO postagem (id_user , texto_post ) VALUES
(1, 'Olá, mundo! Esta é minha primeira postagem na rede.'),
(2, 'Acabei de ler um livro incrível sobre PostgreSQL. Recomendo!'),
(1, 'Que dia lindo para um passeio no parque!');

-- Inserir comentários
-- Ana (id 1) comenta no post de Bruno (id 2)
INSERT INTO comentario (id_postagem , id_user , texto_comentario ) VALUES
(2, 1, 'Qual o nome do livro? Fiquei interessada!'),
-- Carla (id 3) também comenta no post de Bruno (id 2)
(2, 3, 'Também quero saber! Adoro bancos de dados.'),
-- Bruno (id 2) comenta no post de Ana (id 3)
(3, 2, 'Aproveite o dia, Ana!');

-- Inserir curtidas
-- Bruno (id 2) curte o post 1 (de Ana)
INSERT INTO curtida (id_user , id_postagem ) VALUES (2, 1);
-- Ana (id 1) curte o post 2 (de Bruno)
INSERT INTO curtida (id_user , id_postagem ) VALUES (1, 2);
-- Carla (id 3) curte o comentário 1 (de Ana no post de Bruno)
INSERT INTO curtida (id_user , id_comentario ) VALUES (3, 1);

-- Inserir seguidores
-- Ana (id 1) segue Bruno (id 2)
INSERT INTO seguidor (id_user , id_seguidor_user ) VALUES (2, 1);
-- Bruno (id 2) segue Ana (id 1)
INSERT INTO seguidor (id_user , id_seguidor_user ) VALUES (1, 2);
-- Carla (id 3) segue Ana (id 1)
INSERT INTO seguidor (id_user , id_seguidor_user ) VALUES (1, 3);