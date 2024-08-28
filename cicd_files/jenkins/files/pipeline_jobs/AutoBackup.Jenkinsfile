#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/AutoBackup.yaml"

pipeline {
    agent {
        label env.SLAVE
    }
    parameters {
        string(name: 'SLAVE',
                description: 'Slave to run the job on',
                defaultValue: 'GridEngine' )
        string(name: 'FUNCTIONAL_USER_SECRET',
                description: 'User secret to be used when authenticating request to the artifactory',
                defaultValue: 'SELI_ARTIFACTORY')
    }
    stages {
        stage('Clean Workspace') {
            steps {
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive --remote'
                sh "${bob} git-clean"
            }
        }
        stage('Build AutoBackup Script') {
            steps {
                sh "${bob} build-autobackup-script"
            }
        }
        stage('Run AutoBackup Script') {
            steps {
                sh "${bob} run-autobackup-script"
            }
        }
        stage('Upload Database Dump to Storage Location') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.FUNCTIONAL_USER_SECRET, usernameVariable: 'FUNCTIONAL_USER_USERNAME', passwordVariable: 'FUNCTIONAL_USER_PASSWORD')]) {
                    sh "${bob} upload-database-dump"
                }
            }
        }
    }
    post {
        always {
            cleanWs()
            sh '''
            docker rmi -f rpt-autobackup || true
            '''
        }
        failure {
            mail(
                subject: 'ERROR: RPT Auto Backup has failed!',
                from: 'thunderbee@seliius23664.seli.gic.ericsson.se',
                body: """
                    RPT Auto Backup Jenkins job has failed.</br>
                    Jenkins build: <a href='${BUILD_URL}'> Build ${BUILD_NUMBER} </a>""",
                to: 'PDLENMCOUN@pdl.internal.ericsson.com,19df51be.ericsson.onmicrosoft.com@emea.teams.ms',
                mimeType: 'text/html'
            )
        }
    }
}
