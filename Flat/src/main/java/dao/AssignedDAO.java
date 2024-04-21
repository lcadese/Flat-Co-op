package dao;

import domain.Assigned;
import domain.Task;
import domain.User;

public interface AssignedDAO {
    Assigned getAssigned(Task task);
    Assigned getAssigned(User user);
    Assigned getAssigned(User user,Task task);
    void createAssigned(User user,Task task);
    void createAssigned(Assigned assigned);
    void removeAssigned(User user,Task task);
    void removeAssigned(Assigned assigned);

}
