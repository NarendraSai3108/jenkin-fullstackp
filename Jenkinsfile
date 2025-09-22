pipeline {
    agent any

    stages {

        // ===== FRONTEND BUILD =====
        stage('Build Frontend') {
            steps {
                dir('Reactjenkins') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        // ===== FRONTEND DEPLOY =====
        stage('Deploy Frontend to Tomcat') {
    steps {
        bat """
        if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\reactbookapi" (
            rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\reactbookapi"
        )
        mkdir "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\reactbookapi"
        xcopy /E /I /Y Reactjenkins\\dist\\* "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\reactbookapi"
        """
    }
}

                '''
            }
        }

        // ===== BACKEND BUILD =====
        stage('Build Backend') {
            steps {
                dir('BOOK-SPRINGBOOT') {
                    bat 'mvn clean package'
                }
            }
        }

        // ===== BACKEND DEPLOY =====
        stage('Deploy Backend to Tomcat') {
            steps {
                bat '''
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\bookapi.war" (
                 del /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\bookapi.war"
                 )
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\bookapi" (
                 rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\bookapi"
                 )
                copy "BOOK-SPRINGBOOT\\target\\bookapi.war" "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\bookapi.war"
                '''
            }
        }

    }

    post {
        success {
            echo 'Deployment Successful!'
        }
        failure {
            echo 'Pipeline Failed.'
        }
    }
}
