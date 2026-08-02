CREATE TABLE IF NOT EXISTS public.email_outbox
(
    id uuid NOT NULL,
    to character varying(120) NOT NULL,
    cc character varying(120),
    subject character varying(240),
    body character varying(2000),
    tentativas integer DEFAULT 0,
    status character varying(100) NOT NULL,
    data_criacao date NOT NULL,
    data_envio date,
    erro character varying(2000),
    CONSTRAINT emails_pkey PRIMARY KEY (id)

)