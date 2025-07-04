-- CREATE DATABASE redesocialbd
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'Portuguese_Brazil.1252'
--     LC_CTYPE = 'Portuguese_Brazil.1252'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;

-- -- O MEU TABELA USERS.
-- CREATE TABLE IF NOT EXISTS users (
--   id_user SERIAL PRIMARY KEY,
--   email VARCHAR(150) NOT NULL UNIQUE,
--   senha VARCHAR(100) NOT NULL,
--   slug VARCHAR(100) NOT NULL UNIQUE,
--   admin BOOLEAN NOT NULL,
--   nome_user VARCHAR(100),
--   data_insercao TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
-- );

-- -- serbai comparacão a poha do user do serbai
CREATE TABLE IF NOT EXISTS users (
id_user SERIAL PRIMARY KEY,
email VARCHAR(150) NOT NULL UNIQUE ,
senha_hash VARCHAR(255) NOT NULL UNIQUE ,
slug VARCHAR(100) NOT NULL UNIQUE ,
is_admin BOOLEAN NOT NULL DEFAULT FALSE ,
nome_user VARCHAR(100) ,
data_insercao TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now ()
);


-- Tabela de postagens
CREATE TABLE IF NOT EXISTS postagem (
id_postagem BIGSERIAL PRIMARY KEY,
id_user INTEGER NOT NULL ,
anuncio BOOLEAN NOT NULL DEFAULT FALSE ,
texto_post TEXT NOT NULL ,
atributos_json JSONB NULL ,
data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
data_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
CONSTRAINT fk_postagem_users
FOREIGN KEY (id_user) REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE CASCADE
);

 -- Tabela de comentários
 CREATE TABLE IF NOT EXISTS comentario (
id_comentario BIGSERIAL PRIMARY KEY,
id_postagem BIGINT NOT NULL ,
id_user INTEGER NOT NULL ,
texto_comentario TEXT NOT NULL ,
data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
data_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
CONSTRAINT fk_comentario_postagem
FOREIGN KEY ( id_postagem ) REFERENCES postagem( id_postagem )
ON DELETE CASCADE ON UPDATE CASCADE ,
CONSTRAINT fk_comentario_users
FOREIGN KEY (id_user) REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE CASCADE
);

 -- Tabela de curtidas
CREATE TABLE IF NOT EXISTS curtida (
id_curtida BIGSERIAL PRIMARY KEY,
id_user INTEGER NOT NULL ,
id_comentario BIGINT NULL ,
id_postagem BIGINT NULL ,
data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
CONSTRAINT fk_curtida_users
FOREIGN KEY (id_user) REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE CASCADE ,
CONSTRAINT fk_curtida_comentario
FOREIGN KEY ( id_comentario ) REFERENCES comentario ( id_comentario )
ON DELETE CASCADE ON UPDATE CASCADE ,
CONSTRAINT fk_curtida_postagem
FOREIGN KEY ( id_postagem ) REFERENCES postagem( id_postagem )
ON DELETE CASCADE ON UPDATE CASCADE ,
CONSTRAINT chk_curtida_target
CHECK (
( id_comentario IS NOT NULL AND id_postagem IS NULL) OR
( id_comentario IS NULL AND id_postagem IS NOT NULL)
)
);

-- Tabela de seguidores
CREATE TABLE IF NOT EXISTS seguidor (
id_seguidor BIGSERIAL PRIMARY KEY,
id_user INTEGER NOT NULL , -- O usuário que está sendo seguido
id_seguidor_user INTEGER NOT NULL , -- O usuário que está seguindo
data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
CONSTRAINT fk_seguidor_user_being_followed
FOREIGN KEY (id_user) REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE CASCADE ,
CONSTRAINT fk_seguidor_user_who_is_following
FOREIGN KEY ( id_seguidor_user ) REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE CASCADE ,
UNIQUE (id_user , id_seguidor_user ),
CONSTRAINT chk_seguidor_not_self
CHECK (id_user <> id_seguidor_user )
);

-- Índices para otimização de performance
CREATE INDEX idx_postagem_id_user ON postagem(id_user);
CREATE INDEX idx_comentario_id_postagem ON comentario ( id_postagem );
CREATE INDEX idx_comentario_id_user ON comentario (id_user);
CREATE INDEX idx_curtida_id_user ON curtida(id_user);
CREATE INDEX idx_seguidor_id_user ON seguidor(id_user);
CREATE INDEX idx_seguidor_id_seguidor_user ON seguidor( id_seguidor_user );

-- users
-- select * from users;

-- -- curtida
-- select * from curtida;

-- -- comentario
-- select * from comentario;

-- -- postagem
-- select * from postagem;

-- seguidor
-- select * from seguidor;


-- DROP TABLE users;
-- DROP TABLE comentario;
-- DROP TABLE postagem;
-- DROP TABLE curtida;
-- DROP TABLE seguidor;

-- -- Comandos mais comuns:
-- -- Adicionar coluna: 
ALTER TABLE nome_da_tabela ADD nome_da_coluna tipo_de_dados;
-- -- Remover coluna: 
ALTER TABLE nome_da_tabela DROP COLUMN nome_da_coluna;
-- -- Alterar tipo de dados de uma coluna: 
ALTER TABLE nome_da_tabela ALTER COLUMN nome_da_coluna TYPE novo_tipo_de_dados;
-- -- Renomear tabela: 
ALTER TABLE nome_antigo_da_tabela RENAME TO nome_novo_da_tabela;
-- -- Adicionar restrição: 
ALTER TABLE nome_da_tabela ADD CONSTRAINT nome_da_restricao tipo_de_restricao (nome_da_coluna);
-- -- Remover restrição: 
ALTER TABLE nome_da_tabela DROP CONSTRAINT nome_da_restricao; 


-- -- alteracao que eu vou usar na tabela users . 
ALTER TABLE users ADD senha_hash VARCHAR(255) NOT NULL;
ALTER TABLE users ADD is_admin BOOLEAN NOT NULL DEFAULT FALSE;
-- -- mais coisa
ALTER TABLE users DROP COLUMN senha;
ALTER TABLE users DROP COLUMN admin;


-- -- assasina todos os itens da table
TRUNCATE TABLE users;
