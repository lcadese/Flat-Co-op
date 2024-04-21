package dao;

import domain.Flat;
import domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.not;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

class FlatJbdiDAOTest {
    public FlatJbdiDAOTest() {
    }

    FlatDAO dao;
    static UserDAO userDAO;
    Flat flat;
    static User user;

    @BeforeAll
    public static void initialise() {
        JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        userDAO = JdbiDaoFactory.getUserDAO();
        user = new User("1","Dave is best","God","Dave","Dave","Dave@gmail.com",null);

        userDAO.addUser(user);
    }

    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getFlatDAO();
        flat = new Flat("1","10 downing street","Car bomb",user.getUserID());

        dao.addFlat(flat);
    }

    @AfterEach
    void tearDown() {
        dao.removeFlat(flat);
    }
    @Test
    void addFlat() {assertThat(dao.getFlat(flat.getflatID()).getflatID(),is(flat.getflatID()));}

    @Test
    void getFlat() {
        Flat u = dao.getFlat(flat.getflatID());
        assertThat(u, Matchers.samePropertyValuesAs(flat, "flatID"));
        assertThat(dao.getFlat("BAD"),is(nullValue()));

    }

    @Test
    void removeFlat() {
        dao.removeFlat(flat);
        assertThat(dao.getFlat(flat.getflatID()),not(is(flat)));
        dao.addFlat(flat);
    }

}