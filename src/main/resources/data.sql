INSERT INTO rol (rol_id, rol_nombre)
VALUES (1, 'Administrador'),
       (2, 'Empleado'),
       (3, 'Propietario'),
       (4, 'Inquilino')
ON DUPLICATE KEY UPDATE rol_nombre = VALUES(rol_nombre);
