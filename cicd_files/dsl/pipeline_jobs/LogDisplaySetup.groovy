import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()

def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Log_Display_Setup'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This job deploys ElasticSearch and Kibana",
        "cicd_files/dsl/pipeline_jobs/LogDisplaySetup.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/LogDisplaySetup.Jenkinsfile"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        name('gcn')
                        url(commonParams.repoUrl())
                    }
                    branch('master')
                }
            }
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/LogDisplaySetup.Jenkinsfile')
        }
    }
    quietPeriod(5)
    disabled(false)
}
