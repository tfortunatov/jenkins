/**
 * Builds application from source using maven
 * @param   vars        object with all pipeline variables
 * @param   services    applications to work with
 * @see     populateTasks
 */
def build(vars) {
  sh "source devops/jenkins/scripts/setenv.sh"
  sh "echo $LIBPATH"
