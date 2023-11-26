# CAMs
NTU AY2023/24 Semester 1 SC2002 Group Project - Camp Application and Management System (CAMs).  
Camp Application and Management System (CAMs) is a Java console application designed for staff and students to manage, view and register for camps within NTU. The application acts as a centralised hub for all staff and students.  
CAMs was designed with the aim of providing extensibility, maintainability and reusability, through the use of various SOLID and Object-Oriented Programming principles, to ensure our software design is efficient and understandable. We also aim to make the application as user-friendly as possible. 

## Repository Link
[Github Repository](https://github.com/dysfunkt/SC2002_Assignment_CAMs)

## Team Members
We are group 5 from tutorial group SCMC, Nanyang Technological University, Singapore. There are 5 members in our group: 
| Name                 | Email                 |
|----------------------|-----------------------|
| Neo Zhi Xuan         | ZNEO011@e.ntu.edu.sg  |
| Samuel Tan           | C220193@e.ntu.edu.sg  |
| Seow Ming Han Samuel | MSEOW001@e.ntu.edu.sg |
| Tan Jun Yan Xavier   | XTAN102@e.ntu.edu.sg  |
| Willy Tang Jing Lin  | WTANG015@e.ntu.edu.sg |

## Documentation
The report can be found in `Deliverables\report`
UML diagrams can be found in `Deliverables\UML`
The Javadoc can be found at `app\build\docs\javadoc\index.html`.  
The GAI declaration forms can be found in `Deliverables\GAIDeclaration`

## Features
- [x] User
  - [x] Login
  - [x] Change password
- [x] Student
  - [x] View all camps
  - [x] Register for camp
  - [x] View joined camps
  - [x] Submit enquiry
  - [x] View enquiries
  - [x] Edit enquiry
  - [x] Delete enquiry
  - [x] Leave camp
- [x] Camp Committee (also a student)
  - [x] View camp details
  - [x] View enquiries to camp
  - [x] Reply enquiry
  - [x] Submit suggestion
  - [x] View my suggestions
  - [x] Edit suggestion
  - [x] Delete suggestion
  - [x] Generate report
- [x] Staff 
  - [x] Create camp
  - [x] Edit camp
  - [x] Delete camp
  - [x] View all camps
  - [x] View my camps
  - [x] View enquiries
  - [x] Reply enquiry
  - [x] View suggestions
  - [x] Approve suggestions
  - [x] Generate report

## Build
Clone the repository.
```
    git clone https://github.com/dysfunkt/SC2002_Assignment_CAMs.git
    cd SC2002_Assignment_CAMs
```
This is a Gradle project.
To compile and build app:  
- Windows
```
    gradlew.bat build
```
- MacOs
```
    gradlew build    
```
The project is built with Java 21.

## Run
The built jar file is at `app\build\libs\app.jar`.  
To run app:  
There is a shell script `run.sh` and a Windows command script `run.cmd` to run the program.  
Or you can run the jar file with the following command:  
```
    java -jar app\build\libs\app.jar
```
The main class is at `app\src\main\java\cams\MainApp.java`.  
Source code can be found in `app\src\main\java\cams`.  
Data files are found in `data`.  
Output folder for camp reports is `data\reports`.  
  
To reset data to state before running test case, copy all the csv files in the `data\reinit\Test Cases` folder and paste it to the root data folder.
Replace all files when prompted to.
