pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/AyachiZakaria/devops.git'
            }
        }

        stage('Build Project') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh './mvnw test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './mvnw sonar:sonar'
                }
            }
        }

        stage('Prepare for Distribution') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Upload to Nexus') {
            steps {
                sh '''
                curl -u admin:admin123 --upload-file target/your-app.jar \
                http://<NEXUS_IP>:8081/repository/maven-releases/your-app.jar
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t your-dockerhub-username/your-app:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push your-dockerhub-username/your-app:latest'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        always {
            echo 'Pipeline Finished!'
        }
    }
}
