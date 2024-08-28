import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Staging'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job deploys the latest RPT changes onto a staging namespace on the olah023 cluster.",
        "cicd_files/dsl/pipeline_jobs/Staging.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/Staging.Jenkinsfile",
        "cicd_files/jenkins/rulesets/Staging.yaml"))
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
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/Staging.Jenkinsfile')
        }
    }
    quietPeriod(5)
    disabled(false)
}
