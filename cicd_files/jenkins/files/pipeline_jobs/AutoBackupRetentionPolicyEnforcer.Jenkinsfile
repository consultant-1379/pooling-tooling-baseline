#!/usr/bin/env groovy

def bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/AutoBackupRetentionPolicyEnforcer.yaml"

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
        stage('Build Retention Policy Enforcer Script') {
            steps {
                sh "${bob} build-retention-policy-enforcer-script"
            }
        }
        stage('Run Retention Policy Enforcer Script') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.FUNCTIONAL_USER_SECRET, usernameVariable: 'FUNCTIONAL_USER_USERNAME', passwordVariable: 'FUNCTIONAL_USER_PASSWORD')]) {
                    sh "${bob} run-retention-policy-enforcer-script"
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
