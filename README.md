# SOAT Tech Challenge Fast Food

## Integrantes:
- Karen Lais Martins Pontes de Fávere Orrico:
   - RM361158
   - Discord: Karen Pontes
- Raphael Oliver:
   - RM362129
   - Discord: Raphael Oliver - RM362129
- Carlos Eduardo Bastos Laet:
   - RM361151
   - Discord: CarlosLaet
- Lucas Martins Barroso:
   - RM362732
   - Discord: Lucas Barroso - RM362732

## Desenho da Arquitetura - Requisitos do Negócio

> - Arquivo do desenho da arquitetura de negócio: [Tech Challenge.excalidraw](../../../Users/Carlos/Downloads/Tech%20Challenge.excalidraw)

![img.png](img.png)

![img_1.png](img_1.png)

![img_2.png](img_2.png)

![img_3.png](img_3.png)

![img_4.png](img_4.png)

## Build das imagens

> ⚠️ **Pré requisitos**
> - AWS CLI: [Instalação](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
> - Docker: [Instalação](https://docs.docker.com/engine/install/)

As imagens da aplicação já estão publicadas em um repositório no Elastic Container Registry (ECR - AWS). Para realizar o push de novas imagens, primeiro realize os builds e crie as tags necessárias:

```sh
# Banco de dados (PostgresSQL)
docker build -t fiap-soat-techchallenge-db:latest . -f Dockerfile-db
docker tag fiap-soat-techchallenge-db:latest public.ecr.aws/p6c0d2v5/fiap-soat-techchallenge-db:latest
```

```sh
# Broker (RabbitMQ)
docker build -t fiap-soat-techchallenge-broker:latest . -f Dockerfile-broker
docker tag fiap-soat-techchallenge-broker:latest public.ecr.aws/p6c0d2v5/fiap-soat-techchallenge-broker:latest
```

```sh
# Aplicação
docker build -t fiap-soat-techchallenge:latest . -f Dockerfile-app
docker tag fiap-soat-techchallenge:latest public.ecr.aws/p6c0d2v5/fiap-soat-techchallenge:latest
```

Em seguida, faça login:

```sh
aws configure
aws ecr-public get-login-password --region us-east-1
aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws
```

Por fim, faça o push:

```sh
# Banco de dados
docker push public.ecr.aws/p6c0d2v5/fiap-soat-techchallenge-db:latest
```

```sh
# Broker
docker push public.ecr.aws/p6c0d2v5/fiap-soat-techchallenge-broker:latest
```

```sh
# Aplicação
docker push public.ecr.aws/p6c0d2v5/fiap-soat-techchallenge:latest
```

## Implantação com Kubernetes

A aplicação está pronta para ser implantada em um cluster de Kubernetes. Para isso, basta utilizar os YAMLs presentes na pasta `k8s`. Esses arquivos estão organizados por aplicação: `app` (API REST), `db` (PostgresSQL), `broker` (RabbitMQ). Em cada pasta há um arquivo para cada tipo de recurso do Kubernetes, como `deployment.yaml`, `configmap.yaml`, entre outros. Vamos apresentar o processo de implantação no Elastic Kubernetes Service (EKS), da AWS.

> ⚠️ **Pré requisitos**
> - AWS CLI: [Instalação](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
> - EKSCTL: [Instalação](https://eksctl.io/installation/)
> - Kubectl: [Instalação](https://kubernetes.io/docs/tasks/tools/#kubectl)

### Passo a passo

1. Configure suas credenciais AWS:
    ```sh
    aws configure
    ```

2. Crie o cluster, o control plane e os nodes:
    ```sh
    eksctl create cluster --name tech-challenge-eks-01 --region us-east-1
    ```

3. Conecte o kubectl ao cluster:
    ```sh
    aws eks update-kubeconfig --name tech-challenge-eks-01 --region us-east-1
    ```

4. Clone o repositório do projeto:
    ```sh
    # via SSH
    git clone git@github.com:KarenL19/soat-techchallenge.git

    # via HTTPS
    git clone https://github.com/KarenL19/soat-techchallenge.git

    # acesse a pasta do repositório
    cd soat-techchallenge
    ```

5. Aplique os manifestos na seguinte ordem:
   1. Namespace:
      ```sh
      kubectl apply -f k8s/namespace.yaml
      ```

   2. Secrets do banco de dados:
      ```sh
      kubectl apply -f k8s/db/secret.yaml
      ```

   3. Deployment do banco de dados:
      ```sh
      kubectl apply -f k8s/db/deployment.yaml
      ```

   4. Service do banco de dados (ClusterIP):
      ```sh
      kubectl apply -f k8s/db/service.yaml
      ```
    
   5. Deployment do broker:
      ```sh
      kubectl apply -f k8s/broker/deployment.yaml
      ```

   6. Service do broker (ClusterIP):
      ```sh
      kubectl apply -f k8s/broker/service.yaml
      ```

   7. Service da aplicação (LoadBalancer), para obter o IP público:

      ```sh
      # cria o service
      kubectl apply -f k8s/app/service.yaml

      # obtém o IP público da aplicação
      kubectl get service --namespace tech-challenge
      ```

      Exemplo de saída:
      ```
      NAME          TYPE           CLUSTER-IP      EXTERNAL-IP                       PORT(S)        AGE
      app-service   LoadBalancer   10.100.4.138    XXX.us-east-1.elb.amazonaws.com   80:31387/TCP   20s
      ```

   8. Edite o `configmap` com o IP obtido acima no arquivo `k8s/app/configmap.yaml`:
      ```yaml
          data:
            MERCADO_PAGO_CALLBACK_URL: http://XXX.us-east-1.elb.amazonaws.com/soat-fast-food/payment/notifications/mercado-pago
      ```

   9. Aplique o configmap da aplicação:
      ```sh
      kubectl apply -f k8s/app/configmap.yaml
      ```

   10. Secrets da aplicação:
      ```sh
      kubectl apply -f k8s/app/secret.yaml
      ```

   11. Deployment da aplicação:
      ```sh
      kubectl apply -f k8s/app/deployment.yaml
      ```

   12. Horizontal Pod Autoscaler (HPA) da aplicação:
      ```sh
      kubectl apply -f k8s/app/hpa.yaml
      ```

6. Consulte os pods:
    ```sh
    kubectl get pods --namespace tech-challenge
    ```

7. Consulte o HPA:
    ```sh
    kubectl get hpa --namespace tech-challenge
    ```

### Limpando recursos

Para excluir todos os recursos e evitar cobranças na AWS:
```sh
eksctl delete cluster --name tech-challenge-eks-01 --region us-east-1
```

---
## 📁 Identification

A identificação de clientes tem como objetivo permitir o cadastro utilizando nome, CPF e e-mail, além de possibilitar a identificação de clientes já existentes por meio do CPF ou e-mail. Caso prefira, o usuário pode prosseguir com o pedido de forma anônima, sem a necessidade de se cadastrar ou se identificar.

Na etapa de identificação do usuário, existe também o processo de autenticação por meio de login. Esse processo gera um token JWT (JSON Web Token), que é utilizado para garantir a autenticação nas etapas seguintes da integração com o sistema.


### 📌 Authentication

#### `POST /auth/login`
Realiza o login/autenticação do usuário.

**Request:**
```json
{
  "documentNumber": "12345678900",
  "email": "usuario@email.com"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJpZF9jbGllbnQiOiJiZmE2MzhjNS04OGI5LTQ3ZTQtOTAzMC1hYjk3ZjY5ODI4ZjUiLCJkb2N1bWVudE51bWJlciI6InVzdWFyaW9AZW1haWwuY29tIiwiZW1haWwiOiI0Mzc2NDY2OTgwMiIsInN1YiI6IjQzNzY0NjY5ODAyIiwiaWF0IjoxNzQ4NjQyMjMwLCJleHAiOjE3NDg2NDU4MzB9.QkmNDmpHx6G5mjrwqIGRBPW5iN0W_a9HFR_DKTuiA9Y"
}
```
---
### 📌 Identification
Gerencia operações relacionadas à identificação de clientes.

#### `POST /identifications`
Cria um novo cliente.

**Request:**
```json
{
  "numberDocument": "12345678909",
  "email": "usuario@email.com",
  "nameClient": "Nome do Usuário"
}
```
**Response:**
- 201

**Erros:**
```json
{
    "errorCode": "020",
    "message": "Cliente já cadastrado",
    "uuid": "5d3f218d-acfe-4cfc-b1c1-e77fd348c9ee",
    "statusCode": 400,
    "timestamp": "2025-05-30T22:01:23.969382961"
}
```

#### `GET /identifications/{identification_id}`
Busca um cliente pelo número do documento ou e-mail.

**Response:**
```json
{
    "id": "bfa638c5-88b9-47e4-9030-ab97f69828f5",
    "nameClient": "Nome do Usuário",
    "numberDocument": "usuario@email.com",
    "email": "12345678909",
    "createdAt": "2025-05-30T01:43:23.649965",
    "updatedAt": "2025-05-30T01:43:23.650003"
}
```

**Erros:**
```json
{
    "errorCode": "010",
    "message": "Cliente não encontrado",
    "uuid": "6a86bcc6-1dc5-42b6-a72e-850297746949",
    "statusCode": 400,
    "timestamp": "2025-05-30T22:04:38.019863218"
}
```

---

## 📁 Category

Responsável pela gestão das **categorias de produtos** no cardápio, como “Lanches”, “Bebidas” ou “Sobremesas”. As categorias são essenciais para organização e agrupamento dos produtos, permitindo uma navegação intuitiva no menu.

> ⚠️ **Importante:** Ao **deletar uma categoria**, **todos os produtos associados a ela também serão automaticamente excluídos**. Essa ação é irreversível e afeta os dados de forma permanente.


---

### 📌 Endpoints

#### `POST /category`
Cria uma nova categoria de produtos.

**Request:**
```json
{
  "categoryName": "LANCHES"
}
```

**Response:**
```json
{
  "message": "Categoria criada com sucesso"
}
```
**Status:**

- 201 Created
- 422 Error

**Exemplo de erro:**
```json
{
    "errorCode": "422",
    "message": "Categoria já existe",
    "uuid": "c55a7992-71fa-42f5-94ef-5760f21c7f5b",
    "statusCode": 422,
    "timestamp": "2025-05-29T23:53:57.676239136"
}
```

#### `GET /category`
Lista categorias existentes com suporte a paginação. Caso

**Query Params** (opcionais):
- limit (padrão: 10)
- offset (padrão: 0)
- page (padrão: 0)

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "categoryName": "LANCHES",
      "dateInclusion": "2025-05-29",
      "timestamp": "2025-05-29T21:43:35.015+00:00"
    },
    {
      "id": 2,
      "categoryName": "ACOMPANHAMENTOS",
      "dateInclusion": "2025-05-29",
      "timestamp": "2025-05-29T21:43:49.314+00:00"
    },
    {
      "id": 3,
      "categoryName": "BEBIDAS",
      "dateInclusion": "2025-05-29",
      "timestamp": "2025-05-29T21:44:00.514+00:00"
    },
    {
      "id": 4,
      "categoryName": "SOBREMESAS",
      "dateInclusion": "2025-05-29",
      "timestamp": "2025-05-29T21:44:10.040+00:00"
    },
    {
      "id": 5,
      "categoryName": "MOLHOS E ADICIONAIS",
      "dateInclusion": "2025-05-29",
      "timestamp": "2025-05-29T21:44:24.669+00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 5,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 5,
  "first": true,
  "empty": false
}
```
**Status:**

- 200
- 404 Not Found

**Exemplo de erro:**
```json
{
    "errorCode": "404",
    "message": "Nenhuma categoria encontrada",
    "uuid": "3bf82b20-209b-43cd-89f6-0d1f45d20e2b",
    "statusCode": 404,
    "timestamp": "2025-05-30T00:02:52.747589884"
}
```

#### `GET /category/{id}`
Busca uma categoria específica pelo ID.

**Response:**
```json
{
  "id": 3,
  "categoryName": "BEBIDAS",
  "dateInclusion": "2025-05-29",
  "timestamp": "2025-05-29T21:44:00.514+00:00"
}
```
**Exemplo de erro:**
```json
{
    "errorCode": "404",
    "message": "Nenhuma categoria encontrada",
    "uuid": "3bf82b20-209b-43cd-89f6-0d1f45d20e2b",
    "statusCode": 404,
    "timestamp": "2025-05-30T00:02:52.747589884"
}
```

#### `PUT /category/{id}`
Atualiza o nome de uma categoria existente.

**Request:**
```json
{
  "categoryName": "Acompanhamentos"
}
```

**Response:**
```json
{
  "message": "Categoria atualizada com sucesso"
}
```
**Status:**

- 200
- 404 Not Found

**Exemplo de erro:**
```json
{
    "errorCode": "404",
    "message": "Categoria não encontrada",
    "uuid": "e3ac909b-8e8d-4f46-b6b9-f02b497c99fa",
    "statusCode": 404,
    "timestamp": "2025-05-30T00:18:59.102626345"
}
```

#### `DELETE /category/{id}`
Remove uma categoria e todos os produtos vinculados a ela.

**Response:**
```json
{
    "message": "Categoria deletada"
}
```
**Status:**

- 200
- 404 Not Found

**Exemplo de erro:**
```json
{
    "errorCode": "404",
    "message": "Categoria não encontrada",
    "uuid": "e3ac909b-8e8d-4f46-b6b9-f02b497c99fa",
    "statusCode": 404,
    "timestamp": "2025-05-30T00:18:59.102626345"
}
```
#### `GET /category/{id}/products`
Retorna a lista de produtos vinculados a uma categoria específica.

**Response (quando há produtos cadastrado para a categoria):**
```json
{
  "categoriaId": 3,
  "nomeCategoria": "BEBIDAS",
  "produtos": [
    {
      "productId": 12,
      "nameProduct": "Refrigerante",
      "unitPrice": 7.50,
      "preparationTime": 0,
      "dtInclusion": "2024-05-01"
    }
  ]
}
```
**Response (quando não há produtos cadastrado para a categoria):**
```json
{
  "categoriaId": 3,
  "nomeCategoria": "BEBIDAS",
  "produtos": []
}
```

**Status:**

- 200
- 400 BAD REQUEST
- 404 Categoria em questão não encontrada

**Exemplo de erro:**
```json
{
    "errorCode": "400",
    "message": "Erro ao pesquisar produtos por categoria",
    "uuid": "e3ac909b-8e8d-4f46-b6b9-f02b497c99fa",
    "statusCode": 400,
    "timestamp": "2025-05-30T00:18:59.102626345"
}
```

```json
{
    "errorCode": "404",
    "message": "Categoria não encontrada",
    "uuid": "9d8ae761-1fe3-41a0-bf96-f237006fdee9",
    "statusCode": 404,
    "timestamp": "2025-05-30T00:55:51.959757055"
}
```
----

## 📁 Product

Responsável pela gestão dos **produtos** disponíveis no cardápio, como hambúrgueres, bebidas, sobremesas e acompanhamentos. Cada produto pode pertencer a uma categoria específica.

> ⚠️ **Importante:** Produtos devem estar associados a uma **categoria válida** no momento da criação. Um produto não pode existir sem uma categoria.

---

### 📌 Endpoints

#### `POST /product`
Cria um novo produto.

**Request:**
```json
{
  "nameProduct": "X-Burguer",
  "unitPrice": 22.90,
  "preparationTime": 10,
  "categoryId": 1
}
```

**Response:**
```json
{
  "message": "Produto criado com sucesso"
}
```

**Status:**

- 201 Created
- 422 Unprocessable Entity

**Exemplo de erro:**
```json
{
  "errorCode": "422",
  "message": "Produto já existe",
  "uuid": "f2d9841f-38d0-45a5-bb92-b43bc86f435a",
  "statusCode": 422,
  "timestamp": "2025-05-30T10:15:47.102Z"
}
```

---

#### `GET /product`
Lista todos os produtos com suporte a paginação.

**Query Params** (opcionais):
- `limit` (padrão: 10)
- `offset` (padrão: 0)
- `page` (padrão: 0)

**Response:**
```json
{
  "content": [
    {
        "id": 1,
        "nameProduct": "BIG BACON DUPLO",
        "idCategory": 1,
        "unitPrice": 20.00,
        "preparationTime": 5
    },
    {
        "id": 2,
        "nameProduct": "BATATA FRITA",
        "idCategory": 2,
        "unitPrice": 20.00,
        "preparationTime": 5
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true
  },
  "totalElements": 2,
  "totalPages": 1,
  "numberOfElements": 2
}
```

**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
  "errorCode": "404",
  "message": "Nenhum produto encontrado",
  "uuid": "38a3725a-29f4-4b13-b31e-8dfd702a27e1",
  "statusCode": 404,
  "timestamp": "2025-05-30T11:00:00.000Z"
}
```

---

#### `GET /product/{id}`
Busca um produto específico pelo ID.

**Response:**
```json
{
    "id": 1,
    "nameProduct": "BIG BACON DUPLO",
    "idCategory": 1,
    "unitPrice": 20.00,
    "preparationTime": 5
}
```

**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
  "errorCode": "404",
  "message": "Produto não encontrado",
  "uuid": "91e6e09d-f54a-4218-943a-22e8cc34ad03",
  "statusCode": 404,
  "timestamp": "2025-05-30T11:12:01.456Z"
}
```

---

#### `PUT /product/{id}`
Atualiza um produto existente.

**Request:**
```json
{
  "nameProduct": "X-Burguer Especial",
  "unitPrice": 25.90,
  "preparationTime": 12,
  "categoryId": 1
}
```

**Response:**
```json
{
  "message": "Produto atualizado com sucesso"
}
```

**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
  "errorCode": "404",
  "message": "Produto não encontrado",
  "uuid": "b6b5f9f2-dc3c-4a42-b25d-8d81cb7b0952",
  "statusCode": 404,
  "timestamp": "2025-05-30T11:20:00.000Z"
}
```

---

#### `DELETE /product/{id}`
Remove um produto pelo ID.

**Response:**
```json
{
  "message": "Produto removido com sucesso"
}
```

**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
  "errorCode": "404",
  "message": "Produto não encontrado",
  "uuid": "a9cfecb7-e3b0-4a03-8eb3-9e1c9876c5a4",
  "statusCode": 404,
  "timestamp": "2025-05-30T11:25:34.123Z"
}
```

---
# 📦 Order

Responsável pelo gerenciamento de **pedidos realizados** no sistema, incluindo a criação, consulta, atualização e exclusão de pedidos.

---

### 📌 Endpoints

#### `POST /order`
Cria um novo pedido.

**Request:**
```json
{
    "clientId": "123e4567-e89b-12d3-a456-426614174000",
    "products": [
        {
            "productId": 1,
            "vlUnitProduct": 50.0,
            "preparationTime": 100
        },
        {
            "productId": 2,
            "vlUnitProduct": 10.0,
            "preparationTime": 100
        },
        {
            "productId": 2,
            "vlUnitProduct": 10.0,
            "preparationTime": 120
        }
    ]
}
```

**Response:**
```json
{
    "orderId": "A001",
    "totalOrder": 70.0,
    "preparationTime": 320,
    "clientId": "123e4567-e89b-12d3-a456-426614174000",
    "timestamp": "2025-05-30T01:18:06.339+00:00",
    "products": [
        {
            "productId": 1,
            "quantity": 1,
            "vlUnitProduct": 50.0
        },
        {
            "productId": 2,
            "quantity": 2,
            "vlUnitProduct": 20.0
        }
    ]
}
```
**Status:**

- 201 Created
- 400 Bad Request
- 422 Unprocessable Entity

**Exemplo de erro:**
```json
{
  "errorCode": "422",
  "message": "Produto não encontrado",
  "uuid": "c55a7992-71fa-42f5-94ef-5760f21c7f5b",
  "statusCode": 422,
  "timestamp": "2025-05-29T23:53:57.676239136"
}
```

---

#### `GET /order`
Lista pedidos com suporte à paginação.

**Query Params** (opcionais):
- limit (padrão: 10)
- offset (padrão: 0)
- page (padrão: 0)

**Response:**
```json
{
    "content": [
        {
            "orderId": "A001",
            "totalOrder": 70.0,
            "preparationTime": 320,
            "clientId": "123e4567-e89b-12d3-a456-426614174000",
            "timestamp": "2025-05-30T01:18:06.339+00:00",
            "products": [
                {
                    "productId": 1,
                    "quantity": 1,
                    "vlUnitProduct": 50.0
                },
                {
                    "productId": 2,
                    "quantity": 2,
                    "vlUnitProduct": 20.0
                }
            ]
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 1,
    "totalPages": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```

**Status:**

- 200 OK
- 404 Not Found

---

#### `GET /order/{id}`
Busca um pedido específico pelo ID.

**Response:**
```json
{
    "orderId": "A001",
    "totalOrder": 70.0,
    "preparationTime": 320,
    "clientId": "123e4567-e89b-12d3-a456-426614174000",
    "timestamp": "2025-05-30T01:18:06.339+00:00",
    "products": [
        {
            "productId": 1,
            "quantity": 1,
            "vlUnitProduct": 50.0
        },
        {
            "productId": 2,
            "quantity": 2,
            "vlUnitProduct": 20.0
        }
    ]
}
```

**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
  "errorCode": "404",
  "message": "Pedido não encontrado",
  "uuid": "9d8ae761-1fe3-41a0-bf96-f237006fdee9",
  "statusCode": 404,
  "timestamp": "2025-05-30T00:55:51.959757055"
}
```

---

#### `PUT /order/{id}`
Atualiza os dados de um pedido.

**Request:**
```json
{
    "minute": 30,
    "clientId": "123e4567-e89b-12d3-a456-426614174000",
    "products": [
        {
            "productId": 2,
            "vlUnitProduct": 5.0
        },
        {
            "productId": 3,
            "vlUnitProduct": 10.0
        },
        {
            "productId": 3,
            "vlUnitProduct": 10.0
        }
    ]
}
```

**Response:**
```json
{
    "orderId": "A001",
    "totalOrder": 25.0,
    "preparationTime": 0,
    "clientId": "123e4567-e89b-12d3-a456-426614174000",
    "timestamp": "2025-05-30T01:22:24.341+00:00",
    "products": [
        {
            "productId": 2,
            "quantity": 1,
            "vlUnitProduct": 5.0
        },
        {
            "productId": 3,
            "quantity": 2,
            "vlUnitProduct": 20.0
        }
    ]
}
```

**Status:**

- 200 OK
- 404 Not Found

---

> ⚠️ **Nota:** Os pedidos devem ser atualizados ou removidos apenas antes de seu status ser alterado para "PREPARING" ou "COMPLETED".
---

---
# 💳 Payment

Responsável pelo gerenciamento dos **pagamentos realizados** no sistema.

---

---

> ⚠️ **Nota:** Consulte a seção específica sobre a integração com o Mercado Pago para saber como testar o fluxo de pagamento.
---

### 📌 Endpoints

#### `POST /payment`
Cria um novo pedido.

**Request:**
```json
{
	"id": "A001",
	"totalOrderValue": 30.00,
	"products": [
		{
			"name": "X-Tudo",
			"category": "Lanche",
			"unitPrice": 30.00,
			"quantity": 1
		}
	]
}
```

**Response:**
```json
{
	"id": "A001",
	"externalId": "empty-A001",
	"paymentStatus": "OPENED",
	"totalOrderValue": 30,
	"qrCode": "00020101021243650016COM.MERCADOLIBRE020130636afdd3bfb-1eba-4c01-8687-8c60f329bbba5204000053039865802BR5909Test Test6009SAO PAULO62070503***6304C8C8",
	"expiration": "2025-05-31T00:33:10.0498834",
	"createdAt": "2025-05-31T00:23:10.049909849",
	"timestamp": "2025-05-31T00:23:10.049909849"
}
```
**Status:**

- 201 Created
- 400 Bad Request
- 422 Unprocessable Entity

**Exemplo de erro:**
```json
{
	"errorCode": "400",
	"message": "Payment with ID A001 already exists",
	"uuid": "a86eda31-e5fd-4987-874b-4c448b5570c9",
	"statusCode": 400,
	"timestamp": "2025-05-31T00:26:08.950176601"
}
```

#### `GET /payment/{id}`
Obtém os detalhes de um pagamento.

**Response:**
```json
{
	"id": "A001",
	"externalId": "134324248",
	"paymentStatus": "CLOSED",
	"totalOrderValue": 30,
	"qrCode": "00020101021243650016COM.MERCADOLIBRE020130636afdd3bfb-1eba-4c01-8687-8c60f329bbba5204000053039865802BR5909Test Test6009SAO PAULO62070503***6304C8C8",
	"expiration": "2025-05-31T00:33:10.0498834",
	"createdAt": "2025-05-31T00:23:10.049909849",
	"timestamp": "2025-05-31T00:23:10.049909849"
}
```
**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
	"errorCode": "404",
	"message": "Payment not found",
	"uuid": "7caa6b52-9af3-4ade-a833-2ca5068827e8",
	"statusCode": 404,
	"timestamp": "2025-05-31T00:32:23.977305012"
}
```

#### `GET /payment/{id}/qr`
Obtém o código QR do pagamento.

**Response:**
O código QR renderizado

**Status:**

- 200 OK
- 404 Not Found

**Exemplo de erro:**
```json
{
	"errorCode": "404",
	"message": "Payment not found",
	"uuid": "7caa6b52-9af3-4ade-a833-2ca5068827e8",
	"statusCode": 404,
	"timestamp": "2025-05-31T00:32:23.977305012"
}
```

#### `POST /payment/notifications/mercado-pago`
Recebe a notificação de pagamento do Mercado Pago

**Request:**
```json
{
	"action": "payment.created",
	"type": "payment",
	"data": {
		"id": "31023442055"
	}
}
```

**Response:**
```json
{
	"id": "A001",
	"externalId": "134324248",
	"paymentStatus": "CLOSED",
	"totalOrderValue": 30,
	"qrCode": "00020101021243650016COM.MERCADOLIBRE020130636afdd3bfb-1eba-4c01-8687-8c60f329bbba5204000053039865802BR5909Test Test6009SAO PAULO62070503***6304C8C8",
	"expiration": "2025-05-31T00:33:10.0498834",
	"createdAt": "2025-05-31T00:23:10.049909849",
	"timestamp": "2025-05-31T00:23:10.049909849"
}
```
**Status:**

- 200 OK
- 401 Unauthorized
- 400 Bad Request
- 404 Not Found

**Exemplo de erro:**
```json
{
	"errorCode": "400",
	"message": "Payment with external ID 134324248 already exists",
	"uuid": "a86eda31-e5fd-4987-874b-4c448b5570c9",
	"statusCode": 400,
	"timestamp": "2025-05-31T00:26:08.950176601"
}
```

---
# 🍔 Preparation

Responsável pelo gerenciamento da **preparação** dos produtos pela **cozinha** do estabelecimento.

---

### 📌 Endpoints

#### `POST /preparation`
Cria um novo pedido de preparação após o pagamento ser realizado com sucesso.

**Response:**
```json
{
	"id": "A001",
	"preparationPosition": 1,
	"preparationTime": 10,
	"estimatedReadyTime": null,
	"preparationStatus": "RECEIVED",
	"createdAt": "2025-05-31T00:40:17.844447957",
	"timestamp": "2025-05-31T00:40:17.844447957"
}
```
**Status:**

- 201 Created
- 400 Bad Request
- 422 Unprocessable Entity

**Exemplo de erro:**
```json
{
	"errorCode": "400",
	"message": "Preparation with ID A001 already exists",
	"uuid": "8138f8fd-259e-41e6-892c-674bc8e73cd3",
	"statusCode": 400,
	"timestamp": "2025-05-31T00:40:38.778791123"
}
```

#### `POST /preparation/start-next`
Obtém o próximo pedido de preparação de acordo com a ordem de realização dos pedidos e o atualiza com o status "Em preparação".

**Response:**
```json
{
	"id": "A001",
	"preparationPosition": null,
	"preparationTime": 10,
	"estimatedReadyTime": "2025-05-31T00:54:20.729109788",
	"preparationStatus": "IN_PREPARATION",
	"createdAt": "2025-05-31T00:40:17.844448",
	"timestamp": "2025-05-31T00:44:20.729130718"
}
```
**Status:**

- 200 OK
- 400 Bad Request

**Exemplo de erro:**
```json
{
	"errorCode": "400",
	"message": "No preparation available to start",
	"uuid": "37763c1b-3f53-4df3-b076-5deee34bca90",
	"statusCode": 400,
	"timestamp": "2025-05-31T00:44:39.176260968"
}
```

#### `POST /preparation/{preparation_id}/ready`
Atualiza um pedido de preparação com o status "Pronto", indicando que o pedido está pronto para ser entregue ao cliente.

**Response:**
```json
{
	"id": "A001",
	"preparationPosition": null,
	"preparationTime": 10,
	"estimatedReadyTime": "2025-05-31T00:54:20.72911",
	"preparationStatus": "READY",
	"createdAt": "2025-05-31T00:40:17.844448",
	"timestamp": "2025-05-31T00:45:49.02704791"
}
```
**Status:**

- 200 OK
- 400 Bad Request
- 404 Not Found

**Exemplo de erro:**
```json
{
	"errorCode": "404",
	"message": "Preparation not found",
	"uuid": "87a1527e-5f4e-4bf8-96b4-6f149e7016c6",
	"statusCode": 404,
	"timestamp": "2025-05-31T01:07:58.943625153"
}
```

#### `POST /preparation/{preparation_id}/complete`
Atualiza um pedido de preparação com o status "Finalizado", indicando que o pedido foi entregue ao cliente.

**Response:**
```json
{
	"id": "A001",
	"preparationPosition": null,
	"preparationTime": 10,
	"estimatedReadyTime": "2025-05-31T00:54:20.72911",
	"preparationStatus": "COMPLETED",
	"createdAt": "2025-05-31T00:40:17.844448",
	"timestamp": "2025-05-31T00:47:19.106673387"
}
```
**Status:**

- 200 OK
- 400 Bad Request
- 404 Not Found

**Exemplo de erro:**
```json
{
	"errorCode": "400",
	"message": "A preparation with COMPLETED status cannot be updated to COMPLETED",
	"uuid": "f43d72c6-6101-43c2-8811-9ca5d451cfc1",
	"statusCode": 400,
	"timestamp": "2025-05-31T00:48:20.448803064"
}
```

#### `POST /preparation/waiting-list`
Obtém a lista de pedidos de preparação ordenados da seguinte forma:

1. Pedidos prontos para retirada
2. Pedidos em preparação subordenados com base na estimativa de pronto
3. Pedidos recebidos ordenados por ordem de chegada

**Response:**
```json
{
  "items": [
    {
      "id": "A004",
      "preparationPosition": null,
      "preparationTime": 3,
      "estimatedReadyTime": "2025-05-31T00:53:30.101797",
      "preparationStatus": "READY",
      "createdAt": "2025-05-31T00:49:32.62829",
      "timestamp": "2025-05-31T00:50:48.241601"
    },
    {
      "id": "A002",
      "preparationPosition": null,
      "preparationTime": 5,
      "estimatedReadyTime": "2025-05-31T00:55:29.71201",
      "preparationStatus": "IN_PREPARATION",
      "createdAt": "2025-05-31T00:49:18.283901",
      "timestamp": "2025-05-31T00:50:29.712026"
    },
    {
      "id": "A005",
      "preparationPosition": null,
      "preparationTime": 6,
      "estimatedReadyTime": "2025-05-31T00:56:30.70522",
      "preparationStatus": "IN_PREPARATION",
      "createdAt": "2025-05-31T00:49:38.246773",
      "timestamp": "2025-05-31T00:50:30.705237"
    },
    {
      "id": "A006",
      "preparationPosition": null,
      "preparationTime": 10,
      "estimatedReadyTime": "2025-05-31T01:00:31.298067",
      "preparationStatus": "IN_PREPARATION",
      "createdAt": "2025-05-31T00:49:43.814773",
      "timestamp": "2025-05-31T00:50:31.298083"
    },
    {
      "id": "A007",
      "preparationPosition": 1,
      "preparationTime": 2,
      "estimatedReadyTime": null,
      "preparationStatus": "RECEIVED",
      "createdAt": "2025-05-31T00:49:54.869954",
      "timestamp": "2025-05-31T00:49:54.869954"
    },
    {
      "id": "A008",
      "preparationPosition": 2,
      "preparationTime": 8,
      "estimatedReadyTime": null,
      "preparationStatus": "RECEIVED",
      "createdAt": "2025-05-31T00:50:03.727758",
      "timestamp": "2025-05-31T00:50:03.727758"
    },
    {
      "id": "A009",
      "preparationPosition": 3,
      "preparationTime": 3,
      "estimatedReadyTime": null,
      "preparationStatus": "RECEIVED",
      "createdAt": "2025-05-31T00:50:08.975707",
      "timestamp": "2025-05-31T00:50:08.975707"
    },
    {
      "id": "A010",
      "preparationPosition": 4,
      "preparationTime": 5,
      "estimatedReadyTime": null,
      "preparationStatus": "RECEIVED",
      "createdAt": "2025-05-31T00:50:16.123321",
      "timestamp": "2025-05-31T00:50:16.123321"
    },
    {
      "id": "A011",
      "preparationPosition": 5,
      "preparationTime": 2,
      "estimatedReadyTime": null,
      "preparationStatus": "RECEIVED",
      "createdAt": "2025-05-31T00:50:21.327085",
      "timestamp": "2025-05-31T00:50:21.327085"
    }
  ]
}
```
**Status:**

- 200 OK

## Integração com o Mercado Pago

Este projeto integra-se ao Mercado Pago para realizar pagamentos presenciais utilizando [Código QR Dinâmico](https://www.mercadopago.com.br/developers/pt/docs/qr-code/integration-configuration/qr-dynamic/integration).

### Pré-requisitos para testes

Para realizar os testes, há duas opções:

#### Credenciais de teste
Utilizar as credenciais de teste presentes no arquivo `soat.env`, realizando as compras com o usuário de teste **enviado na entrega**.

#### Integração própria
Realizar sua própria integração com o Mercado Pago, seguindo os passos a seguir:
1. Crie uma conta **de produção** no Mercado Pago.
2. Acesse o portal do desenvolvedor e **crie uma aplicação** nessa conta.
3. Gere **usuários de teste** vinculados à aplicação:
   - um usuário **vendedor**
   - um usuário **comprador**
4. Com a conta de vendedor, crie uma aplicação no portal do desenvolvedor.
5. Nas **credenciais de produção** da aplicação, obtenha:
   - `MERCADO_PAGO_ACCESS_TOKEN`: token de acesso (access token)
   - `MERCADO_PAGO_USER_ID`: ID do usuário (user ID)
6. Crie uma **store** via API do Mercado Pago:
   - [POST https://api.mercadopago.com/users/{USER_ID}/stores](https://api.mercadopago.com/users/{USER_ID}/stores)
7. Crie um **POS** (ponto de venda) vinculado à store:
   - [POST https://api.mercadopago.com/pos](https://api.mercadopago.com/pos)
8. O valor do campo `external_store_id` da POS deve ser usado na variável `MERCADO_PAGO_POS`.
9. Gere um token aleatório e seguro para configurar como `MERCADO_PAGO_WEBHOOK_TOKEN`.

### Como testar o fluxo de pagamento?

Após configurar tudo, a API precisa estar **acessível na web** para que o Mercado Pago consiga notificar a finalização do pagamento.

A URL configurada em `MERCADO_PAGO_CALLBACK_URL` deve ser:

```
https://{ENDERECO_DA_API}/soat-fast-food/payment/notifications/mercado-pago
```

### Explicação das variáveis de ambiente

- `MERCADO_PAGO_ACCESS_TOKEN`: token de autenticação da API do Mercado Pago
- `MERCADO_PAGO_CALLBACK_URL`: URL que receberá notificações de pagamento (webhook)
- `MERCADO_PAGO_POS`: valor de `external_store_id` do POS criado via API
- `MERCADO_PAGO_USER_ID`: ID do usuário vinculado à aplicação
- `MERCADO_PAGO_WEBHOOK_TOKEN`: token usado como parâmetro de query para validar as notificações de pagamento recebidas