resource "kubernetes_deployment" "app" {
  metadata {
    name      = "app-deployment"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  spec {
    replicas = 1
    strategy {
      type = "Recreate"
    }
    selector {
      match_labels = {
        app = "tech-challenge-app"
      }
    }

    template {
      metadata {
        labels = {
          app = "tech-challenge-app"
        }

        annotations = {
          "restarted-at" = var.force_rollout
        }
      }

      spec {
        container {
          name              = "tech-challenge-app"
          image             = var.app_image
          image_pull_policy = "Always"

          port {
            container_port = 8080
          }

          resources {
            limits = {
              cpu    = "1"
              memory = "1Gi"
            }
            requests = {
              cpu    = "500m"
              memory = "512Mi"
            }
          }

          env {
            name = "SPRING_DATASOURCE_URL"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SPRING_DATASOURCE_URL"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_USERNAME"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SPRING_DATASOURCE_USERNAME"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SPRING_DATASOURCE_PASSWORD"
              }
            }
          }

          env {
            name = "MERCADO_PAGO_ACCESS_TOKEN"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "MERCADO_PAGO_ACCESS_TOKEN"
              }
            }
          }

          env {
            name = "MERCADO_PAGO_CALLBACK_URL"
            value_from {
              config_map_key_ref {
                name = kubernetes_config_map.app_config.metadata[0].name
                key  = "MERCADO_PAGO_CALLBACK_URL"
              }
            }
          }

          env {
            name = "MERCADO_PAGO_POS"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "MERCADO_PAGO_POS"
              }
            }
          }

          env {
            name = "MERCADO_PAGO_USER_ID"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "MERCADO_PAGO_USER_ID"
              }
            }
          }

          env {
            name = "MERCADO_PAGO_WEBHOOK_TOKEN"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "MERCADO_PAGO_WEBHOOK_TOKEN"
              }
            }
          }

          env {
            name = "JWT_SECRET_KEY"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "JWT_SECRET_KEY"
              }
            }
          }

          env {
            name = "JWT_EXPIRATION_TIME"
            value_from {
              config_map_key_ref {
                name = kubernetes_config_map.app_config.metadata[0].name
                key  = "JWT_EXPIRATION_TIME"
              }
            }
          }

          env {
            name = "BROKER_HOST"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "BROKER_HOST"
              }
            }
          }

          env {
            name = "BROKER_PORT"
            value_from {
              config_map_key_ref {
                name = kubernetes_config_map.app_config.metadata[0].name
                key  = "BROKER_PORT"
              }
            }
          }

          env {
            name = "BROKER_USERNAME"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "BROKER_USERNAME"
              }
            }
          }

          env {
            name = "BROKER_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "BROKER_PASSWORD"
              }
            }
          }
        }
      }
    }
  }
}
