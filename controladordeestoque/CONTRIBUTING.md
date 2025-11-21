# Contribuição

Este projeto segue um fluxo de trabalho baseado em branches e commits pequenos e atômicos. Use este guia para organizar seu trabalho e manter o histórico limpo.

## Estratégia de Branches

- `main`: protegido; recebe apenas merges revisionados.
- `develop` (opcional): ambiente de integração antes de ir para `main`.
- `feature/<assunto>`: um branch por assunto (setup, domínio, serviços, controllers, etc.).

## Convenção de Mensagens de Commit

- `feat:` nova funcionalidade
- `fix:` correção de bug
- `chore:` manutenção/configuração
- `docs:` documentação
- `test:` testes
- `style:` ajustes visuais/estilo (sem impacto de lógica)
- `refactor:` refatoração sem nova feature

Mensagens devem ser curtas e imperativas, por exemplo: `feat: adiciona CategoriaController`.

## Sequência Recomendada (Backend)

Cada etapa deve ser feita em um branch `feature/*` separado, com um commit principal.

1. **Setup Backend**
   - Branch: `feature/setup-backend`
   - Commit: `chore: inicializa projeto backend e setup básico`
   - Arquivos:
     - `.gitignore`, `LICENSE`, `README.md`, `pom.xml`
     - `src/main/java/com/controladordeestoque/EstoqueApplication.java`
     - `src/main/resources/application.properties`

2. **Modelo de Domínio**
   - Branch: `feature/domain-model`
   - Commit: `feat: adiciona entidades e enums do domínio`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/domain/Categoria.java`
     - `src/main/java/com/controladordeestoque/domain/Produto.java`
     - `src/main/java/com/controladordeestoque/domain/Movimentacao.java`
     - `src/main/java/com/controladordeestoque/domain/enums/{Embalagem,Tamanho,TipoMovimentacao}.java`

3. **Repositórios JPA**
   - Branch: `feature/repositories`
   - Commit: `feat: cria repositórios JPA`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/repository/{CategoriaRepository,ProdutoRepository,MovimentacaoRepository}.java`

4. **Serviços de Domínio**
   - Branch: `feature/services`
   - Commit: `feat: implementa serviços de domínio`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/service/{CategoriaService,ProdutoService,RelatorioService,MovimentacaoService}.java`
     - `src/main/java/com/controladordeestoque/service/NotFoundException.java` (se aplicável)

5. **Controladores REST**
   - Branch: `feature/controllers`
   - Commit: `feat: cria controladores REST`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/controller/{CategoriaController,ProdutoController,MovimentacaoController,RelatorioController}.java`

6. **Tratamento Global de Exceções**
   - Branch: `feature/exception-handling`
   - Commit: `feat: adiciona handler global de exceções`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/controller/GlobalExceptionHandler.java`

7. **Configuração de CORS**
   - Branch: `feature/cors-config`
   - Commit: `chore: configura CORS para desenvolvimento`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/config/WebConfig.java`

8. **Carga de Dados de Exemplo**
   - Branch: `feature/data-loader`
   - Commit: `chore: carrega dados de exemplo no startup`
   - Arquivos:
     - `src/main/java/com/controladordeestoque/config/DataLoader.java`

9. **Perfis e Banco**
   - Branch: `feature/db-profiles`
   - Commit: `chore: adiciona perfis e config de banco`
   - Arquivos:
     - `src/main/resources/application.properties`
     - `src/main/resources/application-mysql.properties`
     - Atualizações de dependências no `pom.xml` se necessário

10. **Documentação e Coleções**
    - Branch: `feature/docs-apis`
    - Commit: `docs: adiciona coleções e documentação de API`
    - Arquivos:
      - `documentacao/README.txt`, `documentacao/postman/*`, `documentacao/insomnia/*`
      - Diagramas em `documentacao/`

11. **Testes**
    - Branch: `feature/tests`
    - Commit: `test: adiciona testes básicos de serviço`
    - Arquivos:
      - `src/test/java/...`

## Sequência Recomendada (Frontend)

Se o frontend estiver em outro diretório/repo:

1. `feature/frontend-setup` — `chore: inicializa frontend React + TS + Vite`
   - `vite.config.ts`, `tsconfig.json`, `index.html`, `src/main.tsx`, `src/App.tsx`, `.env`

2. `feature/frontend-api` — `feat: cria cliente HTTP e serviços`
   - `src/api.ts`, `src/types.ts`, `src/services/*`

3. `feature/frontend-pages` — `feat: adiciona páginas e rotas`
   - `src/features/*Page.tsx`, `src/routes/index.tsx`

4. `feature/frontend-ui-polish` — `style: ajustes de UI e estados`
   - componentes de tabela/lista, carregamento, erro

## Fluxo de Trabalho

Para cada etapa:

```bash
git checkout -b feature/<etapa>
git add <arquivos>
git commit -m "<mensagem>"
git push -u origin feature/<etapa>
```

Abra PR, descreva mudanças, marque checklist e faça merge após revisão.

## Checklist de PR

- [ ] Build passa (compilação e servidor sobem)
- [ ] Endpoints afetados testados
- [ ] Documentação atualizada (se aplicável)
- [ ] Escopo do commit é único (sem misturar assuntos)