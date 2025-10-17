# AquaSite API

API completa para rede social focada em vida marinha, desenvolvida com Spring Boot e SQL Server.

## Funcionalidades

### Autenticação
- ✅ Registro de usuários
- ✅ Login com validação
- ✅ Criação automática de perfil

### Perfil de Usuário
- ✅ Visualização de perfil
- ✅ Edição de informações (email, sobre mim)
- ✅ Contadores automáticos (posts, seguidores, seguindo)

### Posts
- ✅ Criar posts
- ✅ Listar posts do usuário
- ✅ Deletar posts próprios
- ✅ Contador automático de posts

### Sistema de Seguidores
- ✅ Seguir usuários
- ✅ Parar de seguir
- ✅ Contadores automáticos

## Tecnologias

- **Backend**: Spring Boot 3.1.5
- **Banco de Dados**: SQL Server (Somee.com)
- **ORM**: JPA/Hibernate
- **Java**: 17

## Endpoints Principais

### Autenticação
- `POST /api/cadastro/auth/register` - Registrar usuário
- `POST /api/cadastro/auth/login` - Fazer login

### Usuário
- `GET /api/usuario/perfil/{nome}` - Buscar perfil
- `PUT /api/usuario/atualizar/{id}` - Atualizar perfil
- `PUT /api/usuario/atualizar/nome/{nome}` - Atualizar por nome

### Posts
- `POST /api/usuario/post` - Criar post
- `GET /api/usuario/posts/{nome}` - Buscar posts do usuário
- `DELETE /api/usuario/post/{id}` - Deletar post

### Seguidores
- `POST /api/usuario/seguir` - Seguir usuário

## Configuração

### Banco de Dados
```properties
spring.datasource.url=jdbc:sqlserver://aquabanco.mssql.somee.com:1433;databaseName=aquabanco
spring.datasource.username=aquasitebanco_SQLLogin_1
spring.datasource.password=09102008Lf
```

### CORS
Configurado para aceitar requisições do frontend React (localhost:5173)

## Como Executar

1. Clone o repositório
2. Configure as variáveis de ambiente do banco
3. Execute: `./mvnw spring-boot:run`
4. API estará disponível em `http://localhost:8080`

## Estrutura do Projeto

```
src/main/java/com/aquasite/
├── config/          # Configurações (CORS)
├── controller/      # Controllers REST
├── model/entity/    # Entidades JPA
├── repository/      # Repositories JPA
└── service/         # Lógica de negócio
```

## Entidades

- **Login**: Autenticação básica
- **Usuario**: Perfil completo do usuário
- **Post**: Posts dos usuários
- **Seguidor**: Relacionamento entre usuários

## Frontend Integrado

A API está integrada com um frontend React que consome todos os endpoints e fornece uma interface completa para:
- Login/Registro
- Perfil do usuário
- Feed de posts
- Criação e exclusão de posts
- Sistema de seguidores