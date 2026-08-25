CREATE TABLE IF NOT EXISTS public.usuario
(
    id uuid NOT NULL,
    username character varying(120) NOT NULL,
    email character varying(120),
    password character varying(240),
    role character varying(255)
    ativo boolean DEFAULT true,
    CONSTRAINT emails_pkey PRIMARY KEY (id)

);