package dao;

import domain.Assigned;
import domain.Task;
import domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AssignedJdbiDAO extends AssignedDAO{
    @Override
    @SqlQuery("select * from assigned where taskID = :taskID")
    @RegisterBeanMapper(Assigned.class)
    Assigned getAssigned(@BindBean Task task);

    @Override
    @SqlQuery("select * from assigned where userID = :userID")
    @RegisterBeanMapper(Assigned.class)
    Assigned getAssigned(@BindBean User user);

    @Override
    @SqlQuery("select * from assigned where userID = :user.userID and taskID = :task.taskID")
    @RegisterBeanMapper(Assigned.class)
    Assigned getAssigned(@Bind("user") User user,@Bind("task") Task task);

    @Override
    @SqlUpdate("INSERT INTO flat (flatID,userID,completed) values (:task.taskID,:user.userID,false)")
    void createAssigned(@Bind("user") User user,@Bind("task") Task task);

    @Override
    @SqlUpdate("INSERT INTO flat (flatID,userID,completed) values (:taskID,:userID,:completed)")
    void createAssigned(@BindBean Assigned assigned);

    @Override
    @SqlUpdate("delete from flat where userID = :user.userID and taskID = :task.taskID")
    void removeAssigned(@Bind("user") User user,@Bind("task") Task task);

    @Override
    @SqlUpdate("delete from flat where userID = :userID and taskID = :taskID")
    void removeAssigned(@BindBean Assigned assigned);
}
