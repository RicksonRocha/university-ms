
## University

Este repositório contém o microserviço responsável pelo gerenciamento de informações acadêmicas do Sistema de Formação e Gestão de Grupos de TCC, desenvolvido como parte do Trabalho de Conclusão de Curso de Tecnologia em Análise e Desenvolvimento de Sistemas - UFPR 2025/01.

Este serviço lida com funcionalidades como eventos, materiais de apoio, notificações, registros de entrada em TCC, linha do tempo e dados relacionados ao TCC em si.

## Tecnologias Utilizadas

- Java 17 + Spring Boot – Backend principal
- Spring Data JPA – Persistência de dados
- WebSocket – Comunicação em tempo real (notificações)
- PostgreSQL – Banco de dados relacional

## Funcionalidades

- Cadastro e consulta de eventos acadêmicos
- Registro e atualização de materiais de apoio
- Gerenciamento de notificações por WebSocket
- Registro de pedidos de ingresso em TCC
- Estruturação da linha do tempo do TCC
- Sincronização de dados de usuários

## Requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- JDK 17 ou superior
- Maven
- PostgreSQL

## Configuração e Execução

### 1. Criar o banco de dados

Antes de iniciar a aplicação, certifique-se de que o PostgreSQL esteja em execução e crie o banco de dados necessário:

```
CREATE DATABASE university;
```

### 2. Configurar as credenciais do banco

No arquivo `src/main/resources/application.properties`, atualize as informações de conexão:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/university
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 3. Iniciar o microserviço

Para iniciar o microserviço, utilize a IDE de sua preferência ou execute o seguinte comando na raiz do projeto:

```
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8081
```

## Estrutura do Projeto

- `controller/` – Contém os endpoints REST da aplicação, como `TccController`, `NotificationController` e `SupportMaterialController`
- `model/` – Contém as entidades JPA, como `Tcc`, `SupportMaterial`, `Notification`, entre outras
- `dto/` – Estrutura de objetos de transferência de dados (Data Transfer Objects)
- `infra/` – Configurações adicionais e serviços auxiliares
- `repository/` – Interfaces JPA para manipulação das entidades
- `websocket/` – Implementações de comunicação em tempo real via WebSocket
- `UniversityApplication.java` – Classe principal de inicialização da aplicação
