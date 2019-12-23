# Digivox Challenge
API REST para gerenciamento de uma loja que aluga itens

___

### Como rodar localmente

Clone o repositório no local de sua preferência

##### Pré-requisitos

- Docker;
- Java 11;

##### Instanciando bancos de dados
O projeto utiliza PostgreSQL como banco de dados. Dentro do projeto existem dois contextos de aplicação: Development/Prod, Testes.
Os testes rodam em um banco separado que utilizam do Flyway para fazer as migrações nos bancos.

Para criar o banco de dados vamos utilizar um PostgreSQL container para termos mais praticidade. Para isso, prercisamos instalar uma instância do Postgresql, execute no terminal o script abaixo:
```
docker run -d --name api-server-db -e POSTGRES_USER=digivox -e POSTGRES_PASSWORD=challenge123 -p 5432:5432 postgres:10
```

Com a instância do Postgres criada, precisamos agora criar nossos databases. Para isso, execute os scripts abaixo:

Cria o banco de dados da aplicação
```
docker exec -e PGPASSWORD=challenge123 -d api-server-db createdb --username=digivox --owner=digivox api-server-test 
```

Cria o banco de dados de teste da aplicação

```
docker exec -e PGPASSWORD=challenge123 -d api-server-db createdb --username=digivox --owner=digivox api-server-test 
```

##### Testando a aplicação

Para rodas os testes implementados, abra um terminal no diretório do projeto, dentro da pasta `api-server`.
Execute o script no terminal:

```
./mvnw test
```

Para cada teste, a aplicação limpa e migra os scripts SQL, criando assim as tabelas e suas dependências.