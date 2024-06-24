INSERT INTO rol (rol_id, rol_nombre)
VALUES (1, 'Administrador'),
       (2, 'Usuario')
ON DUPLICATE KEY UPDATE rol_nombre = VALUES(rol_nombre);
