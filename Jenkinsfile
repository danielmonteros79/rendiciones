#!groovy
@Library ('workflowlibs_devops_ar@feature/EAA1-1000') _
run(){
    architecture = 'spring'
    pipeline = {
        vars = [
            execute: 'true',
            artifactory_repo_releases: 'ar-arnd-rendiciones-maven-local',
            artifactory_repo_snapshots: 'ar-arnd-rendiciones-maven-dev-local',
            path_target: 'target/',
            vtrack_namespace: 'ar.arnd.app-id-1681329.dsg',
        ]
    }
    email = { object ->
        from='noreply@bbva.com'
        to='rodrigo.baulan@bbva.com'
    }
    
}
