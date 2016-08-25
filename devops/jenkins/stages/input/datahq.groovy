/**
 * Requests user input
 * @param   vars        object with all pipeline variables
 * @return  user-entered parameters
 */
def request(vars) {
  def userInput = input(
    id: 'userInput', message: 'Choose parameters', ok: 'Submit', parameters: [
      [$class: 'ChoiceParameterDefinition', choices: "${branches}", description: 'Branch to build from', name: 'BRANCH'],
      [$class: 'ChoiceParameterDefinition', choices: "${environments}", description: 'Environment to deploy to', name: 'ENVIRONMENT'],
    ]
  )

  this.branch = userInput['BRANCH']
  this.environment = userInput['ENVIRONMENT']
}

return this;
