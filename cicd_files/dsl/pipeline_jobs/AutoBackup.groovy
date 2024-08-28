import common_classes.CommonSteps
import common_classes.CommonParameters
import common_classes.ExternalParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()
ExternalParameters externalParams = new ExternalParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Auto_Backup'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job is used to automatically backup the database for RPT.",
        "cicd_files/dsl/pipeline_jobs/AutoBackup.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/AutoBackup.Jenkinsfile"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
        stringParam(externalParams.functionalUserSecret())
    }
    triggers {
        cron('H/30 * * * *')
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        name('gcn')
                        url(commonParams.repoUrl())
                    }
                    branch('*/master')
                }
            }
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/AutoBackup.Jenkinsfile')
        }
    }
    quietPeriod(5)
    disabled(false)
}
