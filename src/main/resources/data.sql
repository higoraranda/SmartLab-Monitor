INSERT INTO predios (nome) VALUES ('Bloco A');
INSERT INTO predios (nome) VALUES ('Bloco B');

INSERT INTO laboratorios (nome, predio_id) VALUES ('Lab 01', 1);
INSERT INTO laboratorios (nome, predio_id) VALUES ('Lab 02', 1);
INSERT INTO laboratorios (nome, predio_id) VALUES ('Lab 03', 2);

INSERT INTO computadores (patrimonio, laboratorio_id) VALUES ('PC-001', 1);
INSERT INTO computadores (patrimonio, laboratorio_id) VALUES ('PC-002', 1);
INSERT INTO computadores (patrimonio, laboratorio_id) VALUES ('PC-003', 2);
INSERT INTO computadores (patrimonio, laboratorio_id) VALUES ('PC-004', 3);
