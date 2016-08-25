/**
 * Defines pipeline branches
 * @return  branches for the dropdown list
 */
def getBranches() {
def gitURL = "http://ssnitsarenko@crdqn1vdcrdwb35.infosolco.com:8082/scm/bits/datahq_data.git"
def command = "git ls-remote -h "+ gitURL
def proc = command.execute()
proc.waitFor()

if ( proc.exitValue() != 0 ) {
    return proc.err.text
}

def branchList = proc.in.text.readLines().collect {
    it.replaceAll(/[a-z0-9]*\trefs\/heads\//, '')
}

this.branches = branchList.join("\n")

}
/**
 * Defines pipeline environments
 * @return  environments for the dropdown list
 */
def getEnvironments() {
  def environmentList = ['staging', 'production']
  this.environments = environmentList.join("\n")
}

/**
 * Defines storage options
 * @return  storage options
 */
def getStorageOptions() {

this.credentialsId = 80116385-8f9c-4d73-875b-95510e3ee8e9
}

return this;
