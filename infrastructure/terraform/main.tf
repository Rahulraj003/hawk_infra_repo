# Configure Kubernetes provider
provider "kubernetes" {
  config_path = "~/.kube/config"
}

# Configure Helm provider
provider "helm" {
  kubernetes {
    config_path = "~/.kube/config"
  }
}

# Create ArgoCD namespace
resource "kubernetes_namespace" "argocd" {
  metadata {
    name = "argocd"
    labels = {
      app = "argocd"
    }
  }
}

# Add Argo Helm repository
resource "helm_release" "argocd" {
  name       = "argocd"
  repository = "https://argoproj.github.io/argo-helm"
  chart      = "argo-cd"
  namespace  = kubernetes_namespace.argocd.metadata[0].name
  version    = var.argocd_version

  # Ultra-lightweight ArgoCD configuration
  set {
    name  = "server.service.type"
    value = "NodePort"
  }

  set {
    name  = "server.service.nodePort"
    value = var.node_port
  }

  set {
    name  = "server.ingress.enabled"
    value = "false"
  }

  # Additional lightweight configurations
  set {
    name  = "server.extraArgs"
    value = "{--insecure}"
  }

  set {
    name  = "configs.secret.argocdServerAdminPassword"
    value = var.admin_password
  }

  set {
    name  = "configs.secret.argocdServerSecretKey"
    value = var.argocd_server_secret_key
  }

  # Ultra-lightweight server resources
  set {
    name  = "server.resources.limits.cpu"
    value = var.server_resources.limits.cpu
  }

  set {
    name  = "server.resources.limits.memory"
    value = var.server_resources.limits.memory
  }

  set {
    name  = "server.resources.requests.cpu"
    value = var.server_resources.requests.cpu
  }

  set {
    name  = "server.resources.requests.memory"
    value = var.server_resources.requests.memory
  }

  # Ultra-lightweight repo server resources
  set {
    name  = "repoServer.resources.limits.cpu"
    value = var.repo_server_resources.limits.cpu
  }

  set {
    name  = "repoServer.resources.limits.memory"
    value = var.repo_server_resources.limits.memory
  }

  set {
    name  = "repoServer.resources.requests.cpu"
    value = var.repo_server_resources.requests.cpu
  }

  set {
    name  = "repoServer.resources.requests.memory"
    value = var.repo_server_resources.requests.memory
  }

  # Ultra-lightweight application controller resources
  set {
    name  = "applicationSetController.resources.limits.cpu"
    value = var.application_set_controller_resources.limits.cpu
  }

  set {
    name  = "applicationSetController.resources.limits.memory"
    value = var.application_set_controller_resources.limits.memory
  }

  set {
    name  = "applicationSetController.resources.requests.cpu"
    value = var.application_set_controller_resources.requests.cpu
  }

  set {
    name  = "applicationSetController.resources.requests.memory"
    value = var.application_set_controller_resources.requests.memory
  }

  # Ultra-lightweight Redis resources (if enabled)
  set {
    name  = "redis.resources.limits.cpu"
    value = var.redis_resources.limits.cpu
  }

  set {
    name  = "redis.resources.limits.memory"
    value = var.redis_resources.limits.memory
  }

  set {
    name  = "redis.resources.requests.cpu"
    value = var.redis_resources.requests.cpu
  }

  set {
    name  = "redis.resources.requests.memory"
    value = var.redis_resources.requests.memory
  }

  # Disable unnecessary features for lightweight setup
  set {
    name  = "ha.enabled"
    value = "false"
  }

  set {
    name  = "metrics.enabled"
    value = "false"
  }

  set {
    name  = "notifications.enabled"
    value = "false"
  }

  set {
    name  = "dex.enabled"
    value = "false"
  }

  set {
    name  = "configs.cm.accounts.admin.enabled"
    value = "true"
  }

  set {
    name  = "configs.cm.accounts.admin.username"
    value = var.admin_username
  }

  set {
    name  = "configs.cm.accounts.admin.password"
    value = var.admin_password
  }

  # Minimal replica count for lightweight setup
  set {
    name  = "server.replicaCount"
    value = "1"
  }

  set {
    name  = "repoServer.replicaCount"
    value = "1"
  }

  set {
    name  = "applicationSetController.replicaCount"
    value = "1"
  }

  depends_on = [kubernetes_namespace.argocd]
}

## Output ArgoCD access information
output "argocd_info" {
  description = "ArgoCD deployment information"
  value = {
    namespace = kubernetes_namespace.argocd.metadata[0].name
    service_name = "argocd-server"
    node_port = var.node_port
    admin_password = var.admin_password
    access_url = "http://localhost:${var.external_port}"
    resource_usage = "Ultra-lightweight configuration"
  }
  sensitive = true  # Mark as sensitive since it contains password
}
