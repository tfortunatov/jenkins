def getBranches("git ls-remote -t -h https://github.com/tfortunatov/jenkins.git").execute()
this branches.text.readLines()
         .collect { it.split()[1].replaceAll('refs/heads/', '')  }
         .unique()
         .findAll { it.startsWith('<some more pattern>') }

/**
 * Defines pipeline branches
 * @return  branches for the dropdown list
/
@NonCPS
def getBranches() {
def gitURL = "https://github.com/tfortunatov/jenkins.git"
def command = "git ls-remote -h "+ gitURL
def proc = command.execute()
proc.waitFor()

if ( proc.exitValue() != 0 ) {
    return proc.err.text
}

def branchList = proc.in.text.readLines().collect {
    it.replaceAll(/[a-z0-9]*\trefs\/heads\//, '')
}
println branchList
//this.branches = branchList.join("\n")
//return branches = branchList.join("\n")

}

/**
 * Defines pipeline branches
 * @return  branches for the dropdown list
 
def getBranches() {
  def branchList = ['master', 'ci_with_slaves']
  this.branches = branchList.join("\n")
}
*/

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

this.credentialsId = "80116385-8f9c-4d73-875b-95510e3ee8e9"
}

return this;
