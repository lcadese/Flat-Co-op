package dao;

import domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserJbdiDAO extends UserDAO {

    @Override
    @SqlQuery("select * from users where userid=:id")
    @RegisterBeanMapper(User.class)
    public User getUserById(@Bind("id") String id);

    @Override
    @SqlQuery("select * from users where userName=:userName")
    @RegisterBeanMapper(User.class)
    public User getUserByUsername(@Bind("userName") String userName);

    @Override
    @SqlUpdate("INSERT INTO users (userid,username,password,lastname,firstname,email,flatid) values (:userID,:username,:password,:firstName,:lastName,:email,:flatID)")
    public void addUser(@BindBean User user);
    @Override
    @SqlUpdate("delete from users where userid = :userID")
    void removeUser(@BindBean User userid);

    @Override
    @SqlQuery("select exists(select * from Users where username = :user and password = :pass)")
    boolean checkCredentials(@Bind("user") String username,@Bind("pass") String password);

}
