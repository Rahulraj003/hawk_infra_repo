# ArgoCD Terraform Deployment

This Terraform configuration deploys ArgoCD to your k3d cluster using Helm.

**Location**: `infrastructure/terraform/`

## 🏗️ **Prerequisites**

- **k3d cluster** running (gitops-demo)
- **Terraform** installed (version >= 1.0)
- **kubectl** configured to access your cluster
- **Helm** installed

## 🚀 **Quick Start**

### **1. Navigate to Terraform Directory**
```bash
# Navigate to the terraform directory
cd infrastructure/terraform

# Verify you're in the right location
pwd
# Should show: /path/to/hawk_infra_repo/infrastructure/terraform
```

### **2. Verify Your Cluster**
```bash
# Check if your k3d cluster is running
k3d cluster list

# Verify kubectl access
kubectl cluster-info

# Check current context
kubectl config current-context
```

### **3. Initialize Terraform**
```bash
# Initialize Terraform
terraform init

# Verify the plan
terraform plan
```

### **4. Deploy ArgoCD**
```bash
# Apply the configuration
terraform apply

# Confirm with 'yes' when prompted
```

### **5. Verify Deployment**
```bash
# Check ArgoCD pods
kubectl get pods -n argocd

# Check ArgoCD services
kubectl get svc -n argocd

# Check ArgoCD namespace
kubectl get namespace argocd
```

## 🌐 **Access ArgoCD**

### **Method 1: Via k3d Port Mapping**
- **URL**: `http://localhost:8081`
- **Username**: `admin`
- **Password**: `admin123` (as configured in terraform.tfvars)

### **Method 2: Via kubectl port-forward**
```bash
# Port forward to local machine
kubectl port-forward svc/argocd-server -n argocd 8080:443

# Access via: https://localhost:8080
```

## 📁 **File Structure**

```
infrastructure/terraform/
├── main.tf                 # Main Terraform configuration
├── variables.tf            # Variable definitions
├── outputs.tf              # Output values
├── terraform.tfvars        # Variable values
└── README_TERRAFORM.md     # This file
```

## ⚙️ **Configuration**

### **Ports**
- **NodePort**: 30080 (internal cluster port)
- **External Port**: 8081 (mapped to your local machine)

### **Resources**
- **Server**: 500m CPU, 512Mi Memory
- **Repo Server**: 500m CPU, 512Mi Memory
- **Application Controller**: 500m CPU, 512Mi Memory

### **Customization**
Edit `terraform.tfvars` to modify:
- Admin password
- Resource limits
- Port mappings
- ArgoCD version

## 🔧 **Terraform Commands**

```bash
# Make sure you're in the terraform directory
cd infrastructure/terraform

# Initialize
terraform init

# Plan deployment
terraform plan

# Apply changes
terraform apply

# Show outputs
terraform output

# Destroy deployment
terraform destroy

# Show current state
terraform show
```

## 🗑️ **Cleanup**

### **Remove ArgoCD**
```bash
# Navigate to terraform directory
cd infrastructure/terraform

# Destroy Terraform resources
terraform destroy

# Confirm with 'yes'
```

### **Verify Cleanup**
```bash
# Check if namespace is gone
kubectl get namespace argocd

# Check if any ArgoCD resources remain
kubectl get all --all-namespaces | grep argocd
```

## 🚨 **Troubleshooting**

### **Common Issues**

1. **Provider Issues**
   ```bash
   # Reinitialize providers
   terraform init -upgrade
   ```

2. **Namespace Stuck in Terminating**
   ```bash
   # Force delete namespace
   kubectl patch namespace argocd -p '{"metadata":{"finalizers":[]}}' --type=merge
   kubectl delete namespace argocd
   ```

3. **Port Already in Use**
   ```bash
   # Check what's using the port
   lsof -i :8081
   
   # Kill the process or change the port in terraform.tfvars
   ```

4. **Wrong Directory**
   ```bash
   # Make sure you're in the terraform directory
   pwd
   # Should show: .../infrastructure/terraform
   
   # If not, navigate there
   cd infrastructure/terraform
   ```

### **Logs and Debugging**
```bash
# Check ArgoCD server logs
kubectl logs -n argocd deployment/argocd-server

# Check ArgoCD application controller logs
kubectl logs -n argocd deployment/argocd-application-controller

# Check ArgoCD repo server logs
kubectl logs -n argocd deployment/argocd-repo-server
```

## 📊 **Monitoring**

### **Check ArgoCD Status**
```bash
# Pod status
kubectl get pods -n argocd -o wide

# Service status
kubectl get svc -n argocd

# Events
kubectl get events -n argocd --sort-by='.lastTimestamp'
```

### **Resource Usage**
```bash
# Check resource usage
kubectl top pods -n argocd

# Check resource limits
kubectl describe pods -n argocd
```

## 🔐 **Security Notes**

- **Change default password** in production
- **Use secrets management** for sensitive data
- **Enable RBAC** for production deployments
- **Consider network policies** for cluster security

## 📚 **Next Steps**

After successful deployment:
1. **Access ArgoCD UI** via `http://localhost:8081`
2. **Add Git repositories** as sources
3. **Create applications** for your workloads
4. **Set up GitOps workflows**

## 🔗 **Related Documentation**

- **Main README**: `../../README.md`
- **GitHub Actions Setup**: `../../SELF_HOSTED_RUNNER_SETUP.md`
- **Repository Structure**: See main README for overview

## 🆘 **Support**

If you encounter issues:
1. Check the troubleshooting section above
2. Verify your k3d cluster is running
3. Check Terraform and kubectl versions
4. Review the logs for error messages
5. Ensure you're in the correct directory (`infrastructure/terraform/`)
