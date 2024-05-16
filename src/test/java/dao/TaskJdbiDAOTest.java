package dao;

import domain.Assigned;
import domain.Flat;
import domain.Task;
import domain.User;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

class TaskJdbiDAOTest {

    static TaskDAO dao;
    static FlatDAO flatdao;
    static UserDAO userDAO;
    Task task;
    static User user;
    static Flat flat;

    @BeforeAll
    public static void initialise() {
        try {
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        }catch(java.lang.IllegalStateException ex){}
        userDAO = JdbiDaoFactory.getUserDAO();
        user = new User("1","Dave is best","God","Dave","Dave","Dave@gmail.com",null);

        flatdao = JdbiDaoFactory.getFlatDAO();
        flat = new Flat("1","10 downing street",user.getUserID(),"car bomb");

        userDAO.addUser(user);
        flatdao.addFlat(flat);

    }

    @AfterAll
    public static void clean()
    {
        flatdao.removeFlat(flat);
        userDAO.removeUser(user.getUserID());
    }


    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getTaskDAO();
        task = new Task("1","get bags","get Big bags", LocalDateTime.now(),flat.getflatID(),false);

        dao.createTask(task);
    }

    @AfterEach
    void tearDown() {
        dao.removeTask(task);
    }
    @Test
    void completeTask() {
        dao.setCompleteTask(task,true);
        assertTrue(dao.getTask(task.getTaskID()).getCompleted());

        dao.setCompleteTask(task.getTaskID(),false);
        assertTrue(!dao.getTask(task.getTaskID()).getCompleted());
    }

    @Test
    void createTask() {
        Task t = dao.getTask(task.getTaskID());
        assertThat(t,is(task));
    }

    @Test
    void removeTask() {
        dao.removeTask(task);
        assertThat(dao.getTask(task.getTaskID()),is(nullValue()));
        dao.createTask(task);

        dao.removeTask(task);
        assertThat(dao.getTask(task.getTaskID()),is(nullValue()));
        dao.createTask(task);
    }

    @Test
    void getTask() {
        assertThat(dao.getTask(task.getTaskID()),is(task));
    }

    @Test
    void getTaskByFlat() {
        assertThat(dao.getTaskByFlat(flat),hasItem(task));
        assertThat(dao.getTaskByFlat(flat.getflatID()),hasItem(task));
    }

    @Test
    void getTaskByUser()
    {
        Assigned assigned = new Assigned(task.getTaskID(),user.getUserID());
        JdbiDaoFactory.getAssignedDAO().createAssigned(assigned);
        assertThat(dao.getTaskByUser(user.getUserID()),hasItem(task));
        JdbiDaoFactory.getAssignedDAO().removeAssigned(assigned);
    }
}