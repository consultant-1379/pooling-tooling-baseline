import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Build_And_Publish'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job packages the RPT helm chart. It then publishes the package to the armdocker JFrog artifactory.",
        "cicd_files/dsl/pipeline_jobs/BuildAndPublish.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/BuildAndPublish.Jenkinsfile",
        "cicd_files/jenkins/rulesets/BuildAndPublish.yaml"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
    }
    blockOn('''resource-pooling-tool-baseline_Pre_Code_Review
resource-pooling-tool-baseline_Staging
resource-pooling-tool-baseline_Upgrade
resource-pooling-tool-baseline_Pipeline_Updater''', {
        blockLevel('GLOBAL')
        scanQueueFor('DISABLED')
    })
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
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/BuildAndPublish.Jenkinsfile')
        }
    }

    quietPeriod(5)
    disabled(false)
}
