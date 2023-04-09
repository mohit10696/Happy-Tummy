![alt HappyTummyLogo](./HappyTummyFrontend/src/assets/images/logo/logo.png) 

🔗 Check out our project live here : https://csci5308vm6.research.cs.dal.ca/pages/dashboard

NOTE : If you are not connected to Dal-WiFi, connect to DalVPN before accessing the application.

## About
Happy Tummy is a user-friendly web application designed to help individuals with minimal cooking experience, as well as those with busy lifestyles, to easily prepare delicious meals. The application offers a wide range of features that not only make cooking enjoyable but also help users discover new recipes, share their cooking experiences, and connect with like-minded food enthusiasts.


## Running in Localhost

### Software Requirements
- [Node.js](https://nodejs.org/en/)
- [MVN](https://maven.apache.org/download.cgi)
- [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Angular CLI](https://cli.angular.io/)


#### Clone the repository
```bash
git clone https://git.cs.dal.ca/courses/2023-winter/csci-5308/group06.git
```

### To Run Backend

Change directory to `happy-tummy-backend` and run the following commands:

    mvn clean install
    mvn spring-boot:run

### To Run Frontend

Change directory to `HappyTummyFrontend` and run the following commands:

    npm install
    ng serve

Open [http://localhost:4200](http://localhost:4200) to view it in the browser.

### Running in Server

### Software Requirements
- [Node.js](https://nodejs.org/en/)
- [MVN](https://maven.apache.org/download.cgi)
- [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Angular CLI](https://cli.angular.io/)
- [Jenkins](https://jenkins.io/download/)
- [Nginx](https://www.nginx.com/resources/wiki/start/topics/tutorials/install/)
- [PM2](https://pm2.keymetrics.io/docs/usage/quick-start/)


### Setup pipelines in Jenkins


#### To Setup Backend Pipeline

- Create a new item in Jenkins and select `Pipeline` and click `OK`.
- In the `Pipeline` section, select `Pipeline script from SCM` and select `Git`.
- In the `Repository URL` field, enter the URL of the repository.
- In the `Branches to build` field, enter `*/main`.
- In the `Script Path` field, enter `happy-tummy-backend/Jenkinsfile`.
- Click `Save`.

#### To Setup Frontend Pipeline

- Create a new item in Jenkins and select `Pipeline` and click `OK`.
- In the `Pipeline` section, select `Pipeline script from SCM` and select `Git`.
- In the `Repository URL` field, enter the URL of the repository.
- In the `Branches to build` field, enter `*/main`.
- In the `Script Path` field, enter `HappyTummyFrontend/Jenkinsfile`.
- Click `Save`.

### Setup Nginx

- Install Nginx.
  - Edit the `default` file in `/etc/nginx/sites-available` and add the following lines:

          server {
          listen 80 default_server;
      
          server_name _;
      
          return 301 https://$host$request_uri;
          }
  
            server {
                listen 443 ssl default_server;
                listen [::]:443 ssl default_server;
                server_name csci5308vm6.research.cs.dal.ca;
  
            ssl_certificate /home/csci5308vm6/csci5308vm6.research.cs.dal.ca.crt;
            ssl_certificate_key /home/csci5308vm6/csci5308vm6.research.cs.dal.ca.key;
  
  
            root /var/www/html;
  
  
            index index.html;
  
            server_name _;
  
            location /staging {
                    alias /var/www/staging;
                    index index.html;
                    try_files $uri $uri/ /index.html?$args;
            }
  
  
            location /apis {
                    rewrite /apis/(.*) /$1  break;
                    proxy_pass http://localhost:8081;
                    #proxy_http_version 1.1;
            }
            location /static {
                    alias /static;
            }
  
  
            location / {
                    root /var/www/html;
                    index index.html;
                    #alias /var/www/html;
                    #index index.html _html.html;
                    # First attempt to serve request as file, then
                    # as directory, then fall back to displaying a 404.
                    try_files $uri $uri/ /index.html?$args;
            }
  
          }

- Restart Nginx.
  - `sudo systemctl restart nginx`

Note that the `csci5308vm6.research.cs.dal.ca` is the domain name of the server.

Note that the `csci5308vm6.research.cs.dal.ca.crt` and `csci5308vm6.research.cs.dal.ca.key` are the SSL certificates of the server.


### Installation Guide for required software on Ubuntu 18.04

- Install Node.js.
  - `curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -`
  - `sudo apt-get install -y nodejs`
- Check the version of Node.js.
  - `node -v`
- Check the version of npm.
  - `npm -v`
- Install Angular CLI.
  - `npm install -g @angular/cli`
- Check the version of Angular CLI.
  - `ng --version`
- Install PM2.
  - `npm install pm2 -g`
- Check the version of PM2.
  - `pm2 -v`
- Install Nginx.
  - `sudo apt-get install nginx`
- Check the version of Nginx.
  - `nginx -v`
- Install Jenkins.
  - `wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -`
  - `sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'`
  - `sudo apt-get update`
  - `sudo apt-get install jenkins`
- Check the version of Jenkins.
  - `java -jar /var/cache/jenkins/war/WEB-INF/jenkins-cli.jar -s http://localhost:8080/ version`
- Install Maven.
  - `sudo apt-get install maven`
- Check the version of Maven.
  - `mvn -v`
- Install Java 8.
  - `sudo apt-get install openjdk-8-jdk`
- Check the version of Java 8.
  - `java -version`
- Install Git.
  - `sudo apt-get install git`
- Check the version of Git.
  - `git --version`





## Documentation
The [Documentation](./Documentation/) of the project consists of the User Stories, Usage scenarios, Excel files for all refactored code smells, Final Presentation, Contribution sheet, Dev & Client team feedback. 

## Dependencies

## Features

#### Login To An Existing Account 🔑

![ LOGIN  GIF](./)

#### Create An Account 🔐

![ SIGN-UP  GIF](./)

