resource "kubernetes_deployment_v1" "java_app" {
  metadata {
    name      = "terraform-java-app"
    namespace = kubernetes_namespace_v1.devops.metadata[0].name
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        app = "terraform-java-app"
      }
    }

    template {
      metadata {
        labels = {
          app = "terraform-java-app"
        }
      }

      spec {
        container {
          name              = "java-web-app"
          image             = "complete-devops-app:2.0"
          image_pull_policy = "Never"

          port {
            container_port = 8082
          }

          readiness_probe {
            http_get {
              path = "/health"
              port = 8082
            }
            initial_delay_seconds = 5
            period_seconds        = 10
          }

          liveness_probe {
            http_get {
              path = "/health"
              port = 8082
            }
            initial_delay_seconds = 15
            period_seconds        = 20
          }

          resources {
            requests = {
              cpu    = "100m"
              memory = "128Mi"
            }

            limits = {
              cpu    = "500m"
              memory = "256Mi"
            }
          }
        }
      }
    }
  }
}
