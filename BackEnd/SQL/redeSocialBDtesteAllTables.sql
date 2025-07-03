CREATE DATABASE redesocialbd
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS users (
  id_user SERIAL PRIMARY KEY,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  admin BOOLEAN NOT NULL,
  nome_user VARCHAR(100),
  data_insercao TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- 3. Tabela POSTAGEM
CREATE TABLE postagem (
  id_postagem BIGSERIAL PRIMARY KEY,
  id_user BIGINT NOT NULL,
  anuncio BOOLEAN NOT NULL DEFAULT FALSE,
  texto_post TEXT NOT NULL,
  atributos_json JSON NULL,
  data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_postagem_users
    FOREIGN KEY (id_user) REFERENCES users(id_user)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);


-- 4. Tabela COMENTARIO
CREATE TABLE comentario (
  id_comentario BIGSERIAL PRIMARY KEY,
  id_user BIGINT NOT NULL,
  texto_comentario TEXT NOT NULL,
  data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_comentario_users
    FOREIGN KEY (id_user) REFERENCES users(id_user)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

-- 5. Tabela CURTIDA
CREATE TABLE curtida (
  id_curtida BIGSERIAL PRIMARY KEY,
  id_user BIGINT NOT NULL,
  id_comentario BIGINT NULL,
  id_postagem BIGINT NULL,
  data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_curtida_users
    FOREIGN KEY (id_user) REFERENCES users(id_user)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_curtida_comentario
    FOREIGN KEY (id_comentario) REFERENCES comentario(id_comentario)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_curtida_postagem
    FOREIGN KEY (id_postagem) REFERENCES postagem(id_postagem)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT chk_curtida_target
    CHECK (
      (id_comentario IS NOT NULL AND id_postagem IS NULL) OR
      (id_comentario IS NULL AND id_postagem IS NOT NULL)
    )
);

-- 6. Tabela SEGUIDOR
CREATE TABLE seguidor (
  id_seguidor BIGSERIAL PRIMARY KEY,
  id_user BIGINT NOT NULL,
  id_seguidor_user BIGINT NOT NULL,
  data_insercao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_seguidor_users
    FOREIGN KEY (id_user) REFERENCES users(id_user)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_seguidor_follower
    FOREIGN KEY (id_seguidor_user) REFERENCES users(id_user)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  UNIQUE (id_user, id_seguidor_user),
  CONSTRAINT chk_seguidor_not_self
    CHECK (id_user <> id_seguidor_user)
);