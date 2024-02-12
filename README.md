
#   Sistema de Gerenciamento de Estacionamento

Este repositório contém o código-fonte para uma API desenvolvida em Spring Boot para gerenciar as operações de um estacionamento. O objetivo deste projeto é fornecer uma plataforma robusta e eficiente para controle de entrada e saída de veículos, alocação de vagas, registro de clientes e administração geral do estabelecimento de estacionamento. A API foi desenvolvida utilizando o framework Spring Boot, o que proporciona uma estrutura sólida e altamente escalável para construir aplicativos Java baseados em microserviços. Com uma arquitetura RESTful, esta API oferece um conjunto abrangente de endpoints para interação com os diferentes recursos do sistema de gerenciamento de estacionamento.

### Tecnologias

-   **Spring Boot**: Framework Java para criação de aplicativos independentes e prontos para produção.
-   **Maven**: Gerenciador de dependências e construção de projetos para Java.
-   **Hibernate**: Framework de mapeamento objeto-relacional (ORM) para Java.
-   **JPA (Java Persistence API)**: Especificação padrão do Java para mapeamento objeto-relacional.
-   **Lombok**: Biblioteca que automatiza a geração de código repetitivo em Java.

## Funcionalidades Principais

### 1. Requisitos e Configurações

-   Configuração do Timezone e Locale do país
-   Implementação de um sistema de auditoria para registro de criação e modificação de registros
-   Configuração do acesso a uma base de dados para o ambiente de desenvolvimento

### 2. Requisitos / Usuários

-   Cadastro de usuários com e-mail único e senha de 6 caracteres
-   Perfis de usuário: administrador ou cliente
-   Recuperação de usuários por ID
-   Autenticação de usuários para acessar recursos específicos
-   Alteração de senha pelo próprio usuário
-   Listagem de todos os usuários (apenas administrador)
-   Testes de integração para os recursos criados

### 3. Requisitos / Autenticação

-   Implementação de autenticação com JSON Web Token (JWT)
-   Documentação do recurso de autenticação
-   Testes sobre o sistema de autenticação

### 4. Requisitos / Clientes

-   Cadastro de clientes vinculado a um usuário
-   Autenticação obrigatória para todas as ações
-   Cadastro de clientes com nome completo e CPF único
-   Listagem de todos os clientes (apenas administrador)
-   Testes de integração para os recursos criados

### 5. Requisitos / Vagas

-   Gerenciamento de vagas com código único e status de livre ou ocupada
-   Localização de vagas por código
-   Testes de integração para os recursos criados

### 6. Requisitos / Estacionamentos

-   Controle de entradas e saídas de veículos
-   Registro de informações do veículo, incluindo placa, marca, modelo, cor, data de entrada e CPF do cliente
-   Geração de número de recibo único para cada entrada
-   Concessão de desconto percentual após 10 estacionamentos completados
-   Associação de estacionamentos a vagas e clientes
-   Localização de estacionamentos por número de recibo
-   Testes de integração para os recursos criados
