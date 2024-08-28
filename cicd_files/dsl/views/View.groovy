sectionedView('Resource Pooling Tool Baseline CICD') {
    description("""<div style="padding:1em;border-radius:1em;text-align:center;background:#fbf6e1;box-shadow:0 0.1em 0.3em #525000">
        <b>Resource Pooling Tool Baseline</b><br>
       CICD Pipelines, Source Control Jobs as well as Staging jobs.<br><br>
        Team: <b>Thunderbee &#x26A1</b><br>
    </div>""")
    sections {
        listView() {
            name('RPT BaselineCICD Pipelines')
            description('RPT Baseline CICD pipeline jobs.')
            jobs {
                name('resource-pooling-tool-baseline_Pre_Code_Review')
                name('resource-pooling-tool-baseline_Build_And_Publish')
                name('resource-pooling-tool-baseline_Staging')
                name('resource-pooling-tool-baseline_Upgrade')
            }
            columns setViewColumns()
        }
        listView() {
            name('RPT Baseline CICD Pipeline Source Control')
            description('RPT Baseline CICD Pipelines and Source Control Jobs.')
            jobs {
                name('resource-pooling-tool-baseline_Pipeline_Generator')
                name('resource-pooling-tool-baseline_Pipeline_Updater')
            }
            columns setViewColumns()
        }
        listView() {
            name('RPT Baseline Spinnaker Pipeline Source Control')
            description('RPT Baseline Spinnaker Pipelines Source Control Jobs.')
            jobs {
                name('resource-pooling-tool-baseline_Spinnaker_Pipeline_Generator')
                name('resource-pooling-tool-baseline_Spinnaker_Pipeline_JSON_Updater')
                name('resource-pooling-tool-baseline_Spinnaker_Pipeline_DSL_Updater')
            }
            columns setViewColumns()
        }
        listView() {
            name('RPT Baseline Certificate Check')
            description('RPT Baseline Certificate Check Job.')
            jobs {
                name('resource-pooling-tool-baseline_Check_Certificate_Expiry_Date')
            }
            columns setViewColumns()
        }
        listView() {
            name('RPT Baseline Automated Backup')
            description('RPT Baseline Automated Backup Jobs.')
            jobs {
                name('resource-pooling-tool-baseline_Auto_Backup')
                name('resource-pooling-tool-baseline_Auto_Backup_Retention_Policy_Enforcer')
            }
            columns setViewColumns()
        }
        listView() {
            name('RPT Baseline Deprecated Jobs')
            description('RPT Baseline Deprecated CICD Pipelines & Source Control Jobs.')
            jobs {
                name('resource-pooling-tool-baseline_Elasticsearch_Curator')
                regex(/.*Thunderbee_Deprecated_resource-pooling-tool-baseline_.*/)
            }
            columns setViewColumns()
        }
    }
}

static Object setViewColumns() {
    return {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
