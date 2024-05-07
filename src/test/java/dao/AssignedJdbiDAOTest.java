package dao;

import domain.Assigned;
import domain.Flat;
import domain.Task;
import domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

class AssignedJdbiDAOTest {

    AssignedDAO dao;
    static TaskDAO taskDAO;
    static FlatDAO flatdao;
    static UserDAO userDAO;
    static Task task;
    static User user;
    static Flat flat;
    Assigned assigned;

    @BeforeAll
    public static void initialise() {
        try {
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        }catch(java.lang.IllegalStateException ex){}
        userDAO = JdbiDaoFactory.getUserDAO();
        user = new User("1","Dave is best","God","Dave","Dave","Dave@gmail.com",null);

        flatdao = JdbiDaoFactory.getFlatDAO();
        flat = new Flat("1","10 downing street",user.getUserID(),"car bomb");

        taskDAO = JdbiDaoFactory.getTaskDAO();
        task = new Task("1","get bags","get Big bags", LocalDateTime.now(),flat.getflatID(),false);

        userDAO.addUser(user);
        flatdao.addFlat(flat);
        taskDAO.createTask(task);

    }

    @AfterAll
    public static void clean()
    {
       taskDAO.removeTask(task);
       flatdao.removeFlat(flat);
       userDAO.removeUser(user.getUserID());
    }

    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getAssignedDAO();
        assigned = new Assigned(user.getUserID(),flat.getflatID());

        dao.createAssigned(assigned);
    }

    @AfterEach
    void tearDown() {
        dao.removeAssigned(assigned);
    }

    @Test
    void getAssigned() {
        Assigned u = dao.getAssigned(user,task);
        assertThat(u, is(assigned));

        u = dao.getAssigned(user.getUserID(),task.getTaskID());
        assertThat(u, is(assigned));
    }

    @Test
    void getMultAssigned(){
        Collection<Assigned> u = dao.getMultAssigned(user);
        assertThat(u, hasItem(assigned));

        u = dao.getMultAssigned(task);
        assertThat(u, containsInAnyOrder(assigned));
    }

    @Test
    void createAssigned() {
        Assigned u = dao.getAssigned(user,task);
        assertThat(u, is(assigned));
    }
    @Test
    void removeAssigned() {
        dao.removeAssigned(assigned);
        Assigned u = dao.getAssigned(user,task);
        assertThat(u, is(nullValue()));
        dao.createAssigned(assigned);

        dao.removeAssigned(user,task);
        u = dao.getAssigned(user,task);
        assertThat(u, is(nullValue()));
        dao.createAssigned(assigned);

        dao.removeAssigned(user.getUserID(),task.getTaskID());
        u = dao.getAssigned(user,task);
        assertThat(u, is(nullValue()));
        dao.createAssigned(assigned);
    }

}