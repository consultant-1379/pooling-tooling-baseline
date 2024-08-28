#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/LogDisplaySetup.yaml"

pipeline {
    agent {
        label SLAVE
    }
    options {
        ansiColor('xterm')
    }
    stages {
        stage('Clean Workspace') {
            steps {
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive --remote'
                sh "${bob} git-clean"
            }
        }
        stage('Install Kubeconfig') {
            steps {
                withCredentials([file(credentialsId: 'TB_OLAH023_SERVICE_ACCOUNT_KUBECONFIG', variable: 'KUBECONFIG')]) {
                    sh "install -vD -m 606 ${KUBECONFIG} ./.kube/config"
                }
            }
        }
        stage('Create and Switch to Log Context') {
            steps {
                sh "${bob} create-and-switch-to-log-context"
            }
        }
        stage('Run Helm Install Elastic') {
            steps {
                sh "${bob} run-helm-install-elastic"
            }
        }
        stage('Apply Elastic Ingress') {
            steps {
                sh "${bob} apply-elastic-ingress"
            }
        }
        stage('Run Helm Install Kibana') {
            steps {
                sh "${bob} run-helm-install-kibana"
            }
        }
        stage('Apply Kibana Ingress') {
            steps {
                sh "${bob} apply-kibana-ingress"
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
