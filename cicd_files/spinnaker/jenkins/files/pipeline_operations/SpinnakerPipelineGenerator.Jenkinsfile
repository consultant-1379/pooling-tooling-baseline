def returnParametersForDsl() {
    return [SLAVE: env.SLAVE]
}

def getPipelineJobs() {
    def pipelineJobList = []

    pipelineJobList = pipelineJobList.plus('cicd_files/spinnaker/dsl/pipeline_jobs/SpinnakerPipelineJsonUpdater.groovy')
    pipelineJobList = pipelineJobList.plus('cicd_files/spinnaker/dsl/pipeline_operations/SpinnakerPipelineUpdater.groovy')

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

        stage('Generate RPT Spinnaker Pipeline Jobs') {
            steps {
                jobDsl targets: getPipelineJobs(),
                    additionalParameters: returnParametersForDsl(),
                    additionalClasspath: env.DSL_CLASSPATH
            }
        }

        stage ('Update RPT Baseline Nested View') {
            steps {
                jobDsl targets: 'cicd_files/dsl/views/View.groovy'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
