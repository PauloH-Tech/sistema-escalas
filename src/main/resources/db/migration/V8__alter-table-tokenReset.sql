ALTER TABLE password_reset_token
ADD CONSTRAINT users_fk
FOREIGN KEY (usuario_id)
REFERENCES usuario (id);