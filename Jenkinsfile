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
                    // Use Maven Docker container to build the application
                    sh '''
                        docker run --rm \
                            -v ${WORKSPACE}:/workspace \
                            -w /workspace \
                            maven:3.9.4-eclipse-temurin-17 \
                            mvn clean compile
                    '''
                    echo 'Application compiled successfully'
                }
            }
        }
        
        stage('Test') {
            steps {
                script {
                    // Use Maven Docker container to run tests
                    sh '''
                        docker run --rm \
                            -v ${WORKSPACE}:/workspace \
                            -w /workspace \
                            maven:3.9.4-eclipse-temurin-17 \
                            mvn test
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
                    // Use Maven Docker container to package the application
                    sh '''
                        docker run --rm \
                            -v ${WORKSPACE}:/workspace \
                            -w /workspace \
                            maven:3.9.4-eclipse-temurin-17 \
                            mvn package -DskipTests
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
