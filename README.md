# Hawk Infrastructure Repository

This repository contains GitHub Actions workflows for managing and monitoring local Kubernetes clusters, plus Terraform configurations for infrastructure deployment.

## 🚀 **What's Included:**

### **1. GitHub Actions Workflow**
- **Self-Hosted Runner Workflow** (`.github/workflows/k8s-local-cluster-self-hosted.yml`)
- Monitors your local k3d cluster
- Shows current context, namespaces, pods, and cluster resources

### **2. Infrastructure as Code**
- **ArgoCD Deployment** using Terraform + Helm
- Located in `infrastructure/terraform/`
- Configured for your k3d `gitops-demo` cluster
- Includes resource management and monitoring

### **3. Application Code (Future)**
- **Applications folder** ready for your application code
- Can be integrated with ArgoCD for GitOps workflows

## 🏗️ **Infrastructure Components:**

### **ArgoCD Setup**
- **Namespace**: `argocd`
- **Port Mapping**: `localhost:8081` → `30080` (NodePort)
- **Admin**: `admin` / `admin123`
- **Resource Limits**: Optimized for local development

### **k3d Cluster Configuration**
```bash
k3d cluster create gitops-demo \
  --servers 1 \
  --agents 1 \
  --image rancher/k3s:v1.30.2-k3s1 \
  --k3s-node-label "role=server,env=dev@server:0" \
  --k3s-node-label "role=agent,env=dev@server:0" \
  --port "8081:30080@loadbalancer" \
  --port "8444:30443@loadbalancer"
```

## 📁 **Repository Structure:**

```
hawk_infra_repo/
├── .github/workflows/          # GitHub Actions workflows (common)
│   └── k8s-local-cluster-self-hosted.yml
├── infrastructure/              # All infrastructure code
│   └── terraform/              # Terraform configurations
│       ├── main.tf             # Main Terraform configuration
│       ├── variables.tf        # Variable definitions
│       ├── outputs.tf          # Output values
│       ├── terraform.tfvars    # Configuration values
│       └── README_TERRAFORM.md # Terraform deployment guide
├── applications/                # Application code (future)
├── README.md                   # This file
└── SELF_HOSTED_RUNNER_SETUP.md # GitHub Actions runner setup
```

## 🚀 **Quick Start:**

### **Option 1: Deploy ArgoCD with Terraform**
```bash
# Navigate to terraform directory
cd infrastructure/terraform

# Initialize and deploy
terraform init
terraform plan
terraform apply

# Access ArgoCD at: http://localhost:8081
# Username: admin, Password: admin123
```

## 📚 **Terraform Fundamentals (What You Need to Know):**

### **1. Terraform State File (`terraform.tfstate`)**
- **Purpose**: Tracks all resources created by Terraform
- **Location**: `infrastructure/terraform/terraform.tfstate`
- **Why Important**: Without it, Terraform can't manage existing resources
- **Management**: Should be committed to Git for testing environments

### **2. Terraform Lock File (`.terraform.lock.hcl`)**
- **Purpose**: Locks provider versions for consistent deployments
- **Location**: `infrastructure/terraform/.terraform.lock.hcl`
- **Why Important**: Prevents provider updates from breaking deployments
- **Management**: Should be committed to Git for reproducibility

### **3. Complete Terraform Workflow**
```bash
# 1. Initialize (downloads providers, creates lock file)
terraform init

# 2. Plan (shows what will change)
terraform plan

# 3. Apply (creates resources, generates state file)
terraform apply

# 4. State management (tracking created resources)
terraform state list
terraform show
```

### **4. Files to Commit After Deployment**
```bash
# Both files should be committed for testing environments
git add terraform.tfstate .terraform.lock.hcl
git commit -m "Update Terraform state and lock file after deployment"
git push origin develop
```

### **Option 2: Monitor Cluster with GitHub Actions**
1. Set up self-hosted runner (see `SELF_HOSTED_RUNNER_SETUP.md`)
2. Push code to trigger workflow
3. View cluster information in Actions tab

## 🔧 **Prerequisites:**

- **k3d cluster** running (`gitops-demo`)
- **Terraform** (version >= 1.0)
- **kubectl** configured
- **Helm** installed
- **GitHub repository** with Actions enabled

## 📚 **Documentation:**

- **`infrastructure/terraform/README_TERRAFORM.md`** - Complete Terraform deployment guide
- **`SELF_HOSTED_RUNNER_SETUP.md`** - GitHub Actions runner setup
- **`README.md`** - This overview file

## 🎯 **Use Cases:**

### **Development & Testing:**
- Local Kubernetes cluster management
- Infrastructure as Code with Terraform
- GitOps workflows with ArgoCD
- Automated cluster monitoring
- Application deployment and management

### **Learning & Experimentation:**
- Kubernetes resource management
- Terraform infrastructure deployment
- GitHub Actions automation
- GitOps principles
- Full-stack development workflow

## 🆘 **Getting Help:**

1. **Terraform Issues**: Check `infrastructure/terraform/README_TERRAFORM.md`
2. **GitHub Actions**: Check `SELF_HOSTED_RUNNER_SETUP.md`
3. **Cluster Issues**: Use the monitoring workflow
4. **General Questions**: Review this README

## 🔄 **Workflow:**

1. **Deploy Infrastructure** → Terraform + ArgoCD
2. **Monitor Cluster** → GitHub Actions workflow
3. **Develop Applications** → Applications folder
4. **Deploy Applications** → ArgoCD GitOps
5. **Iterate & Improve** → Update configurations

## 🚨 **Security Notes:**

- **Change default passwords** in production
- **Use secrets management** for sensitive data
- **Enable RBAC** for production deployments
- **Consider network policies** for cluster security

## 🔮 **Future Enhancements:**

- **Application templates** in applications folder
- **Helm charts** for common services
- **CI/CD pipelines** for applications
- **Monitoring and logging** stacks
- **Security scanning** workflows

---

**Ready to get started?** Choose your path:
- 🏗️ **Deploy ArgoCD**: Follow `infrastructure/terraform/README_TERRAFORM.md`
- 📊 **Monitor Cluster**: Follow `SELF_HOSTED_RUNNER_SETUP.md`
- 🔍 **Explore**: Check the workflow files and configurations
