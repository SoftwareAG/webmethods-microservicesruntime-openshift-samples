# openshift-msr-sample

Sample OpenShift examples that contains CI/CD Scripts

This project uses the Apache License Version 2.0. For details, see [the license file](LICENSE).


## CI (continuous integration and continuous deployment)

Jenkinsfile in the root of the project contain Jenkins Pipeline declaration. With this you will be able to set up the CI in Jenkins quickly.

Required environment variables (see Build.sh in the root directory) should be provided while configuring the CI job in Jenkins. Also update the yaml files in the root directory as per the requirement. ( route.yaml needs to be updated with the host name) 

Currently cleanup.sh is commented in Jenkinsfile.

MSR docker images are created with the -Dimage.name=registry.access.redhat.com/rhel7 option for use with OpenShift if using the IS docker scripts.

Dockerfile in the repository assumes that "microruntime:v1" base image is available in the local registry

For more information about Microservices Runtime, see the official Software AG Microservices Runtime documentation.
  ______________________
  
 Important!
--

Many samples uses Docker images of webMethods Microservices Runtime published on Docker store. 

You need to agree to download webMethods Microservices Runtime Docker image from Docker store https://hub.docker.com/_/softwareag-webmethods-microservicesruntime. Use the username and password of your Docker Store account that you used to accept license for the webMethods Microservices Runtime Docker image in your Docker environment. i.e. make sure you use "docker login" command with the same username and password.

______________________
Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.	

Steps


get IS image