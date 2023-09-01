BE service is written in Springboot(maven)

Install Springboot and connect with VScode or Eclipse

(https://code.visualstudio.com/docs/java/java-spring-boot
https://tariqul-islam-rony.medium.com/learning-java-and-spring-boot-with-visual-studio-code-vscode-part-1-54073f2fa264
https://tariqul-islam-rony.medium.com/spring-boot-with-visual-studio-code-visual-studio-code-part-2-7943febb52f8)

Download the zip or clone the Git repository in VSCode or Eclipse.
Install the Spring boot Extentions Pack for getting Spring boot Development Environement and Intellisense to Visual Studio Code.
Navigate to the file DropboxServiceApplication.java (search for @SpringBootApplication)
Right Click on the file and Run as Java Application
You are all Set

<img width="1405" alt="Screenshot 2023-09-01 at 5 35 24 AM" src="https://github.com/sarthak-kumar-shailendra/dropbox-service/assets/69191344/54dba6b0-852c-405d-8df4-08aacdbac32f">

Meta data is stored in MongoDB Atlas
Paste Connection String in application.properties

you can use that to connect to db using Studio 3T

<img width="1420" alt="Screenshot 2023-09-01 at 5 07 28 AM" src="https://github.com/sarthak-kumar-shailendra/dropbox-service/assets/69191344/d3a8b257-11a5-430b-94b2-d98982abf862">

I am storing the files in the local storage (storage folder)
so you can see the files being created/deleted in the folder

Change the filePath value with the path of storage folder of your local repo. 
(right now it is /Users/sarthakkumar/Downloads/dropbox-service/src/storage/ )
