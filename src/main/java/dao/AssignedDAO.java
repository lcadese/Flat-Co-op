package dao;

import domain.Assigned;
import domain.Task;
import domain.User;

import java.util.Collection;

public interface AssignedDAO {

    Collection<Assigned> getMultAssigned(Task task);

    Collection<Assigned> getMultAssigned(User user);

    Assigned getAssigned(User user, Task task);

    Assigned getAssigned(String userID, String taskID);

    void createAssigned(User user, Task task);

    void createAssigned(Assigned assigned);

    void createAssigned(String taskID, String userID);

    void removeAssigned(User user, Task task);

    void removeAssigned(String userID, String taskID);

    void removeAssigned(Assigned assigned);

}
