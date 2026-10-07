-- O e-mail passa a ser responsabilidade do usuário (acesso ao app).
-- Militares que já tinham e-mail ganham um usuário sem senha (primeiro acesso pendente).
INSERT INTO usuario (id, email, role, ativo, militar_id)
SELECT gen_random_uuid(), lower(trim(m.email)), 'USER', m.st_ativo, m.id
FROM militares m
WHERE m.email IS NOT NULL
  AND trim(m.email) <> ''
  AND NOT EXISTS (SELECT 1 FROM usuario u WHERE u.militar_id = m.id)
  AND NOT EXISTS (SELECT 1 FROM usuario u WHERE u.email = lower(trim(m.email)));

ALTER TABLE militares DROP COLUMN email;
