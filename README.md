# Museu Virtual do Hardware - MSX

Projeto acadêmico de um museu virtual interativo para documentar e apresentar hardwares clássicos, iniciando com o **MSX**.

## 📋 Visão Geral

Este projeto implementa um protótipo funcional (Mock) do **Museu Virtual do Hardware** com:

- **Backend**: Spring Boot 4.0.6 + Spring Data JPA + MySQL
- **Frontend**: HTML5, CSS3, JavaScript vanilla
- **Banco de Dados**: MySQL (configurável, com suporte a H2 para testes)
- **Arquitetura**: MVC com separação em Camadas (Model, Repository, Service, Controller)

## 🏗️ Estrutura do Projeto

```
MVH/
├── src/
│   ├── main/
│   │   ├── java/br/org/museu/hardware/
│   │   │   ├── controller/          # Endpoints REST
│   │   │   ├── model/               # Entidades JPA
│   │   │   ├── repository/          # Acesso a dados (Spring Data JPA)
│   │   │   ├── service/             # Lógica de negócio
│   │   │   └── MuseuVirtualHardwareApplication.java
│   │   ├── resources/
│   │   │   ├── static/              # Frontend (HTML, CSS, JS)
│   │   │   ├── application.properties
│   │   │   └── import.sql           # Seed de dados
│   │   └── test/
│   └── .mvn/
├── pom.xml                          # Dependências Maven
└── README.md
```

## 🔧 Configuração Rápida

### Pré-requisitos
- Java 17+
- Maven 3.6+
- MySQL 8.0+ (opcional, usando H2 em memória por padrão)

### Instalação

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/seu-usuario/museu-virtual-hardware.git
   cd MVH
   ```

2. **Instalar dependências**
   ```bash
   mvn install
   ```

3. **Compilar o projeto**
   ```bash
   mvn clean compile
   ```

4. **Executar a aplicação**
   ```bash
   mvn spring-boot:run
   ```

A aplicação estará disponível em: **http://localhost:8080**

## 📚 API REST

### Equipamentos
- `GET /api/equipamentos` - Listar todos os equipamentos
- `GET /api/equipamentos/{id}` - Obter equipamento por ID
- `GET /api/equipamentos/geracao/{geracao}` - Filtrar por geração
- `GET /api/equipamentos/fabricante/{idFabricante}` - Filtrar por fabricante
- `POST /api/equipamentos` - Criar novo equipamento
- `PUT /api/equipamentos/{id}` - Atualizar equipamento
- `DELETE /api/equipamentos/{id}` - Deletar equipamento

### Fabricantes
- `GET /api/fabricantes` - Listar todos
- `GET /api/fabricantes/{id}` - Obter por ID
- `GET /api/fabricantes/pais/{pais}` - Filtrar por país
- `POST /api/fabricantes` - Criar
- `PUT /api/fabricantes/{id}` - Atualizar
- `DELETE /api/fabricantes/{id}` - Deletar

### Imagens
- `GET /api/imagens` - Listar todas
- `GET /api/imagens/{id}` - Obter por ID
- `GET /api/imagens/equipamento/{idEquipamento}` - Listar por equipamento
- `POST /api/imagens` - Criar
- `PUT /api/imagens/{id}` - Atualizar
- `DELETE /api/imagens/{id}` - Deletar

### Documentos
- `GET /api/documentos` - Listar todos
- `GET /api/documentos/{id}` - Obter por ID
- `GET /api/documentos/equipamento/{idEquipamento}` - Listar por equipamento
- `POST /api/documentos` - Criar
- `PUT /api/documentos/{id}` - Atualizar
- `DELETE /api/documentos/{id}` - Deletar

### Curiosidades
- `GET /api/curiosidades` - Listar todas
- `GET /api/curiosidades/{id}` - Obter por ID
- `GET /api/curiosidades/equipamento/{idEquipamento}` - Listar por equipamento
- `POST /api/curiosidades` - Criar
- `PUT /api/curiosidades/{id}` - Atualizar
- `DELETE /api/curiosidades/{id}` - Deletar

### Fontes
- `GET /api/fontes` - Listar todas
- `GET /api/fontes/{id}` - Obter por ID
- `GET /api/fontes/equipamento/{idEquipamento}` - Listar por equipamento
- `POST /api/fontes` - Criar
- `PUT /api/fontes/{id}` - Atualizar
- `DELETE /api/fontes/{id}` - Deletar

### Emuladores
- `GET /api/emuladores` - Listar todos
- `GET /api/emuladores/{id}` - Obter por ID
- `GET /api/emuladores/fabricante/{idFabricante}` - Listar por fabricante
- `POST /api/emuladores` - Criar
- `PUT /api/emuladores/{id}` - Atualizar
- `DELETE /api/emuladores/{id}` - Deletar

## 💾 Configuração do Banco de Dados

### Usando H2 (padrão - em memória)
```properties
# application.properties (já configurado)
spring.datasource.url=jdbc:h2:mem:db_museu
spring.datasource.driver-class-name=org.h2.Driver
```

### Mudar para MySQL

1. **Instale MySQL** e crie um banco:
   ```sql
   CREATE DATABASE museu_virtual_hardware;
   ```

2. **Atualize `application.properties`**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/museu_virtual_hardware
   spring.datasource.username=root
   spring.datasource.password=sua_senha
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
   ```

3. **Reinicie a aplicação** - as tabelas serão criadas automaticamente

## 🧪 Testando a API

### Via Postman/Insomnia

1. Inicie a aplicação
2. Importe a coleção de endpoints acima
3. Teste cada rota

### Via cURL

```bash
# Listar todos os equipamentos
curl http://localhost:8080/api/equipamentos

# Criar novo equipamento
curl -X POST http://localhost:8080/api/equipamentos \
  -H "Content-Type: application/json" \
  -d '{
    "modelo": "Expert XP-800",
    "ano": 1985,
    "geracao": "MSX1",
    "fabricante": {"id_fabricante": 1}
  }'
```

## 🎨 Frontend

O frontend está localizado em `src/main/resources/static/`

- **index.html** - Interface principal
- **script.js** - Lógica de interação
- **style.css** - Estilos responsivos

### Funcionalidades do Frontend

- ✅ Listagem de equipamentos MSX
- ✅ Filtro por geração
- ✅ Visualização de detalhes em modal
- ✅ Listagem de fabricantes
- ✅ Listagem de emuladores
- ✅ Exibição de curiosidades
- ✅ Links para fontes de informação
- ✅ Design responsivo (mobile-friendly)

## 📝 Exemplo de Dados

O arquivo `import.sql` contém dados iniciais:

```sql
-- Fabricantes
INSERT INTO fabricantes (nome, pais) VALUES ('Gradiente', 'Brasil');
INSERT INTO fabricantes (nome, pais) VALUES ('Sharp', 'Japão');

-- Equipamentos
INSERT INTO equipamentos (modelo, ano, geracao, FK_id_fabricante) 
VALUES ('Expert XP-800', 1985, 'MSX1', 1);

-- Curiosidades
INSERT INTO curiosidades (descricao, FK_id_equipamento) 
VALUES ('O Expert foi o microcomputador mais popular do Brasil.', 1);
```

## 🔄 Fluxo de Desenvolvimento

### Semana 1 ✅ (Infraestrutura e Mapeamento)
- ✅ Configuração do ambiente Maven
- ✅ Criação de todas as entidades JPA
- ✅ Implementação dos Repositories
- ✅ Script SQL seed

### Semana 2 🔄 (Lógica de Negócio e API)
- ✅ Services implementados com validações
- ✅ Controllers completos (CRUD)
- ✅ Endpoints para filtros
- ⏳ Tratamento de exceções global
- ⏳ Testes com Postman

### Semana 3 (Frontend e Integração)
- ✅ Frontend melhorado e responsivo
- ⏳ Testes end-to-end
- ⏳ Documentação completa
- ⏳ Preparação para merge com colaboradores

## 🚀 Próximas Melhorias

- [ ] Adicionar camada de tratamento global de exceções (@ControllerAdvice)
- [ ] Implementar autenticação JWT
- [ ] Adicionar testes unitários com JUnit
- [ ] Implementar paginação nos endpoints
- [ ] Adicionar upload de imagens
- [ ] Criar documentação OpenAPI/Swagger
- [ ] Implement cache com Redis
- [ ] Deploy em containerização (Docker)

## 👥 Estrutura para Colaboração

Quando colaboradores adicionarem novos hardwares, a estrutura será:

```
MVH/
├── src/main/java/br/org/museu/hardware/
│   ├── msx/                  # Curadoria MSX (você)
│   ├── megadrive/            # Curadoria Mega Drive (colaborador 1)
│   ├── apple2/               # Curadoria Apple II (colaborador 2)
│   └── shared/               # Código compartilhado
```

## 📄 Licença

Projeto educacional - Faculdade/Universidade

## 📞 Contato

Para dúvidas sobre o projeto, abra uma issue no GitHub.

---

**Desenvolvido com ❤️ para o projeto Museu Virtual do Hardware**

