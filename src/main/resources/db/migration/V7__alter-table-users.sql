ALTER TABLE password_reset_token
ALTER COLUMN usuario_id TYPE uuid
USING usuario_id::uuid;
