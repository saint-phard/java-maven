def buildJar() {
  echo "building the maven application..."
  sh 'mvn package'
}

def buildImage() {
  echo "building the docker image..."
  withCredentials([usernamePassword(credentialsId: 'dockerhub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
  sh 'docker build -t phard/my-repo:jma2.0 .'
  sh "echo $PASS | docker login -u $USER --password-stdin"
  sh 'docker push phard/my-repo:jma2.0'
  }
}

def deploy() {
  echo 'deploying the application...'
  def dockerCmd = 'docker run -p 3000:80 -d phard/my-repo:my-app-1.0'
  sshagent(['ec2-jenkins-server-key']) {
    sh "ssh -o StrictHostKeyChecking=no ec2-user@18.223.235.129 ${dockerCmd}"
  }
  
  
}

return this
