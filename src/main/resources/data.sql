INSERT INTO rol (rol_id, rol_nombre)
VALUES
    (1, 'Administrador'),
    (2, 'Usuario')
    ON CONFLICT (rol_id)
DO UPDATE SET rol_nombre = EXCLUDED.rol_nombre;
