CREATE TABLE usuario
(
    id         uuid         NOT NULL,
    -- nome de exibição (ex.: nome completo); preenchido pelo próprio militar no primeiro acesso
    nome       varchar(255),
    email      varchar(255) NOT NULL,
    -- NULL = primeiro acesso pendente (usuário ainda não definiu senha)
    password   varchar(255),
    role       varchar(20)  NOT NULL,
    ativo      boolean      NOT NULL DEFAULT true,
    militar_id uuid,
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_email_uk UNIQUE (email),
    CONSTRAINT usuario_militar_uk UNIQUE (militar_id),
    CONSTRAINT usuario_militar_fk FOREIGN KEY (militar_id) REFERENCES militares (id),
    CONSTRAINT usuario_role_ck CHECK (role IN ('ADMIN', 'USER'))
);

CREATE TABLE password_reset_token
(
    id         uuid        NOT NULL,
    token_hash varchar(64) NOT NULL,
    usuario_id uuid        NOT NULL,
    expiracao  timestamp   NOT NULL,
    usado      boolean     NOT NULL DEFAULT false,
    CONSTRAINT password_reset_token_pkey PRIMARY KEY (id),
    CONSTRAINT password_reset_token_hash_uk UNIQUE (token_hash),
    CONSTRAINT password_reset_token_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE
);
