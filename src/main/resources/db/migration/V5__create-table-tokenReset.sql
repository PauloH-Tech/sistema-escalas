CREATE TABLE IF NOT EXISTS public.password_reset_token
(
    id uuid NOT NULL,
    token_hash character varying(64) NOT NULL,
    usuario_id character varying(240) NOT NULL,
    expiracao date NOT NULL,
    usado boolean DEFAULT false,
    CONSTRAINT token_reset_pkey PRIMARY KEY (id)


);