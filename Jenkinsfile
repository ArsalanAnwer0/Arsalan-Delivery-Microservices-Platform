pipeline {
    agent any

    environment {
        COMPOSE_FILE = 'docker-compose.yaml'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

       stage('Prepare Environment') {
           steps {
               withCredentials([file(credentialsId: 'my-app-env', variable: 'ENV_FILE')]) {

                   sh 'cp \$ENV_FILE .env'
               }
           }
       }
stage('Run Tests') {
    parallel {
        stage('Order Service') {
            steps {
                sh "docker compose -f ${COMPOSE_FILE} run --rm order-service ./gradlew test"
            }
        }
        stage('Payment Service') {
            steps {
                sh "docker compose -f ${COMPOSE_FILE} run --rm payment-service ./gradlew test"
            }
        }
        stage('Warehouse Service') {
            steps {
                sh "docker compose -f ${COMPOSE_FILE} run --rm warehouse-service ./gradlew test"
            }
        }
    }
}
        stage('Stop Old Containers') {
            steps {
                sh "docker compose -f ${COMPOSE_FILE} down || true"
            }
        }

        stage('Build & Deploy') {
            steps { 
                sh "docker compose -f ${COMPOSE_FILE} up -d --build"
            }
        }

        stage('Cleanup') {
            steps { 
                sh "docker image prune -f"
            }
        }
    }
    
    post {
        failure {
            echo "Build Failed."
        }
        success {
            echo "Build Success"
        }
    }
}