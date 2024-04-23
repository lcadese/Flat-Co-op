package dao;

import domain.User;

public interface UserDAO {
    User getUserById(String id);
    User getUserByUsername(String userName);
    void addUser(User user);
    void removeUser(String userid);
    void setFlat(String userID,String flatID);
    boolean checkCredentials(String username,String password);
}
