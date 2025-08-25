# Hawk Infrastructure Repository

This repository contains GitHub Actions workflows for managing and monitoring local Kubernetes clusters.

## Kubernetes Cluster Information Workflow

### Self-Hosted Runner Workflow (`.github/workflows/k8s-local-cluster-self-hosted.yml`)

This workflow runs on your **local Mac machine** where your k3d cluster is running, giving it direct access to your local cluster.

**What it provides:**
- ✅ Current Kubernetes context
- ✅ Available namespaces
- ✅ Pods across all namespaces with status
- ✅ Cluster resources and status
- ✅ Node information and capacity
- ✅ Deployments, services, configmaps, and secrets
- ✅ Recent cluster events
- ✅ Complete cluster summary with resource counts

## How It Works

The workflow uses a **self-hosted GitHub Actions runner** that runs directly on your Mac, allowing it to:
- Access your local k3d cluster
- Use your local kubectl and k3d installations
- See real-time cluster information
- Maintain security (no need to expose cluster to internet)

## Prerequisites

- k3d installed on your local Mac
- A local Kubernetes cluster running via k3d
- GitHub repository with Actions enabled
- Self-hosted runner configured on your Mac

## Setup Instructions

### 1. Set Up Self-Hosted Runner
Follow the detailed setup guide in [`SELF_HOSTED_RUNNER_SETUP.md`](SELF_HOSTED_RUNNER_SETUP.md) to:
- Install GitHub Actions runner on your Mac
- Configure it to connect to your repository
- Ensure it has access to kubectl and k3d

### 2. Verify Local Cluster
```bash
# Check k3d cluster status
k3d cluster list

# Verify kubectl access
kubectl cluster-info

# Test pod listing
kubectl get pods --all-namespaces
```

### 3. Run the Workflow
1. Push the workflow files to your repository
2. Go to Actions tab
3. Select "Local K8s Cluster Info (Self-Hosted Runner)"
4. Click "Run workflow"

## What You'll See

The workflow will output:
- **Current Context**: The Kubernetes context being used
- **Cluster Information**: Version, API server details
- **Namespaces**: All available namespaces in your cluster
- **Pods**: All pods across all namespaces with their status
- **Resources**: Deployments, services, and other Kubernetes resources
- **Summary**: Total counts of various resource types

## Benefits of This Approach

1. **Direct Access**: Workflows run on your Mac where the cluster is
2. **Security**: No need to expose your cluster to the internet
3. **Performance**: Fast execution with no network latency
4. **Real-time Data**: Always current cluster information
5. **Full Integration**: Can use all your local tools and configurations

## Troubleshooting

- Ensure your self-hosted runner is active and connected
- Verify that kubectl can connect to your local cluster
- Check the workflow logs for any error messages
- Ensure the runner has proper permissions to access kubectl

## Customization

You can modify the workflow to:
- Add additional kubectl commands
- Filter resources by labels or namespaces
- Export data in different formats
- Add notifications or webhooks
- Schedule regular cluster health checks

## Security Considerations

- The self-hosted runner runs on your local machine
- It has access to your local files and network
- Only run trusted workflows
- Consider using runner groups for better access control
