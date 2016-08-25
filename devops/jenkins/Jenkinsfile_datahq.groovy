// By design the pipeline can only keep records of Serializable objects
// If you still need to keep an intermediate variable with a non serializable object, you need to hide it into a method and annotate this method with @NonCPS
// See https://cloudbees.zendesk.com/hc/en-us/articles/204972960-The-pipeline-even-if-successful-ends-with-java-io-NotSerializableException
@NonCPS
def getLastBuildCause() {
  def causes = currentBuild.rawBuild.getCauses()
  return causes.last().toString()
}

node('master') {
  buildCause = getLastBuildCause()
  echo buildCause
  def startedFromAnotherJob = false
  if ( buildCause =~ "job" ) {
    startedFromAnotherJob = true
    jobMatches = buildCause =~ /(job)\/([a-zA-Z0-9-_]+)\/([0-9]+)/
    upstreamJobName = jobMatches[0][2]
    upstreamJobBuildNumber = jobMatches[0][3]
    echo upstreamJobName
    echo upstreamJobBuildNumber
    // The matcher local variable is of a type (Matcher) not considered serializable by Java
    // therefore it is necessary to discard it before doing anything else
    // See https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md
    jobMatches = null
  }
  checkout([$class: 'GitSCM', branches: [[name: "*/master"]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanBeforeCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "80116385-8f9c-4d73-875b-95510e3ee8e9", url: 'https://github.com/tfortunatov/jenkins.git']]])

  def projectRoot = pwd()

  // The ${JOB_NAME}@script is a workspace created by Jenkins when it downloads the Jenkinsfile.
  // We are forced to load classes from this workspace, because at this point the actual job workspace
  // might not exist
  def vars = load "${projectRoot}/devops/jenkins/variables/vars.groovy"
//  vars.getCloudPlatform()
//  vars.getBusinessServicesWorkspace(projectRoot)
  vars.getBranches()
  vars.getEnvironments()
//  vars.getBusinessServices()
//  vars.getToolsLocations()
//  vars.getToolsOptions(projectRoot)
//  vars.getStorageOptions()

  stage 'Input parameters'
  def branch = ""
  def environment = ""
  def services = []
  if ( startedFromAnotherJob ) {
    def scm = load "${projectRoot}/devops/jenkins/stages/scm/datahq.groovy"
    scm.getSCMInfo(upstreamJobName, upstreamJobBuildNumber)

    branch = scm.branch
    environment = "staging"
    services = scm.services
  } else {
    def userInput = load "${projectRoot}/devops/jenkins/stages/input/datahq.groovy"
    userInput.request(vars)

    branch = userInput.branch
    environment = userInput.environment
    services = userInput.services
  }

  // Set pipeline build name
  currentBuild.displayName = "${currentBuild.number}-${branch}"

//  currentBuild.description = """Services: ${services.join(', ')}
Branch: ${branch}
Environment: ${environment} """

 // vars.getBusinessServicesWorkspace(projectRoot)

  stage "Checkout from repository"
  checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanBeforeCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "80116385-8f9c-4d73-875b-95510e3ee8e9", url: 'https://github.com/tfortunatov/jenkins.git']]])

/*  stage 'Build application'
  def sources = load "devops/jenkins/stages/build/business-service.groovy"
  sources.build(vars, services)

  stage 'Check code with sonar'
  def code = load "devops/jenkins/stages/code-quality/business-service.groovy"
  code.check(vars, services)

  stage 'Build docker image'
  def docker = load "devops/jenkins/stages/build-image/business-service.groovy"
  docker.buildImage(vars, services)

  stage 'Push to registry'
  def registry = load "devops/jenkins/stages/upload-image/business-service.groovy"
  registry.push(vars, services)

  stage 'Update database'
  def database = load "devops/jenkins/stages/update-database/${vars.platform}/business-service.groovy"
  database.update(environment, services)

  stage 'Deploy service'
  def service = load "devops/jenkins/stages/deploy-service/${vars.platform}/business-service.groovy"
  service.deploy(environment, services)

  stage 'Run tests'
  input message: 'Run integration tests?', ok: 'Run'
  def tests = load "devops/jenkins/stages/run-tests/integration.groovy"
  tests.run(vars, services, projectRoot)
*/
}

