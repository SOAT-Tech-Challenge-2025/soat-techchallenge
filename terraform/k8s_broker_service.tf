resource "kubernetes_service" "broker" {
  metadata {
    name      = "broker-service"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  spec {
    type = "ClusterIP"

    selector = {
      app = "tech-challenge-broker"
    }

    port {
      port        = 5672
      target_port = 5672
    }
  }
}
