-- Dados iniciais para desenvolvimento local (H2)
-- Em produção (PostgreSQL/Neon), estes dados NÃO são inseridos automaticamente.

INSERT INTO predios (nome) VALUES ('Bloco A');
INSERT INTO predios (nome) VALUES ('Bloco B');

INSERT INTO laboratorios (nome, predio_id) VALUES ('Lab 01', 1);
INSERT INTO laboratorios (nome, predio_id) VALUES ('Lab 02', 1);
INSERT INTO laboratorios (nome, predio_id) VALUES ('Lab 03', 2);

INSERT INTO computadores (patrimonio, laboratorio_id, status, tempo_inativa_minutos) VALUES ('PC-001', 1, 'DESLIGADO', 10);
INSERT INTO computadores (patrimonio, laboratorio_id, status, tempo_inativa_minutos) VALUES ('PC-002', 1, 'DESLIGADO', 10);
INSERT INTO computadores (patrimonio, laboratorio_id, status, tempo_inativa_minutos) VALUES ('PC-003', 2, 'DESLIGADO', 10);
INSERT INTO computadores (patrimonio, laboratorio_id, status, tempo_inativa_minutos) VALUES ('PC-004', 3, 'DESLIGADO', 10);
