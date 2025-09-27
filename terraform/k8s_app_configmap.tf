resource "kubernetes_config_map" "app_config" {
  metadata {
    name      = "app-config"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  data = {
    MERCADO_PAGO_CALLBACK_URL = "${local.api_gateway_url}/soat-fast-food/payment/notifications/mercado-pago"
    JWT_EXPIRATION_TIME       = var.jwt_expiration_time
    BROKER_PORT               = var.broker_port
  }
}
