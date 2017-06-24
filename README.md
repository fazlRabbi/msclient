Application for multiple sclerosis
==============

## How to build and run project locally
You need an IDE, Tomcat and Maven installed.

Open the project in your favorite IDE (IntelliJ is recommended).

Run Maven command "install" to update and generate the .war file.

Setup the Tomcat server and run the application as a Tomcat application.

The MS-application should now be available on localhost:8080.

## MediCloud
The settings for running the application is found in the "manifest.yml" file. Here you can specify the path for the .war file, memory, instances and the URL for the application.

You need an IBM Bluemix user and the correct rights to access the MediCloud. 

To update the application on MediCloud you need to install CLI (https://console.bluemix.net/docs/starters/install_cli.html). 
After this is installed you need to use a terminal to navigate to the project folder where "manifest.yml" is located.

Run "cf push" to update the application on MediCloud. "cf push" will use your "manifest.yml" file to update the project. 

## Other cloud solutions
Since the application is a Tomcat application you can deploy the application on other cloud solutions aswell. Follow the instructions given by the cloud solution vendor that you want to use. 
