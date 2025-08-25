# Local State Configuration (Perfect for Testing)
# State files will be stored locally in the infrastructure/terraform/ directory

terraform {
  required_version = ">= 1.0"
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.25"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "~> 2.12"
    }
  }
}

# Note: No backend configuration means Terraform uses local state
# State file will be: infrastructure/terraform/terraform.tfstate
# This is perfect for testing and local development
