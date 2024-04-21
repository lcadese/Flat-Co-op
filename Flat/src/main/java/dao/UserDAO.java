package dao;

import domain.User;

public interface UserDAO {
    User getUserById(String id);
    User getUserByUsername(String userName);
    void addUser(User user);
    void removeUser(User userid);

    boolean checkCredentials(String username,String password);
}
