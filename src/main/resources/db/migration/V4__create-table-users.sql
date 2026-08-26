CREATE TABLE IF NOT EXISTS public.usuario
(
    id uuid NOT NULL,
    email character varying(120) NOT NULL,
    password character varying(240) NOT NULL,
    role character varying(255) NOT NULL,
    ativo boolean DEFAULT true NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)

);