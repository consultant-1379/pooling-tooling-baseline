#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/BuildAndPublish.yaml"

pipeline {
    agent {
        label SLAVE
    }

    environment {
        HELM_CHART_REPO_CREDS = credentials('SELI_ARTIFACTORY')
        HELM_CHART_REPO_USER = "${HELM_CHART_REPO_CREDS_USR}"
        HELM_CHART_REPO_KEY = "${HELM_CHART_REPO_CREDS_PSW}"
    }

    stages {
        stage('Cleaning Git Repo') {
            steps {
                sh 'git clean -xdff'
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Bump Chart Version') {
            steps {
                sh "${bob} set-path-env-variables bump-chart-dot-yaml-version"
            }
        }
        stage('Package RPT Chart') {
            steps {
                sh "${bob} package-rpt-chart"
            }
        }
        stage('Publish RPT Chart') {
            steps {
                sh "${bob} set-chart-version-env-variable publish-rpt-chart clean-up-docker-image"
            }
        }
        stage('Add Chart.yaml Version Bump') {
            steps {
                sh "${bob} add-chart-version-change"
            }
        }
        stage('Push Up Chart.yaml Version Bump') {
            steps {
                sh "${bob} push-chart-version-change"
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}