# GUIA DE INTEGRAÇÃO - Museu Virtual do Hardware

## 📌 Para o Professor Orientador

Este documento apresenta o protótipo funcional (Mock) do **Museu Virtual do Hardware** na primeira iteração com a curadoria **MSX**.

### O que foi desenvolvido (Sprint 1-2)

✅ **Infraestrutura Completa:**
- Projeto Maven com Spring Boot 4.0.6
- 7 Entidades JPA mapeadas (Fabricante, Equipamento, Imagem, Documento, Curiosidade, Fonte, Emulador)
- 7 Repositórios com Spring Data JPA
- 7 Services com lógica de negócio e validações
- 7 Controllers REST com CRUD completo
- Tratamento global de exceções

✅ **Backend Funcional:**
- 40+ endpoints REST documentados
- Filtros por geração, fabricante, país
- Validações de entrada
- Resposta em JSON padrão

✅ **Frontend Responsivo:**
- Interface web HTML5/CSS3/JavaScript
- Navegação por abas (Equipamentos, Fabricantes, Emuladores, Detalhes)
- Busca e filtros
- Modal com visualização de detalhes completos
- Integração com API via Fetch
- Design mobile-friendly

✅ **Dados Mock:**
- Script SQL com dados iniciais (fabricantes, equipamentos MSX)
- Suporte a H2 (em memória) e MySQL

### Status: 70% Completo

**Pendente:**
- Testes automatizados (JUnit)
- Documentação Swagger/OpenAPI
- Deploy em Docker
- Refinamento UI/UX

---

## 🔄 Para Colaboradores (Novos Hardwares)

### Passo 1: Preparar seu Ambiente

```bash
git clone <repositório>
cd MVH
mvn install
```

### Passo 2: Estrutura para seu Hardware

Crie um novo pacote dentro de `br.org.museu.hardware`:

```
br/org/museu/hardware/
├── controller/
│   ├── EquipamentoController.java     (EXISTING - MSX)
│   ├── MegaDriveController.java       (NOVO)
│   └── AppleIIController.java         (NOVO)
├── model/
│   ├── Equipamento.java               (EXISTING - MSX)
│   ├── MegaDrive.java                 (NOVO)
│   └── AppleII.java                   (NOVO)
├── repository/
│   ├── EquipamentoRepository.java     (EXISTING - MSX)
│   ├── MegaDriveRepository.java       (NOVO)
│   └── AppleIIRepository.java         (NOVO)
└── service/
    ├── EquipamentoService.java        (EXISTING - MSX)
    ├── MegaDriveService.java          (NOVO)
    └── AppleIIService.java            (NOVO)
```

### Passo 3: Implementar sua Entidade

**Exemplo para Mega Drive:**

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEGA_DRIVES")
public class MegaDrive {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_megadrive;
    
    private String modelo;
    private int ano;
    private String geracao;
    
    @ManyToOne
    @JoinColumn(name = "FK_id_fabricante")
    private Fabricante fabricante;
    
    @OneToMany(mappedBy = "megadrive", cascade = CascadeType.ALL)
    private List<Imagem> imagens;
    
    // ... outros relacionamentos
}
```

### Passo 4: Criar Repository

```java
@Repository
public interface MegaDriveRepository extends JpaRepository<MegaDrive, Long> {
    List<MegaDrive> findByFabricante_IdFabricante(Long idFabricante);
    List<MegaDrive> findByGeracao(String geracao);
}
```

### Passo 5: Criar Service

Copie o padrão de `EquipamentoService.java` e adapte para sua entidade.

### Passo 6: Criar Controller

Copie o padrão de `EquipamentoController.java` e adapte.

### Passo 7: Dados Mock

Adicione no `import.sql`:

```sql
-- Mega Drive
INSERT INTO mega_drives (modelo, ano, geracao, FK_id_fabricante) 
VALUES ('Mega Drive', 1989, 'Gen 16-bit', 1);
```

### Passo 8: Git Workflow

```bash
# Criar branch para seu hardware
git checkout -b feature/mega-drive

# Fazer commits
git add .
git commit -m "feat: adicionar suporte para Mega Drive"

# Push para seu branch
git push origin feature/mega-drive

# Criar Pull Request no GitHub
```

---

## 🧪 Testes Locais

### Testar sua Implementação

1. **Inicie a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

2. **Teste com cURL:**
   ```bash
   curl http://localhost:8080/api/megadrives
   ```

3. **Teste no Frontend:**
   - Abra http://localhost:8080
   - Navigue pelas abas

### Testar Integração

```bash
# Listar todos os recursos
curl http://localhost:8080/api/equipamentos
curl http://localhost:8080/api/megadrives
curl http://localhost:8080/api/appleii

# Verificar se os dados estão corretos
```

---

## 📋 Checklist para Merge

Antes de fazer Pull Request, verifique:

- [ ] Entidade criada com @Entity, @Table, @Data
- [ ] Repository com métodos customizados
- [ ] Service com validações e CRUD
- [ ] Controller com todos os endpoints (GET, POST, PUT, DELETE)
- [ ] Dados mock inseridos no import.sql
- [ ] Código compila sem erros: `mvn clean compile`
- [ ] Endpoints testados e respondendo
- [ ] Segue o padrão de código do projeto (naming, indentação)
- [ ] Sem conflitos com código existente

---

## 🚨 Problemas Comuns

### "A aplicação não inicia"
- Verifique se o MySQL está rodando (ou mude para H2)
- Limpe o cache: `mvn clean`

### "404 em minha API"
- Verifique o mapeamento @RequestMapping
- Teste com cURL antes de integrar ao frontend

### "Erro ao compilar"
- Rode `mvn clean compile -X` para diagnóstico
- Verifique imports de pacotes

### "Frontend não carrega dados"
- Abra o DevTools (F12) e verifique o Network
- Confirme se a API está respondendo

---

## 📞 Suporte

Para dúvidas:
1. Consulte o README.md principal
2. Analise o código de `EquipamentoController` como referência
3. Abra uma issue no repositório

**Boa sorte! 🚀**
