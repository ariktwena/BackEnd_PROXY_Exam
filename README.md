<h1 align="center">Welcome to 3 Semester Spring 2021 - Exam 👋</h1>

<h4 align="center">Exam for 3. Semester of Spring 2021 </h4>

[![Build Status](https://www.travis-ci.com/ariktwena/BackEnd_PROXY_Exam.svg?branch=master)](https://www.travis-ci.com/ariktwena/BackEnd_PROXY_Exam)

## Author

👤 **Sigurd Arik Gaarde Nielsen**

* [Github](https://github.com/ariktwena)
* [LinkedIn](https://www.linkedin.com/in/arik-gaarde-nielsen-3a54255/)

# Deployed project

#### 🖥 [FrontEnd](https://codeops.dk/)

#### 💾 [BackEnd](https://codergram.dk/startcode_exam/)

#### 🖥 [GitHub FrontEnd](https://github.com/ariktwena/Frontend_PROXY_exam)

#### 💾 [GitHub BackEnd](https://github.com/ariktwena/BackEnd_PROXY_Exam)

#### 👮🏽‍♂️ [Travis](https://www.travis-ci.com/github/ariktwena/BackEnd_PROXY_Exam)

## Using this project

1. Initial Setup (Do not open the project in your IDE for anything below)

2. Before you start verify that your local docker-environment is started and startcode_test is available
3. In a terminal (git bash on Windows) clone the project. CD into the project folder and delete the .git folder and Do "Your own" `git init`
4. In the project folder run this: `mvn clean test`  (make sure your docker environment is up)
5. When everything above is fine, create your OWN repository for the project on github. Commit and Push your code to this repo.
6. Go to Travis-ci.com and Sign up with GitHub
7. Accept the Authorization of Travis CI. You’ll be redirected to GitHub
Click the green Activate button, and select the new repository to be used with Travis CI. Now you are ready for the next steps :-)
8. Deploy the project (manually) via Maven
9. Before you start make sure you have all these details ready (make a small document with the values FIRST)

10. The user you have created on your droplet MySQL server, with GRANTS to all databases:
User:		_________
Password	_________

11. The user you have created to allow deployment on Tomcat 
(On your droplet look in tomcat/tomcat-users.xml if you have forgot):
User:		_________
Password	_________

12. The name to your server https://xxxxxx.dk (make sure you can access /manager/html)

13. Open your project in your IDE, since we have to make a few changes to the code and pom-file
14. Open the pom-file, and locate the properties-section at the start of the file. Change the value for remote.server to a URL pointing to YOUR droplet
On your droplet, either using workbench locally or via the SQL-client on the droplet, create a new database called startcode_demo.
1. ssh into your droplet, and navigate into the root of the cloned docker project
1. Stop your docker-servers: `docker-compose down`
1. With nano, open docker-compose.yml
1. Under the web: section find the lines given below and change USER, and PW to your values, and change CONNECTION_STR to point startcode_demo (If you have an existing Java-project, using a database on your droplet, follow the instructions given in DigitalOcean Setup instructions "Create another database and set environment variables to host additional Java Projects") 
 
1. Crate `CONNECTION_STR: "jdbc:mysql://db:3306/startcode"`
`USER: "dev"`
`PW: "ax2"` 
`DEPLOYED: "PROD"`

1. Save the file, rebuild and run using these commands:
`docker-compose down`
`docker-compose build`
`docker-compose up -d`
1. Back in a LOCAL terminal (git bash on Windows), in the root of the Java-project, type:
`mvn clean test -Dremote.user=USER -Dremote.password=PW tomcat7:deploy`
The values for user and password above, are those YOU have added in tomcat-users.xml during the initial setup of your droplet.

1. HOPEFULLY, those values are NOT admin and admin123, if they are, do the following:
`docker-compose down`
1. With nano, open `tomcat/tomcat-users.xml` Change username and password and save.
1. run `docker-compose build --no-cache`
1. Start the servers again: `docker-compose up -d`
1. If everything was fine the project was deployed to your droplet, ready to use with the remote database. Test like this:
`URL_OR_DomainName/devops-starter/api/xxx/count `

1. Let Travis deploy your project - if it builds and all tests are green
In this step we will let travis deploy our project if it builds and all tests are green :-)

1. Login to Travis using Github, and select your project on the dashboard
1. Click "More options" and select "settings"
1. Create two Environment Variables with names and values as sketched below (two steps)
`REMOTE_USER :  XXXXXX`
`REMOTE_PW :   XXXXXXX`
These are the values you added in step 10-11, when we deployed via maven by yourself. In this part,   since you did it yourself, secrets were not an issue. On Travis it is. So this ensures that no one else can see your credentials
1. Now make a small change to index.html (one that is easy to see after deploy)
1. In a terminal, in the root of the project, type: `mvn clean test` (always build and test before you commit). If everything was fine, commit and push your changes
1. On travis-ci.org verify that your commit has been detected and a "build cycle" has started
1. If everything was fine (might take a few minutes) verify that Travis has deployed your new war file to your droplet

## Show your support

Give a ⭐️ if this project helped you!

## Contributing

Feel free to fork this project and make your changes.

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
