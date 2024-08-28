import common_classes.CommonSteps
import common_classes.CommonParameters
import common_classes.ExternalParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()
ExternalParameters externalParams = new ExternalParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Auto_Backup_Retention_Policy_Enforcer'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job is used to enforce the retention policy for automated backups for RPT.",
        "cicd_files/dsl/pipeline_jobs/AutoBackupRetentionPolicyEnforcer.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/AutoBackupRetentionPolicyEnforcer.Jenkinsfile",
        "cicd_files/jenkins/rulesets/AutoBackupRetentionPolicyEnforcer.yaml"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
        stringParam(externalParams.functionalUserSecret())
    }
    triggers {
        cron('H 3 * * *')
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
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/AutoBackupRetentionPolicyEnforcer.Jenkinsfile')
        }
    }
    quietPeriod(5)
    disabled(false)
}
