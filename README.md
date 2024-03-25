# Fourlanches - Microsserviço de Pagamento

Este é o microsserviço responsável por lidar com o pagamento dos pedidos do sistema Fourlanches. Ele irá gerar a intenção
de pagamento, enviar a intenção para o gateway de pagamentos, receber o resultado, processar e notificar os microsserviços
de pedidos e da cozinha (produção).

## 1. Tecnologias

* Java 17
* Spring Boot
* Postgres SQL
* Docker
* Swagger
* Kubernetes - K8S

## 2. Pre-requisitos para rodar a aplicação

* Java Version: 17
* Gradle
* IDE IntelliJ (Ou outra ide preparada para aplicações Java e Spring Boot)
* Docker
* Kubernetes - K8S

## 3. Para executar localmente com Docker

Nota, durante o desenvolvimento, se estiver usando o usuário root do mongo, é preciso adicionar `?authSource=admin` ao final
da uri de conexão.

#### 3.1. Para buildar, executar as migration e rodar o app no docker pela primeira vez

`docker compose up --build -d`

#### 3.2. Para executar o app com docker após a primeira vez
Após rodar a primeira o comando acima, execute o seguinte comando abaixo para que apenas execute
os containers sem a etapa de build e migration.

`docker compose up -d fourlanches-payment-service mongodb`

#### 3.3. Necessário um arquivo .env na raiz do projeto com a seguinte conteúdo:
```
DATABASE_USERNAME=username
DATABASE_PASSWORD=password
DATABASE_AUTH=auth
DATABASE_BASE=database
```

Para isso só copiar o arquivo `.env.example` e renomear a cópia para `.env`

#### 3.4. Preparando o RabbitMQ
Após subir todos os containeres necessários, é preciso realizar os seguintes passos:
1. Acessar o rabbitmq no `localhost:15672` (configuração padrão) com  usários e senha configurados no arquivo `.env.`
2. Após logado é necessário criar as 3 filas e o exchange, usando os mesmos  valores das variaveis usadas no arquivo `.env`
3. E por final, deve ser criado os bindings no exchange criado anteriormente
    * Os binds criados devem: ser vazio (bind default) e um com o seguinte valor: `order.status.update`
> Observação: em caso de subir as tres aplicações localmente, deverá ser usado apenas uma instancia do rabbitmq no docker,
> ou um serviço online, como o cloudamqp.

## 4. Testando

### 4.1 Testando com insomnia

Para executar as requisições com o insomnia, basta importar o arquivo `insomnia-collection.json` no seu insomnia que a collection
estará pronta para ser usada.

### 4.2 Testando com Swagger
Basta acessar a url do swagger disponivel em `http://localhost/swagger-ui/index.htm`

### 4.1. Nota para executar localmente pelo Docker

Subindo a aplicação via docker, a mesma se encotrará disponível no localhost:8080 para ser acessada