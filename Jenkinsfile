pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'meenigam/calculator'
        DOCKER_TAG = 'latest'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Pull code from GitHub repository
                git branch: 'main', url: 'https://github.com/Dheeraj-Murthy/calculator-jenkins.git'
                echo 'Successfully checked out code from repository'
            }
        }
        
        stage('Build') {
            steps {
                echo 'Installing dependencies...'
                sh 'python3 -m pip install -r requirements.txt'
                echo 'Dependencies installed successfully'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'python3 -m pytest tests/ -v --junitxml=test-results.xml'
                echo 'Tests completed successfully'
            }
            post {
                always {
                    // Archive test results
                    junit 'test-results.xml'
                }
                success {
                    echo 'All tests passed!'
                }
                failure {
                    echo 'Tests failed! Pipeline will stop.'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh '''
                        export DOCKER_CONFIG=/tmp
                        echo '{"credsStore": ""}' > /tmp/config.json
                        /usr/local/bin/docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                    '''
                    echo 'Docker image built successfully'
                }
            }
        }
        
        stage('Push to DockerHub') {
            steps {
                script {
                    echo 'Pushing Docker image to DockerHub...'
                    
                    // Login to DockerHub (credentials should be configured in Jenkins)
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'meenigam', passwordVariable: 'dheerumurthy123')]) {
                        sh "DOCKER_CONFIG=/tmp echo ${DOCKER_PASS} | /usr/local/bin/docker login -u ${DOCKER_USER} --password-stdin"
                    }
                    
                    // Push the image
                    sh "DOCKER_CONFIG=/tmp /usr/local/bin/docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    echo 'Docker image pushed successfully to DockerHub'
                    
                    // Logout from DockerHub
                    sh 'DOCKER_CONFIG=/tmp /usr/local/bin/docker logout'
                }
            }
        }
    }
    
    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
            echo "Docker image ${DOCKER_IMAGE}:${DOCKER_TAG} is available on DockerHub"
        }
        failure {
            echo 'Pipeline failed! Please check the logs above.'
        }
    }
}
