# NTU SCSE SC2002 OOP Assignment
This is the main repository for the OOP Assignment

## How to collaborate
To begin collaborating, git clone the repository onto your device.
Go to your desired repository in command line and type in this:
```
    git clone https://github.com/dysfunkt/SC2002_Assignment_CAMs.git
    cd SC2002_Assignment_CAMs
```
Please import this as a **GRADLE PROJECT** to prevent unnecessary stuff from being added to Git.  
### Gradle
To compile and build app:  
- Windows
```
    gradlew.bat build
```
- MacOs
```
    gradlew build    
```
To run app:
```
    java -jar app\build\libs\app.jar
```
### Git
Main branch will be locked from being pushed. All changes are to be made in your own branch and merged via pull requests.  
To create a new branch:
```
    git checkout -b <branch-name>
```
To save your work to your branch locally:
```
    git add <file-name OR folder-name>
    OR
    git add . (to commit whole directory)
    THEN
    git commit -m "COMMENT TO DESCRIBE THE INTENTION OF THE COMMIT"
```
After committing locally, to push to remote repository:
```
    git push origin <branch-name>
```
### Merging and pull requests
To merge to main branch, first push your branch. Then create a pull request by clicking "Compare & pull request" and contact me after.

## References
- [Git Tutorial For Dummies (video)](https://www.youtube.com/watch?v=mJ-qvsxPHpY)
- [The Ultimate Github Collaboration Guide (article)](https://medium.com/@jonathanmines/the-ultimate-github-collaboration-guide-df816e98fb67)