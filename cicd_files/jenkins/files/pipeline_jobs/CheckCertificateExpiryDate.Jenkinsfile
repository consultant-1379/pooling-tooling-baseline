#!/usr/bin/env groovy
import groovy.transform.Field

String pythonScript = "\
    \${WORKSPACE}/cicd_files/jenkins/scripts/check_certificate_expiry_date/check_certificate_expiry_date.py"

@Field String bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/CheckCertificateExpiryDate.yaml"

@Field List failingCerts = []

@Field List certificateIds = [
    'rpt-production',
    'rpt-staging'
]

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
                sh 'git clean -xdff'
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Set Kube Config') {
            steps {
                withCredentials([file(credentialsId: 'TB_OLAH023_SERVICE_ACCOUNT_KUBECONFIG', variable: 'KUBECONFIG')]) {
                    sh "install -vD -m 600 ${KUBECONFIG} ./.kube/config"
                }
            }
        }
        stage('Add Python files') {
            steps {
                sh "install -vD -m 600 ${pythonScript} ./.scripts/check_certs.py"
            }
        }
        stage('Check Certificates Expiry Date') {
            steps {
                checkCertificateExpiry()
            }
        }
        stage('Set Build Result') {
            when { expression { failingCerts.size() > 0 } }
            steps {
                script {
                    currentBuild.result = 'FAILURE'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        failure {
            mail(
            subject: 'WARNING: RPT certificate will expire within the next two months',
            from: 'thunderbee@seliius23664.seli.gic.ericsson.se',
            body: """
                The SSL certificate(s) ${failingCerts} will expire within the next two months.<br/>
                Please investigate and take appropriate action. <br/>
                For information on generating certificate for the application,
                see here: https://confluence-oss.seli.wh.rnd.internal.ericsson.com/x/4R8oHg <br/>
                Jenkins build: ${BUILD_URL}console""",
            to: 'PDLENMCOUN@pdl.internal.ericsson.com',
            mimeType: 'text/html'
        )
        }
    }
}
void checkCertificateExpiry() {
    certificateIds.each { id ->
        try {
            sh "${bob} --property secret-id=${id} check-certificate-expiry"
        } catch (error) {
            failingCerts.push(id)
        }
    }
}
