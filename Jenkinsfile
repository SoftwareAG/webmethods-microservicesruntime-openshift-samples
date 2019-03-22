pipeline {
    agent any 

    stages {
        stage('Initialize'){
            steps {
                sh 'chmod -R 777 ${WORKSPACE}'
            }
        }
		stage('Clean'){
            steps {
                sh '${WORKSPACE}/Cleanup.sh'
        	}
        }
		stage('Build'){
            steps {
                sh '${WORKSPACE}/Build.sh'
            }
        }
		stage('Deploy'){
            steps {
                sh '${WORKSPACE}/Deploy.sh'
            }
        }
    }
}
