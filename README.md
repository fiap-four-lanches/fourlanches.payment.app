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

[TODO: preparar a documentação para rodar com o docker]()

## 4. Testando com insomnia

Para executar as requisições com o insomnia, basta importar o arquivo `insomnia-collection.json` no seu insomnia que a collection
estará pronta para ser usada.

### 4.1. Nota para executar com Insomnia executando pelo Docker

Subindo a aplicação via docker, a mesma se encotrará disponível no localhost:XXXX para ser acessada