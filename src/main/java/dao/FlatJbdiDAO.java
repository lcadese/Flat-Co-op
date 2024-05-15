package dao;

import domain.Flat;
import domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Collection;

public interface FlatJbdiDAO extends FlatDAO {
    @Override
    @SqlUpdate("INSERT INTO flat (flatID,address,host,name) values (:flatID,:address,:host,:name)")
    void addFlat(@BindBean Flat flat);

    @Override
    @SqlQuery("select * from flat where flatid=:id")
    @RegisterBeanMapper(Flat.class)
    Flat getFlat(@Bind("id") String FlatID);

    @Override
    @SqlUpdate("delete from flat where flatid = :flatID")
    void removeFlat(@BindBean Flat flat);

    @Override
    @SqlUpdate("delete from flat where flatid = :flatID")
    void removeFlat(@Bind("flatID") String flatID);

    @Override
    @SqlQuery("select * from users where flatid = :flatID")
    @RegisterBeanMapper(User.class)
    Collection<User> getAllUsers(@Bind("flatID") String flatID);
    
}
