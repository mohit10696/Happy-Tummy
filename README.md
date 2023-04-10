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
The [Documentation](./Documentation/) of the project consists of the User Stories, Usage scenarios, Excel files for all refactored code smells, Final Presentation, Contribution statement, Dev & Client team feedback.

## Dependencies

<!-- ## Features -->

#### User Roles
1.	User - Registered users who have created an account on Happy Tummy.
2.	Guest - Unregistered users who can access limited features without creating an account.

#### User Stories
- As a user or guest, I want to explore the home page, so I can discover top recipes with brief descriptions, likes, and reviews.
- As a user, I want to sign up or log in to Happy Tummy to access all features, like adding recipes, managing my profile, and connecting with other users.
- As a user, I want to create and manage my profile, so I can update my profile image, email, bio, name, list of liked recipes, followed users, followers, and added recipes.
- As a user, I want to add my own recipes, so I can share them with others and receive feedback in the form of likes, comments, and reviews.
- As a user or guest, I want to filter recipes by meal preference (veg, vegan, non-veg), so I can find suitable recipes based on my dietary requirements.
- As a user or guest, I want to view detailed recipe information, including ingredients, quantity, servings, preparation time, cooking time, difficulty level, nutritional information, and tips.
- As a user or guest, I want to access a YouTube video link for each recipe, so I can watch and follow the cooking process if I don't want to read the instructions.
- As a user or guest, I want to use the ingredient filter, search bar, or drag-and-drop ingredient selector to find recipes based on available ingredients, making it easier to discover new recipes that suit my needs.
- As a user, I want to receive recipe recommendations based on my selected ingredients, so I can try new recipes that fit my preferences and dietary needs.
- As a user, I want to like, rate, write reviews, and upload images of the recipes I've made, so I can share my experience and provide feedback to the recipe creator.
- As a user or guest, I want to find nearby grocery stores using the built-in maps feature, so I can easily purchase any missing ingredients needed for a recipe. 

By addressing these user stories, Happy Tummy becomes an engaging, comprehensive, and user-centric platform for novice cooks and busy individuals, providing a fun and interactive way to explore the world of cooking.

## Existing Features

#### 1. Home Page:
The home page displays top recipes with a brief description, the number of likes, and reviews. It is designed to help users and guests explore popular recipes easily, providing an engaging and visually appealing browsing experience. The home page also features a search bar, ingredient drag and drop selector and filtering options, allowing users to search for specific recipes or filter by meal preferences.
[https://www.youtube.com/watch?v=DqHVG6DfvuA](https://www.youtube.com/watch?v=DqHVG6DfvuA))

#### 2. Login/SignUp:
Happy Tummy provides an easy-to-use login and sign-up process for new and existing users. New users can create an account by providing their credentials, such as name, email, and password. Existing users can log in using their credentials to access their profiles, interact with other users, and utilize all the application's features.
[Login](https://www.youtube.com/watch?v=fojyFtzWiDA)
[Sign-up](https://www.youtube.com/watch?v=Ck8FJ8dgndI)

#### 3. User Profile:
Registered users have a personalized profile page where they can manage their profile information, including profile image, name, list of liked recipes, followed users, followers, and added recipes. Users can view and edit their bio and other details, making their profiles more personalized and engaging. The profile page also displays the user's "My Recipes" section, showcasing the recipes they have added to the platform.
[User profile](https://www.youtube.com/watch?v=0wD_K0IMW9Q)
[HappyTummyFrontend - Google Chrome 2023-04-09 19-01-30.mp4](..%2FDocumentation%2FHappyTummyFrontend%20-%20Google%20Chrome%202023-04-09%2019-01-30.mp4)

#### 4. Add Recipe:
Logged-in users can add their own recipes to Happy Tummy using the "Add Recipe" feature. They need to provide the recipe name, introduction, preparation time, cooking time, servings, recipe difficulty, tips (if any), ingredients, steps, nutritional information, and tags. Once added, the recipe will be displayed on their profile page and can be liked, reviewed, and commented on by other users.
[add recipe](https://www.youtube.com/watch?v=KZ9ZsKKsiXE)

#### 5. Recipe Recommendation:
Guests and users can receive recipe recommendations based on their selected ingredients or meal preferences (veg, vegan, non-veg). This feature helps users discover new recipes tailored to their preferences, making it easier to find suitable recipes for their dietary needs.
[recommendation](https://www.youtube.com/watch?v=S-C9Owz0s6c)

#### 6. Recipe View:
When users or guests click on a recipe, they can view detailed information about the recipe, including ingredients, quantity, servings, preparation time, cooking time, difficulty level, nutritional information, and tips. This comprehensive overview allows users to fully understand the recipe before attempting it.
[View recipe description](https://www.youtube.com/watch?v=poEAPFI6N_E)

#### 7. YouTube Video Link:
Each recipe in Happy Tummy includes a YouTube video link, allowing users and guests to watch and follow the cooking process if they prefer not to read the instructions. This feature adds an interactive and engaging element to the recipe learning experience.
[Watch on youtube](https://www.youtube.com/watch?v=DqHVG6DfvuA)

#### 8. Ingredient Filter, Search Bar, and Ingredient Selector:
These features enable users and guests to find recipes based on specific ingredients or combinations of ingredients. Users can type in the search bar, filter by meal preferences, or use the drag-and-drop ingredient selector to find recipes that match their selected ingredients.
[HappyTummyFrontend - Google Chrome 2023-04-09 18-39-03.mp4](..%2FDocumentation%2FHappyTummyFrontend%20-%20Google%20Chrome%202023-04-09%2018-39-03.mp4)
[filters, selector](https://www.youtube.com/watch?v=i9otJMRrPIA)

#### 9. Like, Rate, Review, and Upload Images:
Registered users can interact with recipes by liking, rating, writing reviews, and uploading images of their version of the recipe. This feature encourages engagement and feedback among users, helping recipe creators improve and refine their recipes.
[like, rate, review, upload picture](https://www.youtube.com/watch?v=o_1J8i5vdVw)

#### 10. Nearby Grocery Stores:
The application includes a built-in maps feature that helps users and guests find nearby grocery stores based on their current location. This feature makes it easy for users to purchase missing ingredients needed for a recipe, ensuring a seamless cooking experience.
[grocery store](https://www.youtube.com/watch?v=YsaEIEXq35k)

By providing these features, Happy Tummy offers a comprehensive and user-centric platform that caters to the needs of novice cooks and busy individuals, making cooking enjoyable and accessible for everyone.




