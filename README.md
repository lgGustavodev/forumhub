
# ForumHub

ForumHub é uma API REST para um sistema de fórum, desenvolvida com Spring Boot, Spring Security, JWT, JPA, Flyway e MySQL.

## Funcionalidades

- Cadastro e autenticação de usuários (JWT)
- Criação, listagem e detalhamento de tópicos
- Respostas em tópicos, marcação de solução
- Controle de acesso por roles (USER, ADMIN)
- Documentação automática com Swagger/OpenAPI

## Requisitos

- Java 17+
- Maven
- MySQL

## Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/forumhub.git
   cd forumhub
   ```

2. Configure o banco de dados no arquivo `src/main/resources/application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   ```

3. Execute as migrations do Flyway (automático ao rodar a aplicação).

## Executando

```bash
./mvnw spring-boot:run
```

Acesse a documentação Swagger em:  
[http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

## Endpoints principais

- `POST /api/auth/login` - Login de usuário
- `POST /api/auth/registrar` - Cadastro de usuário
- `GET /api/topicos` - Listar tópicos
- `POST /api/topicos` - Criar tópico
- `GET /api/respostas/topico/{idTopico}` - Listar respostas de um tópico

## Licença

Apache 2.0
