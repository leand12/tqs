pipeline {
  agent any
  stages {
    stage('test java installation') {
      steps {
        sh 'java -version'
      }
    }

    stage('test maven installation') {
      steps {
        sh 'mvn -version'
      }
    }

    stage('') {
      steps {
        dir(path: './lab4/gs-employee-mngr') {
          sh 'mvn clean install'
        }

      }
    }

  }
}