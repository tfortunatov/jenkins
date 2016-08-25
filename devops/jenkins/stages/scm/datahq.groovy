/**
 * Gets information about changed services and branch
 * @return  branch
 */
def getSCMInfo(jobName, jobBuildNumber) {
  node('master') {

    def branchFile = "/tmp/${jobName}_${jobBuildNumber}_datahq_branch"
    def branchFileHandler = new File(branchFile)
    this.branch = branchFileHandler.text

    sh "rm -f /tmp/${jobName}_${jobBuildNumber}_datahq_branch"
  }
}

return this;
