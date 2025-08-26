# Hawk Infrastructure Repository

A comprehensive GitOps infrastructure repository featuring a Java Hello World application with zero-downtime deployments, managed by ArgoCD and deployed on Kubernetes.

## 🚀 **What's Included:**

### **1. Java Hello World Application**
- **Modern Java 17 application** with HTTP server
- **Zero-downtime deployments** using RollingUpdate strategy
- **Helm chart management** with version control
- **Docker containerization** for consistent deployments

### **2. GitOps Pipeline with ArgoCD**
- **Automated deployments** triggered by Git changes
- **Chart version management** for seamless updates
- **Zero-downtime strategy** ensuring continuous availability
- **Multi-replica deployment** for high availability

### **3. Infrastructure as Code**
- **ArgoCD Deployment** using Terraform + Helm
- **Kubernetes cluster management** with k3d
- **Resource optimization** for local development

## 🏗️ **Current Architecture:**

```
┌─────────────────────────────────────────────────────────────────┐
│                    GitOps Workflow                              │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Source Code   │───▶│   Git Push      │───▶│   ArgoCD       │
│   Changes       │    │   to develop    │    │   Detection     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Chart Version │    │   Helm Chart    │    │   Kubernetes    │
│   Bump          │    │   Processing    │    │   Deployment    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Zero-Downtime │    │   RollingUpdate │    │   Live App      │
│   Strategy      │    │   Strategy      │    │   (v0.1.3)     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### **⚠️ Important: Current vs. Production Architecture**

#### **Current Setup (Development/Testing):**
- ✅ **Single Repository**: Both application code and infrastructure code in one repo
- ✅ **Simplified workflow**: Easy to manage for learning and development
- ✅ **Quick iteration**: All changes in one place for rapid development

#### **Production Setup (Recommended):**
- 🏗️ **Infrastructure Repository**: `hawk-infra-repo`
  - Terraform configurations
  - ArgoCD applications
  - Helm charts
  - Infrastructure policies
- 🚀 **Application Repository**: `hawk-app-repo` (or separate repos per app)
  - Source code
  - Application-specific Helm values
  - CI/CD pipelines
  - Application tests

## 🎯 **Key Advancements Implemented:**

### **1. Zero-Downtime Deployments**
- ✅ **RollingUpdate strategy** with `maxUnavailable: 0`
- ✅ **Minimum 2 replicas** ensuring continuous availability
- ✅ **Health checks** (liveness and readiness probes)
- ✅ **Smooth transitions** between application versions

### **2. GitOps Automation**
- ✅ **Chart version management** triggers automatic deployments
- ✅ **ArgoCD integration** for declarative deployments
- ✅ **Git-based workflow** for infrastructure changes
- ✅ **Automated sync** with health monitoring

### **3. Production-Ready Configuration**
- ✅ **Resource limits** and requests configured
- ✅ **NodePort service** for external access
- ✅ **Proper health monitoring** and restart policies
- ✅ **Optimized for local development** with k3d

## 📁 **Repository Structure:**

```
hawk_infra_repo/
├── applications/
│   └── java-hello-world/           # Java application
│       ├── src/main/java/          # Source code
│       │   └── com/example/
│       │       └── HelloWorld.java # Main application
│       ├── helm-chart/             # Helm deployment
│       │   ├── Chart.yaml          # Chart metadata (v0.1.3)
│       │   ├── values.yaml         # Configuration values
│       │   └── templates/          # K8s manifests
│       ├── Dockerfile              # Container build
│       ├── build.gradle            # Java build configuration
│       └── argocd-application.yaml # ArgoCD configuration
├── infrastructure/
│   └── terraform/                  # Infrastructure code
├── .github/workflows/              # GitHub Actions
└── README.md                       # This file
```

## 🚀 **Quick Start:**

### **0. Prerequisites & Local Cluster Setup**
```bash
# Install required tools
brew install k3d kubectl helm terraform

# Create local k3d cluster for development
k3d cluster create gitops-demo \
  --servers 1 \
  --agents 1 \
  --image rancher/k3s:v1.30.2-k3s1 \
  --k3s-node-label "role=server,env=dev@server:0" \
  --k3s-node-label "role=agent,env=dev@server:0" \
  --port "8081:30080@loadbalancer" \
  --port "8444:30443@loadbalancer" \
  --port "8445:30444@loadbalancer"

# Verify cluster is running
kubectl cluster-info
kubectl get nodes

# Set context to your cluster
kubectl config use-context k3d-gitops-demo
```

#### **Port Mappings Explained:**
- **`8081:30080`** → ArgoCD UI (http://localhost:8081)
- **`8444:30443`** → ArgoCD HTTPS (https://localhost:8444)
- **`8445:30444`** → Your Java Hello World app (http://localhost:8445)

#### **Cluster Configuration:**
- **Name**: `gitops-demo`
- **Nodes**: 1 server + 1 agent
- **Kubernetes Version**: v1.30.2-k3s1
- **Purpose**: Local development and testing
- **Resource Usage**: Lightweight, perfect for local development

#### **Cluster Management Commands:**
```bash
# Stop the cluster (saves resources)
k3d cluster stop gitops-demo

# Start the cluster again
k3d cluster start gitops-demo

# Delete the cluster completely
k3d cluster delete gitops-demo

# List all clusters
k3d cluster list

# View cluster logs
k3d cluster logs gitops-demo
```

#### **Troubleshooting Common Issues:**
```bash
# If ports are already in use
k3d cluster delete gitops-demo
# Wait a few seconds, then recreate

# If cluster won't start
kubectl config unset current-context
k3d cluster start gitops-demo

# Check cluster health
kubectl get componentstatuses
kubectl get pods --all-namespaces
```

### **1. Deploy the Application**
```bash
# Navigate to terraform directory
cd infrastructure/terraform

# Deploy ArgoCD
terraform init
terraform plan
terraform apply

# Access ArgoCD at: http://localhost:8081
# Username: admin, Password: admin123
```

### **2. Access Your Application**
```bash
# Application is accessible at:
http://localhost:8445

# You should see: "🚀 Hello Hawk World!"
```

### **3. Make Code Changes**
```bash
# 1. Modify HelloWorld.java
# 2. Bump Chart.yaml version
# 3. Commit and push
# 4. ArgoCD automatically redeploys with zero downtime
```

## 🔧 **How Zero-Downtime Works:**

### **Before Update:**
- 2 pods running (v0.1.2)
- Application serving traffic normally

### **During Update:**
- 1 new pod starts up (v0.1.3)
- 2 old pods continue running
- New pod becomes ready
- Traffic shifts to new pod

### **After Update:**
- 2 new pods running (v0.1.3)
- Old pods terminated
- **Zero downtime achieved!**

## 📊 **Current Application Status:**

- **Chart Version**: 0.1.3
- **Replicas**: 2 (for zero-downtime)
- **Strategy**: RollingUpdate
- **Health Checks**: Liveness + Readiness probes
- **Service Type**: NodePort (port 8445)
- **Status**: ✅ Production-ready with zero-downtime

## 🎯 **Suggested Improvements:**

### **1. ArgoCD Image Updater (High Priority)**
```yaml
# Install ArgoCD Image Updater for automatic Docker image detection
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj-labs/argocd-image-updater/stable/manifests/install.yaml
```
**Benefits:**
- Automatically detects new Docker images
- Updates deployments without manual chart version bumps
- True GitOps workflow for code changes

### **2. Multi-Environment Setup (Medium Priority)**
```yaml
# Create separate ArgoCD applications for dev/staging/prod
# Use different values files and resource limits
# Implement environment-specific configurations
```
**Benefits:**
- Safe testing in dev/staging before production
- Environment-specific resource allocation
- Better development workflow

### **3. Repository Separation (Production Readiness)**
**Current State**: Single repository for both application and infrastructure
**Target State**: Separate repositories for better separation of concerns

#### **Benefits of Separate Repositories:**
- 🔒 **Security**: Infrastructure changes don't require application code access
- 👥 **Team Separation**: Different teams can manage different aspects
- 🚀 **Independent Release Cycles**: Apps and infra can evolve separately
- 📊 **Better Audit Trail**: Clear separation of what changed where
- 🎯 **Focused CI/CD**: Application-specific pipelines and infrastructure pipelines

#### **Migration Path:**
```bash
# 1. Create Infrastructure Repository
git clone https://github.com/your-org/hawk-infra-repo.git
# Move: infrastructure/, .github/workflows/, ArgoCD apps

# 2. Create Application Repository  
git clone https://github.com/your-org/hawk-app-repo.git
# Move: applications/, Dockerfile, build.gradle

# 3. Update ArgoCD to point to new repos
# 4. Update CI/CD pipelines
# 5. Test deployment from separate repos
```

#### **Production Repository Structure:**
```
hawk-infra-repo/           # Infrastructure Repository
├── infrastructure/
│   └── terraform/         # Terraform configurations
├── argocd/
│   └── applications/      # ArgoCD application manifests
├── helm-charts/           # Shared Helm charts
└── .github/workflows/     # Infrastructure CI/CD

hawk-app-repo/             # Application Repository
├── applications/
│   └── java-hello-world/  # Your Java application
├── ci/                     # Application CI/CD
└── docs/                   # Application documentation
```

## 🔍 **Monitoring & Debugging:**

### **Check Application Status:**
```bash
# View ArgoCD application
kubectl get application -n argocd

# Check deployment status
kubectl get deployment -n hawk-apps

# View application logs
kubectl logs -n hawk-apps -l app=java-hello-world
```

### **Access ArgoCD UI:**
- **URL**: http://localhost:8081
- **Username**: admin
- **Password**: admin123

## 🚨 **Security & Best Practices:**

- ✅ **Resource limits** configured
- ✅ **Health checks** implemented
- ✅ **Zero-downtime** strategy
- 🔄 **Change default passwords** in production
- 🔄 **Enable RBAC** for production
- 🔄 **Use secrets management** for sensitive data

## 🔮 **Future Roadmap:**

- **CI/CD Pipeline Integration** with GitHub Actions
- **Multi-Environment Deployments** (dev/staging/prod)
- **Application Monitoring** with Prometheus/Grafana
- **Security Scanning** with Trivy or similar tools
- **Backup & Disaster Recovery** strategies

## 🆘 **Troubleshooting:**

### **Common Issues:**
1. **Chart version not updating**: Ensure Chart.yaml version is bumped
2. **Downtime during deployment**: Check RollingUpdate strategy configuration
3. **Application not accessible**: Verify NodePort service configuration

### **Getting Help:**
- Check ArgoCD application status in UI
- Review Kubernetes events: `kubectl get events -n hawk-apps`
- Check application logs for errors

---

**🎉 Your Hawk Infrastructure is now production-ready with zero-downtime deployments!**

**Ready to deploy?** Follow the Quick Start guide above, or explore the ArgoCD UI to see your application in action.
