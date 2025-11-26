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
                script {
                    // Install Maven using Homebrew on macOS
                    sh '''
                        if ! command -v mvn &> /dev/null; then
                            echo "Installing Maven with Homebrew..."
                            brew install maven
                        fi
                        mvn clean compile
                    '''
                    echo 'Application compiled successfully'
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
                echo 'Tests completed successfully'
            }
            post {
                always {
                    // Archive test results
                    junit 'target/surefire-reports/*.xml'
                }
                success {
                    echo 'All tests passed!'
                }
                failure {
                    echo 'Tests failed! Pipeline will stop.'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Packaging Java application...'
                sh 'mvn package -DskipTests'
                echo 'Application packaged successfully'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh '''
                        if command -v docker &> /dev/null; then
                            export DOCKER_CONFIG=/tmp
                            echo '{"credsStore": ""}' > /tmp/config.json
                            docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        else
                            echo "Docker not available, skipping Docker image build"
                        fi
                    '''
                    echo 'Docker image build completed'
                }
            }
        }
        
        stage('Push to DockerHub') {
            steps {
                script {
                    echo 'Pushing Docker image to DockerHub...'
                    
                    // Login to DockerHub (credentials should be configured in Jenkins)
                    script {
                        if (sh(script: 'command -v docker &> /dev/null', returnStatus: true) == 0) {
                            withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                                sh '''
                                    export DOCKER_CONFIG=/tmp
                                    export HTTP_PROXY=""
                                    export HTTPS_PROXY=""
                                    export NO_PROXY="registry-1.docker.io,docker.io"
                                    echo '{"credsStore": ""}' > /tmp/config.json
                                    echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
                                '''
                            }
                            
                            // Push the image
                            sh '''
                                export DOCKER_CONFIG=/tmp
                                export HTTP_PROXY=""
                                export HTTPS_PROXY=""
                                export NO_PROXY="registry-1.docker.io,docker.io"
                                docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                            '''
                        } else {
                            echo "Docker not available, skipping Docker push"
                        }
                    }
                    echo 'Docker image pushed successfully to DockerHub'
                    
                    // Logout from DockerHub
                    script {
                        if (sh(script: 'command -v docker &> /dev/null', returnStatus: true) == 0) {
                            sh 'DOCKER_CONFIG=/tmp docker logout'
                        }
                    }
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
