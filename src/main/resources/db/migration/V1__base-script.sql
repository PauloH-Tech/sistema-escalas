BEGIN;

CREATE TABLE IF NOT EXISTS public.afastamento
(
    id uuid NOT NULL,
    dt_fim date NOT NULL,
    dt_inicio date NOT NULL,
    tp_afastamento character varying(255) COLLATE pg_catalog."default" NOT NULL,
    militar_id uuid NOT NULL,
    CONSTRAINT afastamento_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.escala_extra
(
    id uuid NOT NULL,
    militar_id uuid NOT NULL,
    rodada_id uuid NOT NULL,
    CONSTRAINT escala_extra_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.militares
(
    id uuid NOT NULL,
    nm_militar character varying(120) COLLATE pg_catalog."default" NOT NULL,
    graduacao integer,
    st_ativo boolean NOT NULL,
    CONSTRAINT militares_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.rodada_escala
(
    id uuid NOT NULL,
    data date NOT NULL,
    CONSTRAINT rodada_escala_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.afastamento
    ADD CONSTRAINT fkkb5oo1e4f8b5g0qe0g19eosnq FOREIGN KEY (militar_id)
    REFERENCES public.militares (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.escala_extra
    ADD CONSTRAINT fkok373vidwhvlaswl7nbhwgrx9 FOREIGN KEY (rodada_id)
    REFERENCES public.rodada_escala (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.escala_extra
    ADD CONSTRAINT fkso9y7mvayjg5v8vri8ld32w76 FOREIGN KEY (militar_id)
    REFERENCES public.militares (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;