# Terraform State Management Guide (Local Testing Setup)

This guide explains how Terraform state is managed for your local testing environment.

## 📁 **State File Location (Local Testing):**

### **Current Setup (Local State in Repository):**
```
infrastructure/terraform/
├── main.tf
├── variables.tf
├── outputs.tf
├── terraform.tfvars
├── backend.tf
├── .gitignore
├── terraform.tfstate     # State file (committed to repo for testing)
├── .terraform/           # Provider files (ignored by Git)
└── terraform.tfplan     # Plan files (if any)
```

## 🏠 **Local State Benefits for Testing:**

### **Advantages:**
- ✅ **Simple setup** - no external dependencies
- ✅ **State tracked** in your repository
- ✅ **Easy to share** with team members
- ✅ **Version control** for infrastructure state
- ✅ **Quick recovery** from Git history
- ✅ **Perfect for testing** and development

### **Considerations:**
- ⚠️ **State files contain** resource IDs and metadata
- ⚠️ **Sensitive data** should be in variables, not hardcoded
- ⚠️ **Team collaboration** requires good communication
- ⚠️ **No automatic locking** for concurrent operations

## 🚀 **How It Works:**

### **1. State File Creation:**
```bash
cd infrastructure/terraform
terraform init    # Downloads providers
terraform plan   # Shows what will change
terraform apply  # Creates resources and state file
```

### **2. State File Contents:**
- **Resource metadata** (IDs, names, attributes)
- **Provider configuration**
- **Resource dependencies**
- **Output values**
- **Current resource state**

### **3. State File Tracking:**
- **State file committed** to your repository
- **Version controlled** with your infrastructure code
- **Easy rollback** to previous states
- **Team can see** current infrastructure state

## 🔄 **State Management in GitHub Actions:**

### **With Local State (Your Setup):**
1. **Workflow runs** on your local Mac runner
2. **Terraform init** downloads providers
3. **Terraform plan** shows changes
4. **Terraform apply** creates/updates resources
5. **State file updated** in repository
6. **Changes committed** to Git

### **State Persistence:**
- ✅ **State persists** between workflow runs
- ✅ **Incremental changes** tracked properly
- ✅ **Resource updates** handled correctly
- ✅ **Full audit trail** in Git history

## 📋 **Repository Structure:**

```
hawk_infra_repo/
├── .github/workflows/
│   ├── deploy-infrastructure.yml    # Deploys ArgoCD
│   ├── destroy-infrastructure.yml   # Destroys ArgoCD
│   └── k8s-local-cluster-self-hosted.yml  # Monitors cluster
├── infrastructure/
│   └── terraform/
│       ├── main.tf                  # Main configuration
│       ├── variables.tf             # Variable definitions
│       ├── outputs.tf               # Output values
│       ├── terraform.tfvars         # Configuration values
│       ├── backend.tf               # Local state config
│       ├── .gitignore               # Ignores provider files
│       ├── terraform.tfstate        # State file (committed)
│       └── STATE_MANAGEMENT.md      # This guide
└── applications/                     # Future application code
```

## 🎯 **Best Practices for Local State:**

### **1. Commit State Files:**
```bash
# Add state file to repository
git add infrastructure/terraform/terraform.tfstate
git commit -m "Update infrastructure state"
```

### **2. Keep Sensitive Data in Variables:**
```hcl
# Good: Use variables for sensitive data
variable "admin_password" {
  description = "Admin password for ArgoCD"
  type        = string
  sensitive   = true
}

# Avoid: Hardcoding sensitive data
admin_password = "secret123"
```

### **3. Regular State Updates:**
```bash
# After infrastructure changes
terraform apply
git add terraform.tfstate
git commit -m "Update infrastructure state"
```

## 🚨 **Security Considerations:**

### **What's Safe to Commit:**
- ✅ **State files** (resource metadata)
- ✅ **Configuration files** (main.tf, variables.tf)
- ✅ **Output values** (non-sensitive)

### **What to Avoid:**
- ❌ **Hardcoded secrets** in .tf files
- ❌ **Sensitive variable values** in terraform.tfvars
- ❌ **Provider credentials** in configuration

### **Use Variables for Secrets:**
```hcl
# terraform.tfvars (committed)
admin_password = "admin123"

# For production, use environment variables
# export TF_VAR_admin_password="secure_password"
```

## 🔍 **State File Examples:**

### **What You'll See in State:**
```json
{
  "version": 4,
  "terraform_version": "1.5.0",
  "resources": [
    {
      "type": "kubernetes_namespace",
      "name": "argocd",
      "instances": [
        {
          "attributes": {
            "metadata": [
              {
                "name": "argocd",
                "labels": {
                  "app": "argocd"
                }
              }
            ]
          }
        }
      ]
    }
  ]
}
```

## 🚀 **Quick Start:**

```bash
cd infrastructure/terraform

# Initialize (creates local state)
terraform init

# Plan deployment
terraform plan

# Apply changes
terraform apply

# Commit state file
git add terraform.tfstate
git commit -m "Deploy ArgoCD infrastructure"
```

## 🆘 **Troubleshooting:**

### **State File Issues:**
```bash
# Backup state
cp terraform.tfstate terraform.tfstate.backup

# Reinitialize
terraform init

# Check state
terraform show
```

### **State File Conflicts:**
```bash
# Pull latest changes
git pull origin develop

# Reapply if needed
terraform plan
terraform apply
```

---

**Your local state setup is perfect for testing! It provides simplicity, version control, and easy team collaboration without external dependencies.**
