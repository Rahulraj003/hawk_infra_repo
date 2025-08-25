# Self-Hosted GitHub Actions Runner Setup

This guide will help you set up a GitHub Actions runner on your local Mac so that workflows can access your local k3d Kubernetes cluster.

## Why Self-Hosted Runner?

- **Direct Access**: The runner runs on your Mac where k3d cluster is running
- **Security**: No need to expose your cluster to the internet
- **Performance**: Faster execution since it's local
- **Full Access**: Can use all your local tools (kubectl, k3d, etc.)

## Prerequisites

- macOS with k3d and kubectl installed
- GitHub repository with Actions enabled
- Administrative access to your Mac

## Step 1: Create a Personal Access Token

1. Go to GitHub → Settings → Developer settings → Personal access tokens
2. Click "Generate new token (classic)"
3. Give it a name like "GitHub Actions Self-Hosted Runner"
4. Select scopes:
   - `repo` (full control of private repositories)
   - `workflow` (update GitHub Action workflows)
5. Click "Generate token"
6. **Copy the token** (you won't see it again!)

## Step 2: Add Runner to Your Repository

1. Go to your repository → Settings → Actions → Runners
2. Click "New self-hosted runner"
3. Select your operating system (macOS)
4. Follow the setup instructions

## Step 3: Download and Configure Runner

Run these commands on your Mac:

```bash
# Create a directory for the runner
mkdir actions-runner && cd actions-runner

# Download the runner (replace with your actual download URL from GitHub)
curl -o actions-runner-osx-x64-2.311.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.311.0/actions-runner-osx-x64-2.311.0.tar.gz

# Extract the runner
tar xzf ./actions-runner-osx-x64-2.311.0.tar.gz

# Configure the runner (replace with your actual values from GitHub)
./config.sh --url https://github.com/YOUR_USERNAME/YOUR_REPO --token YOUR_RUNNER_TOKEN

# Install and start the runner service
./svc.sh install
./svc.sh start
```

## Step 4: Verify Runner is Active

1. Go back to your repository → Settings → Actions → Runners
2. You should see your runner listed as "Idle" or "Active"

## Step 5: Test the Workflow

1. Push the workflow files to your repository
2. Go to Actions tab
3. Select "Local K8s Cluster Info (Self-Hosted Runner)"
4. Click "Run workflow"
5. The workflow will now run on your local Mac!

## Step 6: Verify Cluster Access

The workflow should now be able to:
- ✅ Access your local k3d cluster
- ✅ Show current context
- ✅ List all namespaces
- ✅ Display pods across all namespaces
- ✅ Show cluster resources and status

## Troubleshooting

### Runner Not Starting
```bash
# Check runner status
./svc.sh status

# Restart runner
./svc.sh restart

# Check logs
tail -f ~/.actions-runner/_diag/*.log
```

### Permission Issues
```bash
# Make sure runner has access to kubectl
sudo chown -R $USER:$USER actions-runner/

# Check kubectl access
kubectl cluster-info
```

### Cluster Not Accessible
```bash
# Verify k3d cluster is running
k3d cluster list

# Check cluster status
k3d cluster get YOUR_CLUSTER_NAME

# Test kubectl connection
kubectl get nodes
```

## Security Considerations

- The runner runs on your local machine
- It has access to your local files and network
- Only run trusted workflows
- Consider using runner groups for better access control

## Benefits of This Setup

1. **Full Cluster Access**: Workflows can see everything in your local cluster
2. **No Network Exposure**: Your cluster stays private
3. **Fast Execution**: No network latency
4. **Real-time Data**: Always current cluster information
5. **Integration**: Can trigger workflows based on cluster changes

## Next Steps

Once this is working, you can:
- Schedule regular cluster health checks
- Trigger deployments based on cluster state
- Monitor resource usage over time
- Integrate with other CI/CD tools

## Support

If you encounter issues:
1. Check the runner logs
2. Verify k3d cluster status
3. Test kubectl commands manually
4. Check GitHub Actions runner documentation
