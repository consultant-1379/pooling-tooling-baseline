#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/ElasticsearchCurator.yaml"

pipeline {
    agent {
        label env.SLAVE
    }
    parameters {
        string(name: 'SLAVE',
                description: 'Slave to run the job on',
                defaultValue: 'GridEngine' )
    }
    stages {
        stage('Clean Workspace') {
            steps {
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive --remote'
                sh "${bob} git-clean"
            }
        }
        stage('Build Elasticsearch Curator') {
            steps {
                sh "${bob} build-elasticseacrh-curator"
            }
        }
        stage('Run Elasticsearch Curator') {
            steps {
                sh "${bob} run-elasticseacrh-curator"
            }
        }
    }
    post {
        always {
            cleanWs()
            sh '''
            docker stop rpt-curator-delete-index || true
            docker rm rpt-curator-delete-index || true
            docker rmi rpt-curator || true
            '''
        }
    }
}