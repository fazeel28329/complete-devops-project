resource "kubernetes_service_v1" "java_service" {
  metadata {
    name      = "terraform-java-service"
    namespace = kubernetes_namespace_v1.devops.metadata[0].name
  }

  spec {
    selector = {
      app = "terraform-java-app"
    }

    port {
      port        = 80
      target_port = 8082
      protocol    = "TCP"
    }

    type = "NodePort"
  }
}
