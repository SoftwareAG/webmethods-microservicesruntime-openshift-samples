# openshift-msr-sample
Sample OpenShift examples that contains CI/CD Scripts

## CI (continuous integration and continuous deployment)

Jenkinsfile in the root of the project contain Jenkins Pipeline declaration. With these you will be able to set up you CI in a matter of minutes.

Required environment variables (see Build.sh in the root directory) should be provided while configuring the CI job in Jenkins. Also update the yaml files in the root directory as per the requirement.

Dockerfile in the repository assumes that "microruntime:v1" base image is available in the local registry, this should be replaced with your docker registry location. e.g. FROM store/softwareag/webmethods-microservicesruntime:10.3.

For more information about Microservices Runtime, see the official Software AG Microservices Runtime documentation.
  ______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.	

Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
