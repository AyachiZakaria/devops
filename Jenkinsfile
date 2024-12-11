pipeline {
    agent any
    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling....'
                git branch: 'main',
                    url: 'https://github.com/AyachiZakaria/devops.git'
            }
        }
        stage('Testing maven') {
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
        stage('Maven SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.token=squ_fa2963c59c49aabc32cb3c5215cc92df27f3743d'
            }
        }
    }
}
