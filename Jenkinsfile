pipeline {
    agent any

    tools {
        // These names must match what you set up in Jenkins "Global Tool Configuration"
        // If you haven't named them yet, use 'maven' or the specific name you gave.
        maven 'maven' 
        jdk 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                // Jenkins automatically checks out the code from the repo you configure in the job
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Windows uses 'bat' instead of 'sh'
                // 'clean test' triggers your API tests (assuming Maven)
                bat 'mvn clean test'
            }
        }
    }

    post {
        always {
            // Generates a report (Requires JUnit plugin or similar)
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
