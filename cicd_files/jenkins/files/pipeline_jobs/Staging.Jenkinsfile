#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/Staging.yaml"

pipeline {
    agent {
        label SLAVE
    }

    environment {
        HELM_CHART_REPO_CREDS = credentials('SELI_ARTIFACTORY')
        HELM_CHART_REPO_USER = "${HELM_CHART_REPO_CREDS_USR}"
        HELM_CHART_REPO_KEY = "${HELM_CHART_REPO_CREDS_PSW}"
        TB_GERRIT_CREDS = credentials('TB_GERRIT_USER')
    }

    stages {
        stage('Cleaning Git Repo') {
            steps {
                sh 'git clean -xdff'
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Install Kubeconfig') {
            steps {
                withCredentials([file(credentialsId: 'TB_OLAH023_SERVICE_ACCOUNT_KUBECONFIG', variable: 'KUBECONFIG')]) {
                    sh "install -vD -m 606 ${KUBECONFIG} ./.kube/config"
                }
            }
        }
        stage('Create and Switch to Staging Context') {
            steps {
                sh "${bob} create-and-switch-to-staging-context"
            }
        }
        stage('Run Helm Upgrade') {
            steps {
                sh "${bob} run-helm-upgrade"
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
