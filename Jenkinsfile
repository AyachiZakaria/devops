pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ayachizakaria/events_project:1.0.0"
        DOCKER_REGISTRY_CREDENTIALS = 'dckr_pat_JzVnKoPEgRjo2W4J7jQaSj3Kyz8'
    }


    stages {
        stage('Checkout GIT Repository') {
            steps {
                echo 'Pulling...'
                git branch: 'main', url: 'https://github.com/AyachiZakaria/devops.git'
            }
        }

        stage('Testing Maven') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Maven Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('JUnit/Mockito') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.token=squ_fa2963c59c49aabc32cb3c5215cc92df27f3743d'
            }
        }

        stage('Prepare Distribution') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn clean deploy'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t ${env.DOCKER_IMAGE} .'
                }
            }
        }

        stage('Push DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_REGISTRY_CREDENTIALS) {
                        sh 'docker push $DOCKER_IMAGE'
                    }
                }
            }
        }

        stage('Deploy Using Docker Compose') {
            steps {
                echo 'Deploying Docker containers with docker-compose...'
                sh 'docker-compose down' // Stop running containers, if any
                sh 'docker-compose up -d' // Launch new containers in detached mode
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }

        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}
