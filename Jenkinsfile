pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // This tells Jenkins to go inside the 'petstore2' folder
                dir('petstore2') {
                    bat 'mvn clean test'
                }
            }
        }
    }

    post {
        always {
            // We also need to tell Jenkins where to find the reports inside that folder
            junit 'petstore2/target/surefire-reports/*.xml'
        }
    }
}
