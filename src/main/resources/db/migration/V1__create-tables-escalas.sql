CREATE TABLE militares
(
    id         uuid         NOT NULL,
    nm_militar varchar(120) NOT NULL,
    graduacao  integer      NOT NULL,
    st_ativo   boolean      NOT NULL DEFAULT true,
    email      varchar(128),
    CONSTRAINT militares_pkey PRIMARY KEY (id)
);

CREATE TABLE rodada_escala
(
    id   uuid NOT NULL,
    data date NOT NULL,
    CONSTRAINT rodada_escala_pkey PRIMARY KEY (id),
    CONSTRAINT rodada_escala_data_uk UNIQUE (data)
);

CREATE TABLE escala_extra
(
    id         uuid NOT NULL,
    militar_id uuid NOT NULL,
    rodada_id  uuid NOT NULL,
    CONSTRAINT escala_extra_pkey PRIMARY KEY (id),
    CONSTRAINT escala_extra_militar_rodada_uk UNIQUE (militar_id, rodada_id),
    CONSTRAINT escala_extra_militar_fk FOREIGN KEY (militar_id) REFERENCES militares (id),
    CONSTRAINT escala_extra_rodada_fk FOREIGN KEY (rodada_id) REFERENCES rodada_escala (id) ON DELETE CASCADE
);

CREATE INDEX escala_extra_rodada_idx ON escala_extra (rodada_id);

CREATE TABLE afastamento
(
    id             uuid         NOT NULL,
    tp_afastamento varchar(255) NOT NULL,
    militar_id     uuid         NOT NULL,
    dt_inicio      date         NOT NULL,
    dt_fim         date         NOT NULL,
    CONSTRAINT afastamento_pkey PRIMARY KEY (id),
    CONSTRAINT afastamento_militar_fk FOREIGN KEY (militar_id) REFERENCES militares (id),
    CONSTRAINT afastamento_periodo_ck CHECK (dt_fim >= dt_inicio)
);

CREATE INDEX afastamento_militar_idx ON afastamento (militar_id);
