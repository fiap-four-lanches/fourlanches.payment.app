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

## 4. Testando com insomnia

Para executar as requisições com o insomnia, basta importar o arquivo `insomnia-collection.json` no seu insomnia que a collection
estará pronta para ser usada.

### 4.1. Nota para executar com Insomnia executando pelo Docker

Subindo a aplicação via docker, a mesma se encotrará disponível no localhost:8080 para ser acessada