def getSCMInfo(jobName, jobBuildNumber) {
  node('master') {
    this.branch = "master"
  }
}

return this;
