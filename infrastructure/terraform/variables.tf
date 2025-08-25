variable "cluster_name" {
  description = "Name of the k3d cluster"
  type        = string
  default     = "gitops-demo"
}

variable "argocd_namespace" {
  description = "Namespace for ArgoCD"
  type        = string
  default     = "argocd"
}

variable "argocd_version" {
  description = "ArgoCD Helm chart version"
  type        = string
  default     = "6.7.6"
}

variable "node_port" {
  description = "NodePort for ArgoCD server service"
  type        = number
  default     = 30080
}

variable "external_port" {
  description = "External port mapping for k3d"
  type        = number
  default     = 8081
}

variable "admin_password" {
  description = "Admin password for ArgoCD"
  type        = string
  default     = "admin123"
  sensitive   = true
}

variable "argocd_server_secret_key" {
  description = "Secret key for ArgoCD server (32-byte random key)"
  type        = string
  default     = "your-secret-key-here-change-this-in-production"
  sensitive   = true
}

variable "server_resources" {
  description = "Ultra-lightweight resource limits for ArgoCD server"
  type = object({
    limits = object({
      cpu    = string
      memory = string
    })
    requests = object({
      cpu    = string
      memory = string
    })
  })
  default = {
    limits = {
      cpu    = "200m"
      memory = "256Mi"
    }
    requests = {
      cpu    = "100m"
      memory = "128Mi"
    }
  }
}

variable "repo_server_resources" {
  description = "Ultra-lightweight resource limits for ArgoCD repo server"
  type = object({
    limits = object({
      cpu    = string
      memory = string
    })
    requests = object({
      cpu    = string
      memory = string
    })
  })
  default = {
    limits = {
      cpu    = "200m"
      memory = "256Mi"
    }
    requests = {
      cpu    = "100m"
      memory = "128Mi"
    }
  }
}

variable "application_set_controller_resources" {
  description = "Ultra-lightweight resource limits for ArgoCD application set controller"
  type = object({
    limits = object({
      cpu    = string
      memory = string
    })
    requests = object({
      cpu    = string
      memory = string
    })
  })
  default = {
    limits = {
      cpu    = "200m"
      memory = "256Mi"
    }
    requests = {
      cpu    = "100m"
      memory = "128Mi"
    }
  }
}

variable "redis_resources" {
  description = "Ultra-lightweight resource limits for Redis (if enabled)"
  type = object({
    limits = object({
      cpu    = string
      memory = string
    })
    requests = object({
      cpu    = string
      memory = string
    })
  })
  default = {
    limits = {
      cpu    = "100m"
      memory = "128Mi"
    }
    requests = {
      cpu    = "50m"
      memory = "64Mi"
    }
  }
}
