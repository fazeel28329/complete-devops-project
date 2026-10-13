resource "kubernetes_namespace_v1" "devops" {
  metadata {
    name = "devops-terraform"

    labels = {
      environment = "development"
      managed_by  = "terraform"
      project     = "complete-devops"
    }
  }
}
