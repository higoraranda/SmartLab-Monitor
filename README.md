# SmartLab Monitor

Sistema de gerenciamento de inventário de T.I. para laboratórios acadêmicos, desenvolvido com **Spring Boot**, **H2** e **Vue.js**.

## 📋 Sobre o Projeto

O SmartLab Monitor permite que gestores de T.I. cadastrem e gerenciem a estrutura física dos laboratórios de forma hierárquica — prédios, laboratórios e computadores — através de uma API REST com interface web integrada.

Este repositório contém o **módulo de cadastro e gerenciamento**, que é a base do sistema. As funcionalidades de monitoramento de inatividade e comunicação com agentes locais estão previstas para versões futuras.

---

## 🏗️ Arquitetura

O projeto segue o padrão de camadas do Spring Boot:

```
src/main/java/com/smartlab/monitor/
│
├── SmartlabApplication.java   → Ponto de entrada
│
├── domain/      → Entidades JPA (tabelas do banco)
├── dto/         → Objetos de transferência (Request e Response)
├── repository/  → Acesso ao banco de dados (Spring Data JPA)
├── service/     → Regras de negócio
├── controller/  → Endpoints da API REST
└── config/      → Tratamento global de erros

src/main/resources/
├── application.properties     → Configurações da aplicação
├── data.sql                   → Dados iniciais
└── static/
    ├── index.html             → Frontend Vue.js
    └── css/app.css            → Estilos
```

### Hierarquia de dados

```
Prédio
  └─ Laboratório
       └─ Computador
```

---

## ✅ Funcionalidades

- CRUD completo de **Prédios**, **Laboratórios** e **Computadores**
- Validação de dados com mensagens de erro padronizadas
- Bloqueio de cadastros duplicados (nome/patrimônio)
- Remoção em cascata (remover um prédio remove seus laboratórios e computadores)
- Interface web com Vue.js integrada ao Spring Boot (monólito full stack)
- Documentação automática da API via Swagger
- Console visual do banco H2

---

## 🧱 Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot 3.3.5 | Framework backend |
| Spring Data JPA | Acesso ao banco de dados |
| Spring Validation | Validação de entrada (@NotBlank, @Size) |
| H2 Database | Banco em memória |
| Springdoc OpenAPI | Documentação Swagger |
| Vue.js 3 | Frontend (via CDN) |
| Axios | Requisições HTTP no frontend |

---

## 🚀 Como Rodar

### Pré-requisitos

- Java 17 ou superior
- Maven (ou IntelliJ IDEA com suporte a Maven)

### Passos

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/smartlab-monitor.git
```

**2. Abra no IntelliJ IDEA**
- File → Open → selecione a pasta `smartlab-final`
- O IntelliJ detecta o `pom.xml` automaticamente
- Aguarde o Maven baixar as dependências

**3. Execute a aplicação**
- Abra `SmartlabApplication.java`
- Clique no botão ▶ ao lado do método `main`
- Aguarde a mensagem `Started SmartlabApplication` no console

**4. Acesse no navegador**

| Interface | URL |
|---|---|
| Frontend Vue | http://localhost:8080 |
| Swagger / Docs da API | http://localhost:8080/swagger-ui.html |
| Console H2 | http://localhost:8080/h2-console |

> No H2 Console, use o JDBC URL: `jdbc:h2:mem:smartlabdb`

---

## 🔌 Endpoints da API

### Prédios `/predios`
| Método | Rota | Descrição |
|---|---|---|
| GET | `/predios` | Lista todos os prédios |
| GET | `/predios/{id}` | Busca prédio por id |
| POST | `/predios` | Cadastra novo prédio |
| PUT | `/predios/{id}` | Atualiza um prédio |
| DELETE | `/predios/{id}` | Remove prédio e seus dependentes |

### Laboratórios `/laboratorios`
| Método | Rota | Descrição |
|---|---|---|
| GET | `/laboratorios` | Lista todos os laboratórios |
| GET | `/laboratorios/{id}` | Busca laboratório por id |
| POST | `/laboratorios` | Cadastra novo laboratório |
| PUT | `/laboratorios/{id}` | Atualiza um laboratório |
| DELETE | `/laboratorios/{id}` | Remove laboratório e seus computadores |

### Computadores `/computadores`
| Método | Rota | Descrição |
|---|---|---|
| GET | `/computadores` | Lista todos os computadores |
| GET | `/computadores/{id}` | Busca computador por id |
| POST | `/computadores` | Cadastra novo computador |
| PUT | `/computadores/{id}` | Atualiza um computador |
| DELETE | `/computadores/{id}` | Remove um computador |

### Exemplos de requisição

**Cadastrar prédio:**
```json
POST /predios
{
  "nome": "Bloco A"
}
```

**Cadastrar laboratório:**
```json
POST /laboratorios
{
  "nome": "Lab 01",
  "predioId": 1
}
```

**Cadastrar computador:**
```json
POST /computadores
{
  "patrimonio": "PC-001",
  "laboratorioId": 1
}
```

---

## 💾 Dados Iniciais

Ao iniciar a aplicação, o arquivo `data.sql` popula o banco automaticamente com:

- 2 prédios (Bloco A e Bloco B)
- 3 laboratórios
- 4 computadores

---

## 🗺️ Roadmap

- [ ] Agente local — monitora inatividade do mouse nas máquinas dos laboratórios
- [ ] Comunicação entre agente local e servidor via Sockets TCP ou REST
- [ ] Políticas de desligamento automático por laboratório (tempo de inatividade e horário fixo)
- [ ] Migração do banco H2 para PostgreSQL ou MySQL
- [ ] Autenticação e controle de acesso

---

## 👨‍💻 Autores

Desenvolvido como projeto acadêmico da disciplina de Programação Orientada a Objetos.