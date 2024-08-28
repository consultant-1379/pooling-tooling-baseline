package common_classes

class CommonParameters {

    static List slave(String defaultValue='GridEngine') {
        return ['SLAVE', defaultValue, 'Slave to run job against.']
    }

    static String repo() {
        return 'OSS/com.ericsson.oss.ci/pooling-tooling-baseline'
    }

    static String repoUrl() {
        return '\${GERRIT_MIRROR}/'+repo()
    }
}
