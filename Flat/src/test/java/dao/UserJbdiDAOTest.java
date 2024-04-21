package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.User;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
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
        JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
    }

    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getUserDAO();
        user = new User("1","Dave is best","God","Dave","Dave","Dave@gmail.com",null);

        dao.addUser(user);
    }

    @AfterEach
    void tearDown() {
        dao.removeUser(user);
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
        //dao.removeUser(user);
    }

    @Test
    void checkCredentials() {
    }
}