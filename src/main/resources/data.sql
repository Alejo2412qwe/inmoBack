INSERT INTO rol (rol_id, rol_nombre)
VALUES (1, 'Administrador'),
       (2, 'Custodio'),
       (3, 'Docente')
ON DUPLICATE KEY UPDATE rol_nombre = VALUES(rol_nombre);
