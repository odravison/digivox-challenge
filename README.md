# Digivox Challenge
API REST para gerenciamento de uma loja que aluga itens.

##### Sumário do que foi implementado

- Manter Tipo de item; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__
- Manter Item; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__
- Manter cliente; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__
- Reservar de item; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__
- Cancelar reserva; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__
- Alugar item; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__
- Devolução de item; __BACKEND + TESTES DE INTEGRAÇÃO (OK)__

Dashboard com informações sobre:
  - Itens a serem devolvidos no período semanal, com seus valores;
  - Itens alugados no período semanal, com seus valores;

___

### Como rodar localmente

Clone o repositório no local de sua preferência

##### Pré-requisitos

- Docker;
- Java 11;

##### Instanciando bancos de dados
O projeto utiliza PostgreSQL como banco de dados. Dentro do projeto existem dois contextos de aplicação: Development/Prod, Testes.
Os testes rodam em um banco separado que utilizam do Flyway para fazer as migrações nos bancos.

Para criar o banco de dados vamos utilizar um PostgreSQL container para termos mais praticidade. Para isso, prercisamos rodar uma instância do Postgresql, execute no terminal o script abaixo:
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

NOTA: é de extrema importância que sua `JAVA_HOME` esteja devidamente configurada, apontando para a pasta do JAVA 11.
Caso contrário, você pode rodar os testes em sua IDE de preferência.

### Considerações finais
Antes de mais nada, quero agradecer a oportunidade e o tempo na realização das etapas da entrevista.

Por um contra-tempo a parte do front-end não pôde ser implementada, o tempo não foi sucifiente. Entretanto, é possível observar projetos de minha autoria usando ReactJS na minha conta do github.