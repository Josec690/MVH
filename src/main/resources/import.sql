SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM EMULADORES;
DELETE FROM CURIOSIDADES;
DELETE FROM DOCUMENTOS;
DELETE FROM IMAGENS;
DELETE FROM FONTES;
DELETE FROM EQUIPAMENTOS;
DELETE FROM FABRICANTES;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. Cadastrando Fabricantes
-- Usamos INSERT explÃ­cito de PK para garantir que os FK do DER batam com os IDs do seed.
INSERT INTO FABRICANTES (id_fabricante, nome, pais) VALUES (1, 'Gradiente', 'Brasil');
INSERT INTO FABRICANTES (id_fabricante, nome, pais) VALUES (2, 'Sharp', 'Japão');

-- 2. Cadastrando Equipamentos
-- TambÃ©m fixamos a PK para que FK_id_equipamento nos inserts dependentes seja vÃ¡lido.
INSERT INTO EQUIPAMENTOS (id_equipamento, modelo, ano, geracao, FK_id_fabricante) VALUES (1, 'Expert XP-800', 1985, 'MSX1', 1);
INSERT INTO EQUIPAMENTOS (id_equipamento, modelo, ano, geracao, FK_id_fabricante) VALUES (2, 'Hotbit HB-8000', 1985, 'MSX1', 2);


-- 3. Cadastrando Imagens (FK_id_equipamento 1 = Expert)
-- Imagem remota removida (causava erro de carregamento). Usar imagens locais em /images.
-- VALUES ('.png', '1.2MB', '800x600', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Computer_icon_green.svg/256px-Computer_icon_green.svg.png', 1);

-- 4. Cadastrando Curiosidades
INSERT INTO CURIOSIDADES (descricao, FK_id_equipamento) VALUES ('O Expert foi o microcomputador de 8 bits mais popular do Brasil.', 1);

-- 5. Cadastrando Fontes
INSERT INTO FONTES (nome_site, url_original, FK_id_equipamento) VALUES ('Museu da Computação', 'https://museu.exemplo.com/msx', 1);

-- 6. Cadastrando Documentos
INSERT INTO DOCUMENTOS (titulo, url, FK_id_equipamento) VALUES ('Manual do Usuário Expert', 'https://docs.exemplo.com/manual-expert.pdf', 1);

-- 7. Cadastrando Emuladores (Ligados ao Fabricante no seu DER)
INSERT INTO EMULADORES (nome_emu, versao, FK_id_fabricante) VALUES ('fMSX', '6.0', 1);
INSERT INTO EMULADORES (nome_emu, versao, FK_id_fabricante) VALUES ('BlueMSX', '2.8.2', 2);


-- Added by Copilot: image for Expert XP-800
INSERT INTO IMAGENS (formato, tamanho, resolucao, url, FK_id_equipamento) VALUES ('.jpg', '10,1KB', 'unknown', '/images/MSX-Expert-XP800.jpg', 1);

-- Curiosidades, Fontes e Documentos para Hotbit HB-8000 (equipamento id 2)
INSERT INTO CURIOSIDADES (descricao, FK_id_equipamento) VALUES ('O Hotbit foi conhecido por sua compatibilidade com muitos softwares populares da época e por sua presença no mercado brasileiro.', 2);
INSERT INTO FONTES (nome_site, url_original, FK_id_equipamento) VALUES ('Revista Micro Sistemas', 'https://revistamicrosistemas.exemplo/hotbit', 2);
INSERT INTO DOCUMENTOS (titulo, url, FK_id_equipamento) VALUES ('Manual Hotbit HB-8000', '/documents/hotbit_manual.pdf', 2);
-- imagem local para Hotbit
INSERT INTO IMAGENS (formato, tamanho, resolucao, url, FK_id_equipamento) VALUES ('.jpg', '45KB', 'unknown', '/images/hotbit_hb8000.jpg', 2);

-- 8. Cadastrando um equipamento adicional adicionado pelo usuário
-- Adicionando fabricante Sony (id 3)
INSERT INTO FABRICANTES (id_fabricante, nome, pais) VALUES (3, 'Sony', 'Japão');
INSERT INTO EQUIPAMENTOS (id_equipamento, modelo, ano, geracao, FK_id_fabricante) VALUES (3, 'msx2_body_hitbit_hb-t7', 1987, 'MSX2', 3);
-- Imagem remota fornecida pelo usuário
INSERT INTO IMAGENS (formato, tamanho, resolucao, url, FK_id_equipamento) VALUES ('.jpg.webp', 'unknown', 'unknown', 'https://cdn.suruga-ya.com/database/pics_webp/game/123011357.jpg.webp', 3);
-- Curiosidade sobre o equipamento
INSERT INTO CURIOSIDADES (descricao, FK_id_equipamento) VALUES ('Conhecido como "Communication Terminal", o MSX2 Hit Bit HB-T7 incluía um modem embutido de 300/1200 bps e software de agenda e discagem automática.', 3);
-- Se desejar, adicione entradas para outros equipamentos aqui.

