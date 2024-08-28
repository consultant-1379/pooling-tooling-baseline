package common_classes

class ExternalParameters {

    static List functionalUserSecret(String defaultValue='SELI_ARTIFACTORY') {
        return ['FUNCTIONAL_USER_SECRET', defaultValue, 'User secret to be used when authenticating request to the artifactory']
    }

}
