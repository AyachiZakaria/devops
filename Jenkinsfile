pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ayachizakaria/events_project:1.0.0"
        DOCKER_CREDENTIALS_ID = 'DOCKER_REGISTRY_CREDENTIALS' // Jenkins Credential ID
        SONAR_TOKEN = 'squ_fa2963c59c49aabc32cb3c5215cc92df27f3743d'
    }

    stages {
        stage('Checkout Repository') {
            steps {
                echo 'Cloning the repository...'
                git branch: 'main', url: 'https://github.com/AyachiZakaria/devops.git'
            }
        }

        stage('Verify Maven Installation') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('Clean and Compile') {
            steps {
                echo 'Cleaning and compiling the project...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo 'Running tests with JUnit and Mockito...'
                sh 'mvn test'
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                echo 'Running SonarQube analysis...'
                sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN}"
            }
        }

        stage('Package Application') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying artifacts to Nexus repository...'
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    echo 'Pushing Docker image to DockerHub...'
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh '''
                            echo "Logging into DockerHub..."
                            docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
                            docker push ${DOCKER_IMAGE}
                        '''
                    }
                }
            }
        }

        stage('Deploy Using Docker Compose') {
            steps {
                echo 'Deploying application using Docker Compose...'
                sh '''
                    docker-compose down
                    docker-compose up -d
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed.'
        }
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs for errors.'
        }
    }
}
