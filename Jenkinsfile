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
    // В декларативном пайплайне parallel ставится ВМЕСТО steps, а не ВНУТРИ steps
    parallel {
        stage('Order Service') {
            steps {
                echo "Testing Order Service..."
                sh "docker compose -f ${COMPOSE_FILE} run --rm order-platform.order-service ./gradlew test"
            }
        }
        stage('Payment Service') {
            steps {
                echo "Testing Payment Service..."
                sh "docker compose -f ${COMPOSE_FILE} run --rm order-platform.payment-service ./gradlew test"
            }
        }
        stage('Warehouse Service') {
            steps {
                echo "Testing Warehouse Service..."
                sh "docker compose -f ${COMPOSE_FILE} run --rm order-platform.warehouse-service ./gradlew test"
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