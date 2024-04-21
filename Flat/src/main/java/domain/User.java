package domain;

/**
 *
 * @author haydenaish
 */
public class User {
    private String userID;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String flatID;
    private String password;

    public User() {
    }

    public User(String userID, String username, String password, String firstName, String lastName, String email, String flatID) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.flatID = flatID;
        this.password = password;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUserID() {return userID;}

    public void setUserID(String userID) {this.userID = userID;}

    public String getFlatID() {return flatID;}

    public void setFlatID(String flatID) {this.flatID = flatID;}

    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
