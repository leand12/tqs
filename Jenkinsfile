pipeline {
  agent {
    docker {
      image 'maven:3.8.1-adoptopenjdk-11'
      args '-v /root/.m2:/root/.m2'
    }

  }
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

    stage('error') {
      steps {
        dir(path: './lab4/gs-employee-mngr') {
          sh 'mvn clean install'
        }

      }
    }

  }
}