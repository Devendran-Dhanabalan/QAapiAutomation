**Test project with automated tests for Stocks API Automation:**

# Test project repository : https://github.com/dev/qa-automation.git

**#Software Stacks to automate application for both Windows and Mac:**
Intellij Jdk Maven Git Clone the project using above git url provided. Set the jdk for the application . Build the
project using Maven locally for dependency management

**#Set the environment variables**
Set MAVEN_HOME and JAVA_HOME environment variables according to OS availability

**#One-time configuration**
Clone the project using above git url provided. Set the jdk for the application . Build the project using Maven locally
for dependency management

**#Run Suite Options:**
Option 1:
Steps:

1. Go to file src/test/resources/com.qa/config/config.properties and set the TEST_ENVIRONMENT variable to uat/prod.
2. Go to file com.qa/config/prod.properties or uat.properties as per the environment selected and specify the
   required user details in it. 3. Right click on pom.xml or testng.xml and run the suite.

Option 2: Through CLI Steps:

1. Open the terminal in Intellij idea and run the below command:
   mvn clean test -Denv=uat/prod -Dmobilenumber=mobile -Dpassword=pwd -Dcuserid=cuserid eg:  mvn clean test -Denv=prod
   -Dmobilenumber=9999999999 -Dpassword=abcd11 -Dcuserid=111111

**#Test Report**
After test run completion, the report can be found under the target/ExtentReports/ folder. Application level logging can
be found under the project folder with file name 'application.log'