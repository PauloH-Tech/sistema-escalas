CREATE TABLE email_outbox
(
    id           uuid         NOT NULL,
    msg_to       varchar(255) NOT NULL,
    msg_cc       varchar(255),
    subject      varchar(255),
    body         text,
    tentativas   integer      NOT NULL DEFAULT 0,
    status       varchar(30)  NOT NULL,
    data_criacao timestamp    NOT NULL,
    data_envio   timestamp,
    erro         text,
    CONSTRAINT email_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX email_outbox_status_idx ON email_outbox (status);
