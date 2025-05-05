insert into usuario (id, correo, estado_cuenta, nivel_conocimiento, nombre, tipo_usuario)
values (1, 'usuario1@gmail.com', TRUE, 'BASICO', 'Pepe', 'ALUMNO');
insert into usuario (id, correo, estado_cuenta, nivel_conocimiento, nombre, tipo_usuario)
values (2, 'marcossantangelo1@gmail.com', TRUE, 'INTERMEDIO', 'Marcos Santangelo', 'ADMIN');

insert into suscripcion (id, fecha_fin, fecha_inicio, tipo_suscripcion, id_usuario, precio)
values (1, '2026-01-01', '2025-01-01', 'PREMIUM', 2, 100.00);