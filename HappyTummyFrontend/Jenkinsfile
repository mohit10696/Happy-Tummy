pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }
    agent any
    environment {
        HOME = '.'
    }
    stages {
        stage('Build')
        {
            steps
            {
                script {
                    echo "${env.GIT_BRANCH}"
                    sh 'printenv'
                    dir('HappyTummyFrontend') {
                        sh 'npm cache clean --force'
                        sh 'npm install'
                        sh 'npm install @angular/cli'
                        if ("${env.GIT_BRANCH}" == 'origin/main') {
                            sh 'npm run build'
                        }
                        else if ("${env.GIT_BRANCH}" == 'origin/staging') {
                            sh "npm run build-stage"
                        }
                    }
                }
            }
        }
        stage('Deploy')
        {
            steps
            {
                script {
                    dir('HappyTummyFrontend') {
                        if ("${env.GIT_BRANCH}" == 'origin/main') {
                            echo 'Deleting the old build.  '
                            sh 'rm -r /var/www/html || ls'
                            echo 'Old build deleted, Deploying new build'
                            sh 'cp -a dist/happy-tummy-frontend/. /var/www/html'
                            echo 'Build Deployed. '
                        }else if ("${env.GIT_BRANCH}" == 'origin/staging') {
                            echo 'Deleting the old build.  '
                            sh 'rm -r /var/www/html/staging || ls'
                            echo 'Old build deleted, Deploying new build'
                            sh 'cp -a dist/happy-tummy-frontend/. /var/www/staging'
                            echo 'Build Deployed. '
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
