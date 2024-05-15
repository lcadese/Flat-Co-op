package dao;
import domain.Flat;
import domain.User;

import java.util.Collection;

public interface FlatDAO {

    void addFlat(Flat flat);
    Flat getFlat(String FlatID);
    void removeFlat(Flat flat);
    void removeFlat(String flatID);
    Collection<User> getAllUsers(String flatID);
   
}
