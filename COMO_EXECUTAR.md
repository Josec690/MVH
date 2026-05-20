# 🚀 GUIA RÁPIDO DE EXECUÇÃO

## ⚡ Comece em 3 Passos

### 1️⃣ Compilar
```bash
cd c:\Users\Admin\Desktop\MVH
mvn clean compile
```

### 2️⃣ Executar
```bash
mvn spring-boot:run
```

### 3️⃣ Abrir no Navegador
```
http://localhost:8080
```

---

## 🧪 Testar a API

### Com cURL

```bash
# Listar todos os equipamentos
curl http://localhost:8080/api/equipamentos

# Listar equipamentos MSX1
curl http://localhost:8080/api/equipamentos/geracao/MSX1

# Listar fabricantes
curl http://localhost:8080/api/fabricantes

# Listar emuladores
curl http://localhost:8080/api/emuladores

# Criar novo equipamento
curl -X POST http://localhost:8080/api/equipamentos \
  -H "Content-Type: application/json" \
  -d '{
    "modelo": "Novo MSX",
    "ano": 1990,
    "geracao": "MSX2",
    "fabricante": {"id_fabricante": 1}
  }'
```

### Com Postman

1. Abra Postman
2. Crie uma requisição GET para: `http://localhost:8080/api/equipamentos`
3. Clique em Send
4. Você verá a lista de equipamentos em JSON

---

## 🌐 Frontend

Ao abrir `http://localhost:8080`, você verá:

- **Aba Equipamentos**: Lista de MSX com filtro por geração
- **Aba Fabricantes**: Lista de fabricantes
- **Aba Emuladores**: Lista de emuladores
- **Aba Detalhes**: Visualização detalhada (clique em "Ver Detalhes")

### Interações Possíveis

1. ✅ Clique em "Recarregar" para atualizar
2. ✅ Digite uma geração (ex: MSX1) e clique "Filtrar"
3. ✅ Clique em "Ver Detalhes" para abrir modal
4. ✅ Navegue pelas abas com os botões

---

## 💾 Banco de Dados

### MySQL (padrão do projeto)

1. Crie o banco usando o arquivo `mysql-setup.sql` na raiz do projeto ou execute:
```sql
CREATE DATABASE IF NOT EXISTS museu_virtual_hardware
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

2. Configure as credenciais em `src/main/resources/application.properties` ou via variáveis de ambiente:
```bash
set SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/museu_virtual_hardware?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
set SPRING_DATASOURCE_USERNAME=seu_usuario
set SPRING_DATASOURCE_PASSWORD=sua_senha
```

3. Consulte os dados no MySQL com sua ferramenta preferida:
```sql
SELECT * FROM EQUIPAMENTOS;
SELECT * FROM FABRICANTES;
SELECT * FROM IMAGENS;
SELECT * FROM CURIOSIDADES;
```

---

## 🔧 Configurações Importantes

### Em `application.properties`

**MySQL (produção/desenvolvimento):**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/museu_virtual_hardware?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

---

## 📋 Endpoints Disponíveis

### Equipamentos (8)
```
GET    /api/equipamentos
GET    /api/equipamentos/{id}
GET    /api/equipamentos/geracao/{geracao}
GET    /api/equipamentos/fabricante/{idFabricante}
POST   /api/equipamentos
PUT    /api/equipamentos/{id}
DELETE /api/equipamentos/{id}
```

### Fabricantes (8)
```
GET    /api/fabricantes
GET    /api/fabricantes/{id}
GET    /api/fabricantes/pais/{pais}
POST   /api/fabricantes
PUT    /api/fabricantes/{id}
DELETE /api/fabricantes/{id}
```

### Imagens, Documentos, Curiosidades, Fontes, Emuladores
Cada um com 6 endpoints similares (total de 30)

---

## ⚠️ Troubleshooting

### Erro: "Port 8080 already in use"
```bash
# Mudança de porta em application.properties
server.port=8081
```

### Erro: "Could not find a version of the plugin"
```bash
mvn clean install -U
```

### Erro: "Database connection failed"
- Verificar se o MySQL está rodando
- Conferir URL, usuário e senha configurados
- Criar o banco com `mysql-setup.sql` se ele ainda não existir

### Frontend não carrega dados
- Abrir DevTools (F12)
- Verificar aba Network
- Confirmar se API responde em http://localhost:8080/api/equipamentos

---

## 📊 Estrutura do JSON Retornado

### Equipamento
```json
{
  "id_equipamento": 1,
  "modelo": "Expert XP-800",
  "ano": 1985,
  "geracao": "MSX1",
  "fabricante": {
    "id_fabricante": 1,
    "nome": "Gradiente",
    "pais": "Brasil"
  }
}
```

### Erro
```json
{
  "timestamp": "2026-05-15T18:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Modelo é obrigatório",
  "path": "/api/equipamentos"
}
```

---

## 🎓 Próximos Passos

1. **Testar**: Execute e explore todos os endpoints
2. **Entender**: Analise o código em Controllers e Services
3. **Expandir**: Adicione novo hardware seguindo GUIA_INTEGRACAO.md
4. **Melhorar**: Adicione testes, validações extras, etc

---

## 💬 Dúvidas Comuns

**P: Como adiciono novo hardware?**
R: Leia GUIA_INTEGRACAO.md - tem passo-a-passo completo

**P: Preciso de MySQL?**
R: Sim, a aplicação principal e os testes usam MySQL.

**P: Como testo sem frontend?**
R: Use cURL ou Postman para testar a API

**P: Como vejo os dados no banco?**
R: Use um cliente MySQL, como MySQL Workbench, DBeaver ou a linha de comando.

**P: Posso modificar os dados?**
R: Sim, use POST/PUT/DELETE ou edite import.sql

---

## 🎉 Pronto!

Agora você tem um **Museu Virtual do Hardware funcional!**

- Backend REST completo ✅
- Frontend integrado ✅
- Dados mock ✅
- Documentação ✅

**Boa sorte com sua apresentação! 🚀**
