pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        BUILD_NUMBER = "${BUILD_NUMBER}"
    }

    stages {

        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage("Deploy to QA Environment") {
            steps {
                echo("Deploying the project to QA Env")
            }
        }

        stage('Run Docker Image with Regression Tests') {
            steps {
                script {
                    echo "Starting Regression Tests in Docker container..."

                    // Remove old container if exists
                    bat "docker rm -f apitesting${BUILD_NUMBER} 2>nul"

                    def exitCode = bat(
                        script: """
docker run --name apitesting${BUILD_NUMBER} ^
-e MAVEN_OPTS=-Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml ^
mohanaprasath29/apirestassuredframeworklatest:latest
                        """,
                        returnStatus: true
                    )

                    if (exitCode != 0) {
                        currentBuild.result = 'FAILURE'
                    }

                    // Copy reports
                    bat "docker cp apitesting${BUILD_NUMBER}:/app/allure-results %WORKSPACE%\\allure-results"

                    // Remove container
                    bat "docker rm -f apitesting${BUILD_NUMBER}"
                }
            }
        }

        stage('Publish Allure Reports - Regression') {
            steps {
                script {
                    echo "Publishing Allure Reports..."
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        stage("Deploy to Stage") {
            steps {
                echo("Deploying the Project to Stage")
            }
        }

        stage('Run Docker Image with Sanity Tests') {
            steps {
                script {
                    echo "Starting Sanity Tests in Docker container..."

                    bat "docker rm -f apitesting_sanity${BUILD_NUMBER} 2>nul"

                    def exitCode = bat(
                        script: """
docker run --name apitesting_sanity${BUILD_NUMBER} ^
-e MAVEN_OPTS=-Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml ^
mohanaprasath29/apirestassuredframeworklatest:latest
                        """,
                        returnStatus: true
                    )

                    if (exitCode != 0) {
                        currentBuild.result = 'FAILURE'
                    }

                    bat "docker rm -f apitesting_sanity${BUILD_NUMBER}"
                }
            }
        }

        stage("Deploy to PROD") {
            steps {
                echo("Deploying the project to PROD")
            }
        }
    }
}