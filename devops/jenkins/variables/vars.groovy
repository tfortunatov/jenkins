/**
 * Defines pipeline branches
 * @return  branches for the dropdown list
*/ 
def getBranches() {
  def branchList = ['master', 'devops']
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

this.credentialsId = "80116385-8f9c-4d73-875b-95510e3ee8e9"
}

return this;
