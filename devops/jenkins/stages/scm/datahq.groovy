def getSCMInfo(jobName, jobBuildNumber) {
  node('master') {
    this.services = servicesFileHandler.text.trim().tokenize(',')
    this.branch = branchFileHandler.text
  }
}

return this;
