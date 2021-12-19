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

  sshagent(['ec2-jenkins-server-key']) {
    sh 'scp docker-compose.yaml ec2-user@18.223.235.129:/home/ec2-user'
    sh 'ssh -o StrictHostKeyChecking=no ec2-user@18.223.235.129'
    sh 'ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose'
    sh 'docker-compose -f docker-compose.yaml up -d'
  }
 
  
}

return this
