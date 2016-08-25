def getSCMInfo(jobName, jobBuildNumber) {
  node('master') {
    def branchFile = "/tmp/${jobName}_${jobBuildNumber}"
    def branchFileHandler = new File(branchFile)
    this.branch = branchFileHandler.text
  }
}

return this;
