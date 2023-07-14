#!groovy
@Library ('workflowlibs_devops_ar@tags/3.0') _
run(){
    architecture = 'spring'
    pipeline = {
        group = 'publish_vdc'
        revision = '1.9.0'
        vars = [
			execute: 'true',
			artifactory_repo_deploy: 'ar-arnd-rendiciones-maven',
			vtrack_namespace: 'ar.arnd.app-id-1681329.dsg'
		]
        
    }
    email = { object ->
        from='noreply@bbva.com'
        to='martina.pereyra@bbva.com'
    }
    
}
