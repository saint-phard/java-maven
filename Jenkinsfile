def gv

pipeline {
  
  agent any
  parameters {
    choice(name: 'VERSION', choices: ['1.1.0','1.2.0','1.3.0'], description: '')
    booleanParam(name: 'executeTests', defaultValue: true, description: '')
  }
  
  stages {
    
    stage("init") {
       steps {
         script {
           gv = load "script.groovy"
         }
      }
    }
      
      stage("build") {
        steps {
          script {
          gv.buildApp()
          }
        }
      }
    
    stage("test") {
      when {
        expression {
         params.executeTests == true
        }
      }
      
      steps {
        script {
          gv.testApp()
          }
      }
    }
    
    stage("deploy") {
      input {
        message "choose your prefered enviroment"
        ok "Acknowledged"
        parameters {
          choice(name: 'first', choices: ['dev','staging','prod'], description: 'environment')
          choice(name: 'second', choices: ['dev','staging','prod'], description: 'environment')
        }
      }
      steps {
        script {
          gv.deployApp()
          echo "Deploying to ${first}"
          echo "Deploying to ${second}"
          }
      }
    }
    
  }
  
}
