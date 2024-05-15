package dao;

import domain.Flat;
import domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
        try {
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        } catch (java.lang.IllegalStateException ex) {
        }

        userDAO = JdbiDaoFactory.getUserDAO();
        user = new User("1", "Dave is best", "God", "Dave", "Dave", "Dave@gmail.com", null);

        userDAO.addUser(user);
    }

    @AfterAll
    public static void clean()
    {
        userDAO.removeUser(user.getUserID());
    }

    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getFlatDAO();
        flat = new Flat("1", "10 downing street", user.getUserID(),"car bomb");

        dao.addFlat(flat);
        userDAO.setFlat(user.getUserID(),flat.getflatID());
        user.setFlatID(flat.getflatID());
    }

    @AfterEach
    void tearDown() {
        userDAO.setFlat(user.getUserID(),null);
        user.setFlatID(null);
        dao.removeFlat(flat.getflatID());
    }

    @Test
    void addFlat() {
        assertThat(dao.getFlat(flat.getflatID()).getflatID(), is(flat.getflatID()));
    }

    @Test
    void getFlat() {
        Flat u = dao.getFlat(flat.getflatID());
        assertThat(u, Matchers.samePropertyValuesAs(flat, "flatID"));
        assertThat(dao.getFlat("BAD"), is(nullValue()));
    }

    @Test
    void removeFlat() {
        userDAO.setFlat(user.getUserID(),null);
        user.setFlatID(null);
        dao.removeFlat(flat);
        assertThat(dao.getFlat(flat.getflatID()), not(is(flat)));
        dao.addFlat(flat);
        dao.removeFlat(flat.getflatID());
        assertThat(dao.getFlat(flat.getflatID()), not(is(flat)));
        dao.addFlat(flat);
    }

    @Test
    void getAllUsers() {
        System.out.println(userDAO.getUserById(user.getUserID()));
        assertThat(dao.getAllUsers(flat.getflatID()),hasItem(user));
    }

}
