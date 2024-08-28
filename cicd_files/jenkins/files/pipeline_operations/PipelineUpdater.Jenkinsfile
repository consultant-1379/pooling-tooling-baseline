def returnParametersForDsl() {
    return [SLAVE: env.SLAVE]
}

def getPipelineJobs() {
    def pipelineJobList = []

    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/PreCodeReview.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/BuildAndPublish.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/Staging.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/Upgrade.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/AutoBackup.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/AutoBackupRetentionPolicyEnforcer.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/CheckCertificateExpiryDate.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_jobs/ElasticsearchCurator.groovy')

    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_operations/PipelineGenerator.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/dsl/pipeline_operations/PipelineUpdater.groovy')

    return pipelineJobList.join('\n')
}

pipeline {
    agent {
        node {
            label SLAVE
        }
    }

    environment {
        DSL_CLASSPATH = 'cicd_files/dsl'
        CHANGED_FILES = sh(returnStdout: true, script: "git diff-tree --diff-filter=ACM --no-commit-id --name-only -r $GIT_COMMIT -- 'cicd_files/*'").replaceAll("\\n", " ")
    }

    stages {
        stage ('Validate required parameters set') {
            when {
                expression {
                    env.SLAVE == null
                }
            }

            steps {
                error ('Required parameter(s) not set. Please provide a value for all required parameters')
            }
        }

        stage ('Generate RPT Baseline Pipeline Jobs') {
            when {
                expression {
                    env.CHANGED_FILES != null
                }
            }
            steps {
                jobDsl targets: getPipelineJobs(),
                additionalParameters: returnParametersForDsl(),
                additionalClasspath: env.DSL_CLASSPATH
            }
        }

        stage ('Update RPT Baseline List View') {
            when {
                expression {
                    env.CHANGED_FILES != null
                }
            }
            steps {
                jobDsl targets: 'cicd_files/dsl/views/View.groovy',
                    additionalClasspath: env.DSL_CLASSPATH
            }
        }
    }

    post {
        success {
            build propagate: false, wait: false, job: 'resource-pooling-tool-baseline_Spinnaker_Pipeline_DSL_Updater'
        }
        always {
            cleanWs()
        }
    }
}
