# SOAT Tech Challenge Fast Food

## Startar o Banco de Dados e Aplicação
1. Comando para rodar o docker-compose para subuir o banco de dados e a aplicação. Força a reconstrução das imagens dos serviços definidos no arquivo docker-compose.yml, mesmo que já existam imagens anteriores (o recomendado para utilizar):

```bash
docker compose up --build
```

2. Para subir o Docker Compose (não reconstroe as imagens):

```bash
docker compose up -d
```
3. Para parar e remover todos os containers, redes e volumes criados pelo Docker Compose:

```bash
docker compose up -d
```

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
## Integração com o Mercado Pago

O projeto se integra ao Mercado Pago para realização de pagamentos do tipo presencial com [Código QR Dinâmico](https://www.mercadopago.com.br/developers/pt/docs/qr-code/integration-configuration/qr-dynamic/integration).

Para realização dos testes, é necessário:

1. Ter uma conta de produção no Mercado Pago
2. Gerar uma aplicação nesta conta
3. Gerar usuários de teste nesta aplicação
   - um vendedor
   - um comprador
4. Com a conta de vendedor, acessar o portal do desenvolvedor e criar uma aplicação
5. Nas credenciais de produção desta aplicação, obter os dados que serão usados nas variáveis de ambiente do projeto:
   - `MERCADO_PAGO_ACCESS_TOKEN`: O access token disponível nas credenciais de produção
   - `MERCADO_PAGO_USER_ID`: O user ID disponível nas informações gerais
6. Criar uma `store` através da API do Mercado Pago. Verificar a documentação do endpoint POST https://api.mercadopago.com/users/{USER_ID}/stores
7. Criar um `POS` através da API do Mercado Pago vinculada a esta `store`. Verificar a documentação do endpoint POST https://api.mercadopago.com/pos
8. O campo `external_store_id` da `POS` deve ser usado para configurar a variável de ambiente `MERCADO_PAGO_POS`.
9. Gere um valor aleatório e seguro para ser usado como token e configure-o como a variável de ambiente `MERCADO_PAGO_WEBHOOK_TOKEN`.

### Como testar o fluxo de pagamento?

Para testar todo o fluxo, após seguir todas as etapas anteriores, é necessário publicar o site na WEB, para que o Mercado Pago seja capaz de notificar que o pagamento foi finalizado. O valor da variável de ambiente `MERCADO_PAGO_CALLBACK_URL` será: `http://{ENDERECO_DO_SITE}/soat-fast-food/payment/notifications/mercado-pago`.

Utilizamos o https://localhost.run/ na fase de desenvolvimento. É necessário realizar o cadastro das chaves SSH para que os domínios não troquem tão rápido a ponto de inviabilizar os testes.

## Variáveis de ambiente

Utilize o arquivo `.env.example` como referência para configurar as variáveis de ambiente do projeto.

```
MERCADO_PAGO_ACCESS_TOKEN=***
MERCADO_PAGO_CALLBACK_URL=https://{ENDERECO_DO_SITE}/soat-fast-food/payment/notifications/mercado-pago
MERCADO_PAGO_POS=ST01PS01
MERCADO_PAGO_USER_ID=***
MERCADO_PAGO_WEBHOOK_TOKEN=***
```

Abaixo, a explicação de cada uma delas:

- `MERCADO_PAGO_ACCESS_TOKEN`: O token da integração com o Mercado Pago
- `MERCADO_PAGO_CALLBACK_URL`: A URL para a qual será enviado um POST quando o pagamento for concluído
- `MERCADO_PAGO_POS`: O `external_store_id` do ponto de venda criado previamente através da API do Mercado Pago para a realização dos pagamentos
- `MERCADO_PAGO_USER_ID`: O ID do usuário da integração cm o Mercado Pago
- `MERCADO_PAGO_WEBHOOK_TOKEN`: O token a ser utilizado como query parameter para validação da requisição de finalização de pagamento.
