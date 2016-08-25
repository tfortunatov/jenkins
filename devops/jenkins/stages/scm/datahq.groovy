def getSCMInfo(jobName, jobBuildNumber) {
  node('master') {
    def branchFile = "/tmp/${jobName}_${jobBuildNumber}_business_branch"
    def branchFileHandler = new File(branchFile)
    this.branch = branchFileHandler.text
  }
}

return this;
