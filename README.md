# SOAT Tech Challenge Fast Food




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
