# openshift-msr-sample
Sample OpenShift examples that contains CI/CD Scripts

## CI (continuous integration and continuous deployment)

Jenkinsfile in the root of the project contain Jenkins Pipeline declaration. With this you will be able to set up the CI in Jenkins quickly.

Required environment variables (see Build.sh in the root directory) should be provided while configuring the CI job in Jenkins. Also update the yaml files in the root directory as per the requirement. ( route.yaml needs to be updated with the host name) 

Currently cleanup.sh is commented in Jenkinsfile.

Dockerfile in the repository assumes that "microruntime:v1" base image is available in the local registry

For more information about Microservices Runtime, see the official Software AG Microservices Runtime documentation.
  ______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.	

