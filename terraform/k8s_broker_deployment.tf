resource "kubernetes_deployment" "broker" {
  metadata {
    name      = "broker-deployment"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
    annotations = {
      "restarted-at" = timestamp()
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "tech-challenge-broker"
      }
    }

    template {
      metadata {
        labels = {
          app = "tech-challenge-broker"
        }

        annotations = {
          "restarted-at" = var.force_rollout
        }
      }

      spec {
        container {
          name              = "tech-challenge-broker"
          image             = var.broker_image
          image_pull_policy = "Always"

          port {
            container_port = 5672
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
        }
      }
    }
  }
}
