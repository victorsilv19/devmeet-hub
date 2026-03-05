# DevMeet Hub - Plataforma de Gestão de Eventos Tech

Bem-vindo ao **DevMeet Hub**! Este é um projeto full-stack completo, construído com as melhores práticas de mercado e pensado estrategicamente para ser o **destaque do seu portfólio** para vagas de estágio e nível júnior.

---

## 🎯 1. O Tema do Projeto
**Tema:** Uma plataforma para cadastro, curadoria e listagem de eventos de tecnologia (Tech Meetups, Hackathons, Workshops).
**Projeto:** O sistema permite criar eventos informando dados básicos e um CEP. O sistema inteligentemente busca o endereço detalhado na web e cadastra o evento, exibindo-o em um mural público.

## 💡 2. Por que este tema é ideal para o seu Portfólio?
Muitos candidatos apresentam sistemas de "To-Do List" ou "Controle de Estoque" básicos. O **DevMeet Hub** se destaca porque:
- **Resolve um problema real** de forma elegante.
- Possui **integração com API Externa Real (ViaCEP)**, o que recrutadores adoram ver (mostra que você sabe interagir com outros sistemas).
- Lida com **datas e validações**, um desafio comum do dia a dia.
- Apresenta um **visual de produto**, não de trabalho de faculdade.
- Possui escopo fechado e entendível na hora de explicar na entrevista.

## ⚙️ 3. Principais Funcionalidades
1. **Cadastro de Evento:** Usuário insere Nome, Data, Descrição e CEP.
2. **Auto-complete de Endereço (Backend):** O backend consome a API do ViaCEP para buscar Logradouro, Bairro, Cidade e UF automaticamente antes de salvar no banco.
3. **Listagem de Eventos:** Página inicial que consome a API REST mostrando os próximos eventos disponíveis em cards modernos.
4. **Tratamento Global de Erros:** Respostas padronizadas para erros de validação ou quando um evento não é encontrado.

## 📐 4. Arquitetura do Sistema
A arquitetura segue o modelo **Client-Server (Cliente-Servidor)** com separação estrita de responsabilidades:
- **Frontend (SPA):** Responsável apenas pela interface, navegação e interação com o usuário. Comunica-se exclusivamente via protocolo HTTP (REST).
- **Backend (API REST):** Uma camada agnóstica de apresentação. Recebe requisições via Controllers, processa regras de negócio nos Services, comunica-se com APIs externas e persiste os dados via Repositories.
- **Banco de Dados (Relacional):** O PostgreSQL armazena as informações de forma normalizada, garantindo a integridade referencial.

## 🛠️ 5. Tecnologias Utilizadas e Seus Papéis
*   **Java 17+ e Spring Boot 3:** O ecossistema backend moderno, rápido de configurar e robusto. Usado para expor os Endpoints (`@RestController`).
*   **Maven:** Gerenciador de dependências e automação de build do projeto Java.
*   **Spring Data JPA / Hibernate:** Abstração que executa as queries SQL no banco de dados mapeando objetos (ORM).
*   **PostgreSQL:** Banco de dados robusto, muito usado no mercado corporativo, ótimo para uso e modelagem juntamente com ferramentas como **DBeaver**.
*   **Angular 17+:** Framework Frontend corporativo padrão de mercado. Usado aqui no padrão de *Standalone Components* para renderizar a interface e consumir a API HTTP do Spring Boot.
*   **ViaCEP (API Externa):** Consumida pelo Backend usando `RestTemplate` para enriquecer os dados do evento.

## 🗂️ 6. Estrutura do Backend (Camadas)
O backend foi desenhado seguindo princípios *SOLID* e separação de camadas (*N-Tier*):
*   `controller/` - Recebe as requisições HTTP (POST, GET) e retorna os HTTP Status (200, 201, 404).
*   `service/` - Contém a regra de negócio. Ex: Antes de salvar, busca o endereço no cliente externo.
*   `repository/` - Interfaces do Spring Data JPA para comunicação com banco de Dados PostgreSQL.
*   `dto/` - (Data Transfer Object) - Evita vazar a Entidade de Banco de Dados para a web. Define o que entra e o que sai do Controller.
*   `entity/` - Classes espelho das tabelas do banco de dados (Anotadas com `@Entity`).
*   `client/` (ou `integration/`) - Classes responsáveis por chamar APIs Externas (ex: ViaCepClient).
*   `config/` - Configurações gerais (Ex: CORS para permitir que o Angular consiga conversar com o Spring sem erros no console do navegador).
*   `exception/` - `GlobalExceptionHandler` intercepta erros (ex: Evento não encontrado) e os transforma em JSON amigável para o front.

## 🗂️ 7. Estrutura do Frontend (Angular)
Organizado por domínio (Funcionalidade) visando crescimento do projeto:
*   `src/app/models/` - Interfaces TypeScript (`event.model.ts`).
*   `src/app/services/` - `EventService` usando `HttpClient` do Angular para requisitar os dados ao Spring Boot.
*   `src/app/pages/` - As páginas inteiras (`EventHomeComponent`, `EventCreateComponent`).
*   `src/app/components/` - Pedaços visuais reutilizáveis (`EventCardComponent`, `NavbarComponent`).

## 🗃️ 8. Modelo de Banco de Dados (PostgreSQL)
Modelo focado para apresentação no **DBeaver**.
Tabela Principal: `tb_event`

| Coluna | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | BIGINT PK | Identificador único auto incremental |
| `title` | VARCHAR(100) | Nome do Evento (ex: "Meetup Angular SP") |
| `description` | TEXT | Descrição em detalhes |
| `event_date` | TIMESTAMP | Data e hora em que ocorrerá |
| `cep` | VARCHAR(9) | Informado pelo usuário |
| `street` | VARCHAR(150)| Rua (Vindo da API Externa) |
| `neighborhood`| VARCHAR(100)| Bairro (Vindo da API) |
| `city` | VARCHAR(100)| Cidade (Vindo da API) |
| `state` | VARCHAR(2) | Estado (UF) |

## 🌐 9. Integração com API Externa
**Qual API?** ViaCEP (`https://viacep.com.br/ws/`).
**Onde é consumida?** No **Backend**. Quando o Frontend faz um `POST /api/events` enviando apenas Título, Data, Descrição e CEP, o `EventService` no backend pega esse CEP, faz um GET na API pública, extrai a rua, cidade, etc., monta a Entidade completa e salva no Banco PostgreSQL.

---

## 🌟 12. Diferenciais Deste Projeto (Pontos de Ouro para Entrevistas)
1.  **Uso de DTOs:** Demonstra que você entende o risco de retornar Entidades cruas (*Entities*) contendo IDs internos ou senhas no JSON de resposta.
2.  **CORS Configurado Corretamente:** Resolve de fato um dos problemas práticos que os novatos mais penam com integrações full-stack.
3.  **Tratamento Global de Erros:** O backend não retorna *StackTraces* gigantes em Java quando dá erro, mas sim um JSON limpo, tratado no `@ControllerAdvice`.
4.  **Consumo de API no Backend:** Em vez de fazer o front buscar o CEP direto (o que é mais fácil), colocar essa responsabilidade no backend mostra domínio de *Server-to-Server communication*.

---

## 🚀 Tutorial Passo a Passo Prático (Setup & Execução)

Para começar a desenvolver imediatamente e testar os arquivos gerados, siga este guia.

### 🐘 Passo 1: Configurar Banco PostgreSQL (No DBeaver)
1. Certifique-se de ter o PostgreSQL instalado no computador.
2. Abra seu **DBeaver**.
3. Crie uma conexão PostgreSQL com suas credenciais padrão (geralmente usuário: `postgres`, senha: `postgres`).
4. Execute o script abaixo para criar o banco de dados do projeto:
   ```sql
   CREATE DATABASE devmeet_db;
   ```
   *(O Spring Boot com as configurações abaixo criará a tabela automaticamente para você assim que rodar!)*

### 🍃 Passo 2: Rodar o Backend Spring Boot
Foram gerados arquivos base do backend nesta pasta. O passo a passo ideal e limpo é:
1. Acesse o **Spring Initializr** (`https://start.spring.io/`).
2. Gere um projeto: **Maven**, **Java 17+**, **Spring Boot 3+**.
3. Adicione as dependências: **Spring Web**, **Spring Data JPA**, **PostgreSQL Driver**, **Lombok**, **Validation**.
4. Faça o download, extraia e **substitua os arquivos** pelos gerados nesta pasta `backend/src/main/java...`.
5. Substitua o arquivo `application.properties` com as credenciais do seu banco de dados local.
6. Rode com a sua IDE (IntelliJ / Eclipse / VS Code) ou pelo terminal de onde está o `pom.xml`:
   `mvn spring-boot:run`

### 🅰️ Passo 3: Rodar o Frontend Angular
Para utilizar os arquivos de front gerados:
1. Abra um terminal e tenha o Node.js instalado.
2. Instale o Angular CLI, caso não tenha: `npm install -g @angular/cli`.
3. Crie um novo projeto limpo:
   `ng new devmeet-ui --style=css` (Aceite o modo routing).
4. **Copie as pastas** de componentes (`components`, `pages`, `services`, `models`) geradas aqui na pasta `frontend/src/app` para dentro do seu projeto Angular recém-criado.
5. Para testar o app execute no terminal:
   `ng serve`
6. Vá no navegador: `http://localhost:4200`
