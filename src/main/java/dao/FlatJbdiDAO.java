package dao;

import domain.Flat;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FlatJbdiDAO extends FlatDAO {
    @Override
    @SqlUpdate("INSERT INTO flat (flatID,address,name,host) values (:flatID,:address,:name,:host)")
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
    
}