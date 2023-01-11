#!groovy
@Library ('workflowlibs_devops_ar@tags/2.0') _
run(){
    architecture = 'spring'
    pipeline = {
        group = 'publish_vdc'
        revision = '1.9.0'
        vars = [
            execute: 'true',
            artifactory_repo_deploy: 'ar-arnd-rendiciones-maven',
            artifactory_repo_releases: 'ar-arnd-rendiciones-maven',
            artifactory_repo_snapshots: 'ar-arnd-rendiciones-maven-dev',
            path_target: 'target/',
            vtrack_namespace: 'ar.arnd.app-id-1681329.dsg',
        ]
    }
    email = { object ->
        from='noreply@bbva.com'
        to='rodrigo.baulan@bbva.com,pablosebastian.lucero@bbva.com'
    }
    
}
