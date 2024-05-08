package dao;

import domain.Assigned;
import domain.Task;
import domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Collection;

public interface AssignedJdbiDAO extends AssignedDAO {

    @Override
    @RegisterBeanMapper(Assigned.class)
    @SqlQuery("select * from assigned where taskID = :taskID")
    Collection<Assigned> getMultAssigned(@BindBean Task task);

    @Override
    @RegisterBeanMapper(Assigned.class)
    @SqlQuery("select * from assigned where userID = :userID")
    Collection<Assigned> getMultAssigned(@BindBean User user);

    @Override
    @SqlQuery("select * from assigned where userID = :user.userID and taskID = :task.taskID")
    @RegisterBeanMapper(Assigned.class)
    Assigned getAssigned(@BindBean("user") User user, @BindBean("task") Task task);

    @Override
    @SqlQuery("select * from assigned where userID = :userID and taskID = :taskID")
    @RegisterBeanMapper(Assigned.class)
    Assigned getAssigned(@Bind("userID") String userID, @Bind("taskID") String taskID);

    @Override
    @SqlUpdate("INSERT INTO assigned (flatID,userID,completed) values (:task.taskID,:user.userID,false)")
    void createAssigned(@Bind("user") User user, @Bind("task") Task task);

    @Override
    @SqlUpdate("INSERT INTO assigned (taskID,userID) values (:taskID,:userID)")
    void createAssigned(@BindBean Assigned assigned);

    @Override
    @SqlUpdate("INSERT INTO assigned (taskID, userID) VALUES (:taskID, :userID)")
    void createAssigned(@Bind("taskID") String taskID, @Bind("userID") String userID);

    @Override
    @SqlUpdate("delete from assigned where userID = :user.userID and taskID = :task.taskID")
    void removeAssigned(@BindBean("user") User user, @BindBean("task") Task task);

    @Override
    @SqlUpdate("delete from assigned where userID = :userID and taskID = :taskID")
    void removeAssigned(@Bind("userID") String userID, @Bind("taskID") String taskID);

    @Override
    @SqlUpdate("delete from assigned where userID = :userID and taskID = :taskID")
    void removeAssigned(@BindBean Assigned assigned);
}
