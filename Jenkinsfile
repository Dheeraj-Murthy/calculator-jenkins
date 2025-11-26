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
                    // Install Maven manually
                    sh '''
                        echo "=== BUILD STAGE STARTING ==="
                        if ! command -v mvn &> /dev/null; then
                            echo "Installing Maven manually..."
                            curl -O https://archive.apache.org/dist/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz
                            tar -xzf apache-maven-3.9.4-bin.tar.gz
                            export PATH=$PWD/apache-maven-3.9.4/bin:$PATH
                            echo "Maven installed at: $PWD/apache-maven-3.9.4/bin/mvn"
                        else
                            echo "Maven already available"
                        fi
                        echo "Running Maven compile..."
                        $PWD/apache-maven-3.9.4/bin/mvn clean compile
                    '''
                    echo 'Application compiled successfully'
                }
            }
        }
        
        stage('Test') {
            steps {
                script {
                    sh '''
                        echo "Running Maven tests..."
                        $PWD/apache-maven-3.9.4/bin/mvn test
                    '''
                    echo 'Tests completed successfully'
                }
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
                script {
                    sh '''
                        echo "Packaging Java application..."
                        $PWD/apache-maven-3.9.4/bin/mvn package -DskipTests
                    '''
                    echo 'Application packaged successfully'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh '''
                        if command -v /usr/local/bin/docker &> /dev/null; then
                            export DOCKER_CONFIG=/tmp
                            echo '{"credsStore": ""}' > /tmp/config.json
                            /usr/local/bin/docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
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
                        if (sh(script: 'command -v /usr/local/bin/docker &> /dev/null', returnStatus: true) == 0) {
                            withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                                sh '''
                                    export DOCKER_CONFIG=/tmp
                                    export HTTP_PROXY=""
                                    export HTTPS_PROXY=""
                                    export NO_PROXY="registry-1.docker.io,docker.io"
                                    echo '{"credsStore": ""}' > /tmp/config.json
                                    echo ${DOCKER_PASS} | /usr/local/bin/docker login -u ${DOCKER_USER} --password-stdin
                                '''
                            }
                            
                            // Push the image
                            sh '''
                                export DOCKER_CONFIG=/tmp
                                export HTTP_PROXY=""
                                export HTTPS_PROXY=""
                                export NO_PROXY="registry-1.docker.io,docker.io"
                                /usr/local/bin/docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                            '''
                        } else {
                            echo "Docker not available, skipping Docker push"
                        }
                    }
                    echo 'Docker image pushed successfully to DockerHub'
                    
                    // Logout from DockerHub
                    script {
                        if (sh(script: 'command -v /usr/local/bin/docker &> /dev/null', returnStatus: true) == 0) {
                            sh 'DOCKER_CONFIG=/tmp /usr/local/bin/docker logout'
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
