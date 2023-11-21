// 所有的脚本命令都放在当前pipeline中
pipeline {
    // 指定任务的构建在哪个Jenkens集群节点中执行
    agent any
    tools {
        jdk 'jdk17'
    }
    // 指定环境变量
    environment {
        ENV = 'development'
        harborUser = 'admin'
        harborPasswd = 'Harbor12345'
        harborUrl = '192.168.0.201:80'
        harborRepo = 'internal'
    }

    stages {
        stage('Pull') {
            steps {
                // 执行shell命令
                echo 'start to pull'
                checkout scmGit(branches: [[name: '${tag}']], extensions: [], userRemoteConfigs: [[url: 'http://192.168.0.200:8929/root/web01.git']])
            }
        }
        stage('Test') {
            steps {
                // 执行shell命令
                echo 'start to test'
                sh '/share/maven/bin/mvn test'
            }
        }
        stage('Build') {
            steps {
                // 执行shell命令
                echo 'start to build via maven'
                sh '/share/maven/bin/mvn clean package -DskipTests'
            }
        }
        stage('SonarQube') {
            steps {
                // 执行shell命令
                sh '/share/maven/bin/mvn sonar:sonar'
            }
        }
        stage('image Build') {
            steps {
                // 执行shell命令
                sh '''mv ./target/*.jar ./docker/
                docker build -t ${JOB_NAME}:${tag} ./docker/'''
            }
        }
        stage('Push') {
            steps {
                // 执行shell命令
                echo 'Push image to Harbor'
                sh '''docker login -u ${harborUser} -p ${harborPasswd} ${harborUrl}
                docker tag ${JOB_NAME}:${tag} ${harborUrl}/${harborRepo}/${JOB_NAME}:${tag}
                docker push ${harborUrl}/${harborRepo}/${JOB_NAME}:${tag}
                '''
            }
        }
        stage('Publish over SSH') {
            steps {
                // 执行shell命令
                echo 'start to publish over ssh'
                sshPublisher(publishers: [sshPublisherDesc(configName: 'test', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "deploy.sh $harborUrl $harborRepo $JOB_NAME $tag $port", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
    }
}
