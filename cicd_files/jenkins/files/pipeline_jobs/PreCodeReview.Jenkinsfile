#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/PreCodeReview.yaml"

pipeline {
    agent {
        label SLAVE
    }
    stages {
        stage('Clean Workspace') {
            steps {
                sh 'git clean -xdff'
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Helm Chart Linting') {
            steps {
                script {
                    try {
                        sh "${bob} run-helm-linting"
                        sh '''
                        echo "======================"
                        echo "Helm linting passed!"
                        echo "======================"
                        '''
                    } catch(error) {
                        sh '''
                        echo "+++++++++++++++++++++++++++++++++++++++++++"
                        echo "Linting errors detected. Please check above"
                        echo "+++++++++++++++++++++++++++++++++++++++++++"
                        '''
                        currentBuild.result = "FAILURE"
                    }
                }
                sh 'echo -e "\n"'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}