package cams.object.person;

public class User {
  /*  
    Unique identifier (Id) for the User - either Staff or Student.
  */
    private String userId;
  /*
    Name of the User
  */
    private String userName; 
  /*
    Email of the User
  */
    private String userEmail;
  /*  
    Password of the User
  */
    private String password;
  /*
    Faculty of the User
  */
    private String faculty;
  
  /*
    Create a new User with given ID, password and faculty

    @param id        This User's unique ID.
    @param password  This User's password.
    @param faculty   This User's faculty.
  */
    public User(String id, String password, String faculty) {
        this.userId = id;
        this.password = password;
        this.faculty = faculty;
    }

  
// Read from CSV file or something???

  
  /*
    Gets the unique Id of this user.

    @return this User's Id.
  */
    public String getUserId() {
        return userId;
    }
  
  /*
    Gets the password of this user.

    @return this User's password.
  */
    public String getPassword() {
        return password;
    }
  
  /*
    Gets the faculty of this user.

    @return this User's faculty.
  */
    public String getFaculty() {
        return faculty;
    }

  /*
    Changes the password of this user.

    @set User's current password as new password.
  */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
