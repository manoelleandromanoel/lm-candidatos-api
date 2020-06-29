
## CANDIDATOS API
- API REST para Controlar uma lista de candidatos e contas correntes

## Tecnologias
- Ambiente de desenvolvimento (IDE): [Spring Tool Suite 3](https://github.com/spring-projects/toolsuite-distribution/wiki/Spring-Tool-Suite-3) Version: 3.9.11.RELEASE
- Linguagem de programação: [Java 8](https://www.java.com/pt_BR/download/faq/java8.xml)
- Banco de dados: Foi utilizado o [H2](https://www.h2database.com/html/main.html) que é em memória, caso queira reinicializar os testes basta parar e subir a aplicação novamente. Para acessar o console do H2: http://localhost:9000/h2
- Framework: [SpringBoot](https://spring.io/projects/spring-boot) 2.2.6
- Controle de dependências: [Maven](https://maven.apache.org/)

## Padrões
- [RESTful](https://blog.caelum.com.br/rest-principios-e-boas-praticas/)

## Instalação
- Faça o download e a instalação do [Java](https://www.java.com/pt_BR/download/)
- Instale o [Maven](http://www.matera.com/blog/post/tutorial-instalacao-apache-maven-configuracao-eclipse)

## Executar a aplicação
- Faça o Download do repositório no diretório desejado através do comando 'git clone https://github.com/manoelleandromanoel/lm-candidatos-api.git'
- Abra o prompt de comando (cmd/PowerShell) e rode o comando: mvn clean package
- Posteriormente rode o comando: java -jar target/lm-candidatos-api-1.0.jar
- Para efetuar os testes da aplicação, foi utilizado a ferramenta [Postman](https://www.postman.com/). Caso queira facilitar os testes basta importar no Postman o arquivo que está no diretório: 'docs/LM Candidatos API.postman_collection.json'

## ROTAS DA API

Lista de candidatos
- **@GET** http://localhost:9000/candidatos/

Busca um candidato pelo ID
- **@GET** 'http://localhost:9000/candidatos/{id}'

Adiciona um candidato
- **@POST** 'http://localhost:9000/candidatos'
Com o Json de exemplo: { "nome": "José Maria" }

Deleta um candidato
- **@DELETE** 'http://localhost:9000/candidatos/{id}'

Atualiza um candidato
- **@PUT** 'http://localhost:9000/candidatos/{id}'
Com o Json de exemplo: { "nome": "José Maria DA SILVA" }

Lista todas as contas correntes
- **@GET** 'http://localhost:9000/contacorrente/'

Busca uma conta corrente pelo ID
- **@GET** 'http://localhost:9000/contacorrente/{id}'

Adiciona uma conta corrente
- **@POST** 'http://localhost:9000/contacorrente/'
Com o Json de exemplo: { "banco": "Banco do Brasil", "agencia": 12345, "numeroConta": "12345678-9", "candidato": { "id": 1 } }

Deleta uma conta corrente
- **@DELETE** 'http://localhost:9000/contacorrente/{id}'

Atualiza uma conta corrente
- **@PUT** 'http://localhost:9000/candidatos/{id}'
Com o Json de exemplo: { "banco": "Banco do Brasil DE TESTE", "agencia": 555, "numeroConta": "12345678-99999"}
