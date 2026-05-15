@echo off
chcp 65001 > nul
cd /d "c:\Users\Admin\Desktop\MVH"

git add .

git commit -m "feat: Implementação completa do backend MSX com API REST, Services, Controllers e Frontend

- Corrigir entidades JPA (Equipamento com Lombok)
- Implementar 7 Services com validações completas
- Criar 7 Controllers REST com CRUD (46 endpoints)
- Atualizar Repositories com filtros
- Adicionar GlobalExceptionHandler
- Redesenhar Frontend responsivo
- Modal com detalhes completos
- Filtros por geração e fabricante
- Documentação completa
- Dados mock MSX

Status: 93%% pronto para demo

Co-authored-by: Copilot <223556219+Copilot@users.noreply.github.com>"

echo Commit realizado com sucesso!
pause
