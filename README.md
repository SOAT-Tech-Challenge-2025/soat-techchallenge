# SOAT Tech Challenge Fast Food

## Build

Comando para criar imagem do database no docker:

    - docker build -t minha-imagem-postgres:tag1 -f Dockerfile .  
    - docker run -d -p 5432:5432 --name meu-postgres minha-imagem-postgres:tag1


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

Após configurar tudo, o site precisa estar **acessível na web** para que o Mercado Pago consiga notificar a finalização do pagamento.

A URL configurada em `MERCADO_PAGO_CALLBACK_URL` deve ser:

```
https://{ENDERECO_DO_SITE}/soat-fast-food/payment/notifications/mercado-pago
```

#### Usando o localhost.run

Durante o desenvolvimento, utilizamos o [localhost.run](https://localhost.run), um túnel SSH que expõe sua aplicação local para a internet.

##### Passo a passo:

1. [Configure uma chave SSH](https://admin.localhost.run/) para manter a mesma URL por mais tempo com o localhost.run

2. **Abra um terminal** e execute:
```bash
ssh -R 80:localhost:8080 {ID_DA_CHAVE_SSH}@localhost.run
```

3. O terminal mostrará uma URL pública semelhante a:
```
https://456we13dsc23.lhr.life
```

4. Use essa URL pública para configurar, no arquivo `soat.env`:
- Combine-a com `/soat-fast-food/payment/notifications/mercado-pago` e use esse valor para a variável `MERCADO_PAGO_ACCESS_TOKEN`. Por exemplo:
  ```
  https://456we13dsc23.lhr.life/soat-fast-food/payment/notifications/mercado-pago 
  ```

5. Faça o build da aplicação em **outro terminal**:
```bash
docker-compose build
```

6. **Inicie a aplicação**, normalmente:
```bash
docker-compose up -d
```

7. Crie um pagamento no endpoint POST `/soat-fast-food/payment`

8. Com o ID gerado na etapa anterior, obtenha o código QR no endpoint GET `/soat-fast-food/payment/{PAYMENT_ID}/qr`.

9. Acesse o aplicativo do Mercado Pago com o usuário comprador de teste indicado nos pré-requisitos dos testes.

10. Utilize o endpoint GET `/soat-fast-food/payment/{PAYMENT_ID}` para verificar o status do pagamento.

##### Observação importante:

- Deixe o terminal do `localhost.run` aberto enquanto estiver testando. Fechar ele derruba o túnel.

### Explicação das variáveis de ambiente

- `MERCADO_PAGO_ACCESS_TOKEN`: token de autenticação da API do Mercado Pago
- `MERCADO_PAGO_CALLBACK_URL`: URL que receberá notificações de pagamento (webhook)
- `MERCADO_PAGO_POS`: valor de `external_store_id` do POS criado via API
- `MERCADO_PAGO_USER_ID`: ID do usuário vinculado à aplicação
- `MERCADO_PAGO_WEBHOOK_TOKEN`: token usado como parâmetro de query para validar as notificações de pagamento recebidas
