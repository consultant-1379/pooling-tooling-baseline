import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Pre_Code_Review'

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job conducts the precode review tests for RPTs Helm Chart.",
        "cicd_files/dsl/pipeline_jobs/PreCodeReview.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/PreCodeReview.Jenkinsfile"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
    }
    blockOn('''resource-pooling-tool-baseline_Build_And_Publish
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
                        refspec('\${GERRIT_REFSPEC}')
                    }
                    branch('\${GERRIT_PATCHSET_REVISION}')
                    extensions {
                        choosingStrategy {
                            gerritTrigger()
                        }
                        cleanBeforeCheckout()
                    }
                }
            }
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/PreCodeReview.Jenkinsfile')
        }
    }

    triggers {
        gerrit {
            project(commonParams.repo(), 'master')
            events {
                patchsetCreated()
            }
        }
    }

    quietPeriod(5)
    disabled(false)
}
