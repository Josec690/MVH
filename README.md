# Museu Virtual do Hardware - MSX 📚💻

Projeto academico desenvolvido como um museu virtual para catalogar e apresentar informacoes sobre hardwares classicos, com foco inicial em equipamentos da linha MSX. Este projeto é um Projeto Acadêmico na FATEC - Faculdade de Tecnologia da Zona Leste 📚.

A aplicacao combina uma API REST em Spring Boot com uma interface web simples em HTML, CSS e JavaScript vanilla.

## Tecnologias utilizadas

- Java 17
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- HTML5, CSS3 e JavaScript

## Padrao de projeto utilizado

O projeto utiliza o padrao MVC em camadas, comum em aplicacoes Spring Boot.

No MVC, a responsabilidade da aplicacao e dividida entre:

- Model: representa as entidades do dominio e o mapeamento das tabelas do banco.
- View: representa a interface visual acessada pelo usuario.
- Controller: recebe as requisicoes HTTP e devolve as respostas da API.

Alem do MVC, o projeto tambem usa uma separacao em camadas para organizar melhor a regra de negocio e o acesso aos dados:

- Controller: expoe os endpoints REST e trata as entradas HTTP.
- Service: concentra as regras de negocio, validacoes e operacoes principais.
- Repository: faz a comunicacao com o banco por meio do Spring Data JPA.
- Model: contem as entidades JPA que representam as tabelas.
- Resources/static: contem a interface web da aplicacao.

Fluxo principal da aplicacao:

```text
Usuario / Frontend
        |
        v
Controller REST
        |
        v
Service
        |
        v
Repository
        |
        v
Banco de Dados MySQL
```

Esse formato facilita manutencao, testes e evolucao do projeto, porque cada camada tem uma responsabilidade clara.

## Estrutura do projeto

```text
MVH/
|-- .mvn/                         # Configuracoes do Maven Wrapper
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   `-- br/org/museu/hardware/
|   |   |       |-- controller/    # Controllers REST da aplicacao
|   |   |       |-- model/         # Entidades JPA do dominio
|   |   |       |-- repository/    # Repositories Spring Data JPA
|   |   |       |-- service/       # Regras de negocio e validacoes
|   |   |       |-- GlobalExceptionHandler.java
|   |   |       `-- MuseuVirtualHardwareApplication.java
|   |   `-- resources/
|   |       |-- static/            # Frontend: HTML, CSS e JavaScript
|   |       |   |-- index.html
|   |       |   |-- script.js
|   |       |   `-- style.css
|   |       |-- application.properties
|   |       `-- import.sql         # Dados iniciais da aplicacao
|   `-- test/
|       |-- java/                  # Testes automatizados
|       `-- resources/
|           `-- application.properties
|-- mysql-setup.sql                # Script auxiliar para criar banco e usuario
|-- pom.xml                        # Dependencias e configuracao Maven
|-- mvnw
|-- mvnw.cmd
`-- README.md
```

## Principais pacotes

### `controller`

Contem os endpoints REST da aplicacao:

- `EquipamentoController`
- `FabricanteController`
- `ImagemController`
- `DocumentoController`
- `CuriosidadeController`
- `FonteController`
- `EmuladorController`

### `service`

Contem a camada de regra de negocio. E nela que ficam validacoes, buscas, criacoes, atualizacoes e exclusoes antes de acessar o banco.

### `repository`

Contem interfaces que estendem os recursos do Spring Data JPA para acessar o MySQL sem precisar escrever SQL manual para operacoes basicas.

### `model`

Contem as entidades principais do sistema:

- `Equipamento`
- `Fabricante`
- `Imagem`
- `Documento`
- `Curiosidade`
- `Fonte`
- `Emulador`

## Funcionalidades

- Cadastro e consulta de equipamentos MSX.
- Cadastro e consulta de fabricantes.
- Listagem de imagens, documentos, curiosidades e fontes ligadas aos equipamentos.
- Listagem de emuladores ligados aos fabricantes.
- Filtros por geracao, fabricante, pais e equipamento.
- Interface web responsiva em `src/main/resources/static`.
- Tratamento global de excecoes com `GlobalExceptionHandler`.
- Carga inicial de dados com `import.sql`.

## Endpoints da API

### Equipamentos

```http
GET    /api/equipamentos
GET    /api/equipamentos/{id}
GET    /api/equipamentos/geracao/{geracao}
GET    /api/equipamentos/fabricante/{idFabricante}
POST   /api/equipamentos
PUT    /api/equipamentos/{id}
DELETE /api/equipamentos/{id}
```

### Fabricantes

```http
GET    /api/fabricantes
GET    /api/fabricantes/{id}
GET    /api/fabricantes/pais/{pais}
POST   /api/fabricantes
PUT    /api/fabricantes/{id}
DELETE /api/fabricantes/{id}
```

### Imagens

```http
GET    /api/imagens
GET    /api/imagens/{id}
GET    /api/imagens/equipamento/{idEquipamento}
POST   /api/imagens
PUT    /api/imagens/{id}
DELETE /api/imagens/{id}
```

### Documentos

```http
GET    /api/documentos
GET    /api/documentos/{id}
GET    /api/documentos/equipamento/{idEquipamento}
POST   /api/documentos
PUT    /api/documentos/{id}
DELETE /api/documentos/{id}
```

### Curiosidades

```http
GET    /api/curiosidades
GET    /api/curiosidades/{id}
GET    /api/curiosidades/equipamento/{idEquipamento}
POST   /api/curiosidades
PUT    /api/curiosidades/{id}
DELETE /api/curiosidades/{id}
```

### Fontes

```http
GET    /api/fontes
GET    /api/fontes/{id}
GET    /api/fontes/equipamento/{idEquipamento}
POST   /api/fontes
PUT    /api/fontes/{id}
DELETE /api/fontes/{id}
```

### Emuladores

```http
GET    /api/emuladores
GET    /api/emuladores/{id}
GET    /api/emuladores/fabricante/{idFabricante}
POST   /api/emuladores
PUT    /api/emuladores/{id}
DELETE /api/emuladores/{id}
```

## Como executar

### Pre-requisitos

- Java 17 ou superior
- MySQL 8 ou superior
- Maven ou Maven Wrapper do proprio projeto

### Configurar o banco

O projeto usa MySQL por padrao. A aplicacao tenta criar o banco automaticamente quando a URL contem `createDatabaseIfNotExist=true`.

Configuracao padrao em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/museu_virtual_hardware?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

Tambem existe o script `mysql-setup.sql`, que cria o banco `museu_virtual_hardware` e um usuario auxiliar.

### Executar com Maven Wrapper

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Depois de iniciar, acesse:

```text
http://localhost:8080
```

## Testes

Para executar os testes:

```bash
mvnw.cmd test
```

Os testes usam uma configuracao propria em `src/test/resources/application.properties`, apontando para o banco `museu_virtual_hardware_test`.

## Frontend

O frontend fica em:

```text
src/main/resources/static/
```

Arquivos principais:

- `index.html`: estrutura da pagina.
- `style.css`: estilos visuais e responsividade.
- `script.js`: consumo da API e interacoes da tela.

Como os arquivos estao dentro de `resources/static`, o Spring Boot serve a interface automaticamente na raiz da aplicacao.

## Dados iniciais

O arquivo `src/main/resources/import.sql` insere dados de exemplo, incluindo:

- Fabricantes Gradiente e Sharp.
- Equipamentos Expert XP-800 e Hotbit HB-8000.
- Imagem, documento, curiosidade e fonte relacionados a equipamento.
- Emuladores fMSX e BlueMSX.

## Resumo

Este projeto foi estruturado com Spring Boot seguindo MVC em camadas. A API REST fica responsavel pela comunicacao com o frontend, a camada de service concentra as regras de negocio, os repositories acessam o MySQL e as models representam o dominio do museu virtual.

## Como contribuir

Contribuicoes sao bem-vindas! 🙌 Passos rapidos:

1. Fork no GitHub e clone o seu fork localmente.
2. Crie uma branch com nome descritivo: `feature/minha-mudanca` ou `fix/corrige-xyz`.
3. Rode os testes localmente: no Windows `mvnw.cmd test`.
4. Faça commits pequenos e claros; escreva mensagens em portugues ou ingles claras.
5. Abra um Pull Request no repositório original descrevendo a mudanca.

Boas praticas:

- Atualize `src/main/resources/import.sql` se precisar incluir seeds.
- Se adicionar imagens, prefira colocá-las em `src/main/resources/static/images/` e referenciar via URL relativa.
- Execute `mvnw.cmd test` antes de abrir o PR e adicione testes quando aplicavel.

## Quick start (desenvolvimento) 🚀

1. Configurar banco (exemplo Windows + MySQL):

   mysql -u root -p < "C:\\Users\\User\\Documents\\JC\\Projetos\\MVH\\mysql-setup.sql"

2. Importar dados iniciais:

   mysql -u root -p museu_virtual_hardware < "C:\\Users\\User\\Documents\\JC\\Projetos\\MVH\\src\\main\\resources\\import.sql"

3. Executar a aplicacao (Windows):

   mvnw.cmd spring-boot:run

Apos subir, acessar http://localhost:8080

## Testes

Para rodar todos os testes (Windows):

   mvnw.cmd test

No ambiente onde executei (local de desenvolvimento), os testes automatizados executam com sucesso. Se algum teste falhar, verifique as mensagens em `target/surefire-reports/`.

## Relatar problemas

- Use a pagina de Issues do repositório no GitHub para relatar bugs ou sugerir melhorias.
- Inclua passos para reproduzir, logs relevantes e ambiente (SO, Java, MySQL).

## Estilo de codigo

- Java 17, siga convencoes do Google Java Style ou convencoes da equipe.
- Preferir nomes claros para variaveis e metodos; mantenha controllers finos e regras no service.

## Contato

Se precisar de ajuda ou quiser que eu execute checks adicionais (baixar imagens, criar migrations Flyway, criar scripts .bat), diga qual tarefa prefere e eu executo.

