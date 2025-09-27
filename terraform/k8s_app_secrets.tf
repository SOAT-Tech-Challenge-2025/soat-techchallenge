resource "kubernetes_secret" "app_secret" {
  metadata {
    name      = "app-secret"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  type = "Opaque"

  data = {
    SPRING_DATASOURCE_URL      = local.jdbc_url
    SPRING_DATASOURCE_USERNAME = var.db_user
    SPRING_DATASOURCE_PASSWORD = var.db_password
    MERCADO_PAGO_ACCESS_TOKEN  = var.mercado_pago_access_token
    MERCADO_PAGO_POS           = var.mercado_pago_pos
    MERCADO_PAGO_USER_ID       = var.mercado_pago_user_id
    MERCADO_PAGO_WEBHOOK_TOKEN = var.mercado_pago_webhook_token
    JWT_SECRET_KEY             = var.jwt_secret_key
    BROKER_HOST                = var.broker_host
    BROKER_USERNAME            = var.broker_username
    BROKER_PASSWORD            = var.broker_password
  }
}
