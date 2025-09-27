resource "kubernetes_ingress_v1" "app" {
  metadata {
    name      = "app-ingress-route"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        path {
          path      = "/"
          path_type = "Prefix"

          backend {
            service {
              name = kubernetes_service.app.metadata[0].name
              port {
                number = 80
              }
            }
          }
        }
      }
    }
  }
}
