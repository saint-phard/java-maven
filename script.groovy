def buildMaven() {
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

def deploy() {
  echo 'deploying the application...'
}

return this
