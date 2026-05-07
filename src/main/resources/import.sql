-- 1. Cadastrando Fabricantes
INSERT INTO fabricantes (nome, pais) VALUES ('Gradiente', 'Brasil');
INSERT INTO fabricantes (nome, pais) VALUES ('Sharp', 'Japão');

-- 2. Cadastrando Equipamentos (FK_id_fabricante 1 = Gradiente, 2 = Sharp)
INSERT INTO equipamentos (modelo, ano, geracao, FK_id_fabricante) VALUES ('Expert XP-800', 1985, 'MSX1', 1);
INSERT INTO equipamentos (modelo, ano, geracao, FK_id_fabricante) VALUES ('Hotbit HB-8000', 1985, 'MSX1', 2);

-- 3. Cadastrando Imagens (FK_id_equipamento 1 = Expert)
INSERT INTO imagens (formato, tamanho, resolucao, FK_id_equipamento) VALUES ('.png', '1.2MB', '800x600', 1);

-- 4. Cadastrando Curiosidades
INSERT INTO curiosidades (descricao, FK_id_equipamento) VALUES ('O Expert foi o microcomputador de 8 bits mais popular do Brasil.', 1);

-- 5. Cadastrando Fontes
INSERT INTO fontes (nome_site, url_original, FK_id_equipamento) VALUES ('Museu da Computação', 'https://museu.exemplo.com/msx', 1);

-- 6. Cadastrando Documentos
INSERT INTO documentos (titulo, url, FK_id_equipamento) VALUES ('Manual do Usuário Expert', 'https://docs.exemplo.com/manual-expert.pdf', 1);

-- 7. Cadastrando Emuladores (Ligados ao Fabricante no seu DER)
INSERT INTO emuladores (nome_emu, versao, FK_id_fabricante) VALUES ('fMSX', '6.0', 1);
INSERT INTO emuladores (nome_emu, versao, FK_id_fabricante) VALUES ('BlueMSX', '2.8.2', 2);