output "argocd_namespace" {
  description = "ArgoCD namespace"
  value       = kubernetes_namespace.argocd.metadata[0].name
}

output "argocd_service_name" {
  description = "ArgoCD server service name"
  value       = "argocd-server"
}

output "argocd_node_port" {
  description = "NodePort for ArgoCD server"
  value       = var.node_port
}

output "argocd_external_url" {
  description = "External URL to access ArgoCD"
  value       = "http://localhost:${var.external_port}"
}

output "argocd_admin_password" {
  description = "Admin password for ArgoCD"
  value       = var.admin_password
  sensitive   = true
}

output "argocd_cluster_info" {
  description = "Complete ArgoCD cluster information"
  value = {
    namespace      = kubernetes_namespace.argocd.metadata[0].name
    service_name   = "argocd-server"
    node_port      = var.node_port
    external_port  = var.external_port
    external_url   = "http://localhost:${var.external_port}"
    admin_user     = "admin"
    admin_password = var.admin_password
    helm_release   = helm_release.argocd.name
    chart_version  = helm_release.argocd.version
  }
}

output "kubectl_commands" {
  description = "Useful kubectl commands for ArgoCD"
  value = {
    check_status     = "kubectl get pods -n ${kubernetes_namespace.argocd.metadata[0].name}"
    check_services   = "kubectl get svc -n ${kubernetes_namespace.argocd.metadata[0].name}"
    get_admin_secret = "kubectl -n ${kubernetes_namespace.argocd.metadata[0].name} get secret argocd-initial-admin-secret -o jsonpath=\"{.data.password}\" | base64 -d"
    port_forward     = "kubectl port-forward svc/argocd-server -n ${kubernetes_namespace.argocd.metadata[0].name} 8080:443"
  }
}
