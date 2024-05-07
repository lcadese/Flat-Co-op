package dao;

import domain.Flat;
import org.junit.jupiter.api.*;
import domain.User;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

class UserJbdiDAOTest {

    public UserJbdiDAOTest() {
    }

    UserDAO dao;
    User user;

    @BeforeAll
    public static void initialise() {
        try {
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        }catch(java.lang.IllegalStateException ex){}
    }

    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getUserDAO();
        user = new User("1","Dave is best","God","Dave","Smith","Dave@gmail.com",null);

        dao.addUser(user);
    }

    @AfterEach
    void tearDown() {
        dao.removeUser("1");
    }

    @Test
    void getUserById() {
        User u = dao.getUserById(user.getUserID());
        assertThat(u, Matchers.samePropertyValuesAs(user, "userId"));
        assertThat(dao.getUserById("BAD"),is(nullValue()));
    }

    @Test
    void getUserByUsername() {
        User u = dao.getUserByUsername(user.getUsername());
        assertThat(u, Matchers.samePropertyValuesAs(user, "userId"));
        assertThat(dao.getUserByUsername("BAD"),is(nullValue()));
    }

    @Test
    void addUser() {
        assertThat(dao.getUserByUsername(user.getUsername()).getUserID(),is(user.getUserID()));
    }

    @Test
    void removeUser()
    {
        dao.removeUser("1");
        assertThat(dao.getUserByUsername(user.getUsername()),not(is(user)));
        dao.addUser(user);
    }

    @Test
    void checkCredentials() {
        assertTrue(dao.checkCredentials(user.getUsername(),user.getPassword()));
        assertFalse(dao.checkCredentials("evil","worse evil"));
    }

    @Test
    void setFlat() {
        Flat flat = new Flat("2A","10 downing street",user.getUserID(),"car bomb");

        JdbiDaoFactory.getFlatDAO().addFlat(flat);

        dao.setFlat(user.getUserID(), flat.getflatID());
        assertThat(dao.getUserByUsername(user.getUsername()).getFlatID(),is(flat.getflatID()));
        dao.setFlat(user.getUserID(), user.getFlatID());

        JdbiDaoFactory.getFlatDAO().removeFlat(flat);
    }
}
