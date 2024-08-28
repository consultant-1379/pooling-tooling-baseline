import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Upgrade'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job deploys the latest RPT changes onto a production environment.",
        "cicd_files/dsl/pipeline_jobs/Upgrade.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/Upgrade.Jenkinsfile",
        "cicd_files/jenkins/rulesets/Upgrade.yaml"))
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
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/Upgrade.Jenkinsfile')
        }
    }
    quietPeriod(5)
    disabled(false)
}
