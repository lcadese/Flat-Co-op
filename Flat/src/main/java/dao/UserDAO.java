package dao;

import domain.User;
import org.jdbi.v3.sqlobject.customizer.Bind;

public interface UserDAO {
    User getUserById(String id);
    User getUserByUsername(String userName);
    void addUser(User user);
    void removeUser(User userid);

    boolean checkCredentials(String username,String password);
}
