import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Check_Certificate_Expiry_Date'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job is used to check when SSL certs will expire on a bi-weekly basis.",
        "cicd_files/dsl/pipeline_jobs/CheckCertificateExpiryDate.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/CheckCertificateExpiryDate.Jenkinsfile"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
    }
    triggers {
        cron('H 8 1,15 * *')
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
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/CheckCertificateExpiryDate.Jenkinsfile')
        }
    }
    quietPeriod(5)
    disabled(false)
}
