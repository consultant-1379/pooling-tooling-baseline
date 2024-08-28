// Note: This job is disabled

import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = 'resource-pooling-tool-baseline_Elasticsearch_Curator'

pipelineJob(pipelineBeingGeneratedName) {
    disabled()
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "This ${pipelineBeingGeneratedName} job is used to automatically delete indices in Elasticsearch which are older than 30 days.",
        "cicd_files/dsl/pipeline_jobs/ElasticsearchCurator.groovy",
        "cicd_files/jenkins/files/pipeline_jobs/ElasticsearchCurator.Jenkinsfile",
        "cicd_files/jenkins/rulesets/ElasticsearchCurator.yaml"))
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
    }
    // To avoid the job from triggering (in case its enabled)
    // triggers {
    //     cron('H 0 * * *')
    // }
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
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/ElasticsearchCurator.Jenkinsfile')
        }
    }
    quietPeriod(5)
}