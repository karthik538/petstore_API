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
                dir('petstore2') {
                    // Update this path to match YOUR laptop's actual path
                    // NOTICE: Use double slashes \\ for Windows paths
                    bat 'mvn clean test -Dmaven.repo.local="C:\\Users\\karth\\.m2\\repository"'
                }
            }
        }
    }

    post {
        always {
            junit 'petstore2/target/surefire-reports/*.xml'
        }
    }
}
C:\Users\karth\.m2\repository
