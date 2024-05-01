package dao;

import domain.Flat;
import domain.Payments;
import domain.Task;
import domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {

    PaymentDAO dao;
    static TaskDAO taskDAO;
    static FlatDAO flatdao;
    static UserDAO userDAO;
    static Task task;
    static User user;
    static Flat flat;
    Payments payment;

    @BeforeAll
    public static void initialise() {
        try {
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        }catch(java.lang.IllegalStateException ex){}
        userDAO = JdbiDaoFactory.getUserDAO();
        user = new User("1","Dave is best","God","Dave","Dave","Dave@gmail.com",null);

        flatdao = JdbiDaoFactory.getFlatDAO();
        flat = new Flat("1","10 downing street",user.getUserID());

        taskDAO = JdbiDaoFactory.getTaskDAO();
        task = new Task("1","get bags","get Big bags", LocalDateTime.now(),flat.getflatID(),false);

        userDAO.addUser(user);
        flatdao.addFlat(flat);
        taskDAO.createTask(task);

    }

    @AfterAll
    public static void clean() {
        taskDAO.removeTask(task);
        flatdao.removeFlat(flat);
        userDAO.removeUser(user.getUserID());
    }


    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getPaymentDAO();
        payment = new Payments(user.getUserID(),flat.getflatID(),new BigDecimal(1.0),false);

        dao.createPayment(payment);
    }

    @AfterEach
    void tearDown() {
        dao.removePayment(payment);
    }
    @Test
    void getMultPayment() {
        assertThat(dao.getMultPayment(task),hasItem(payment));
        assertThat(dao.getMultPayment(user),hasItem(payment));
    }

    @Test
    void getPayment() {
        assertThat(dao.getPayment(user,task),is(payment));
        assertThat(dao.getPayment(user.getUserID(),task.getTaskID()),is(payment));
    }

    @Test
    void createPayment() {
        assertThat(dao.getPayment(user,task),is(payment));
    }

    @Test
    void removePayment() {
        dao.removePayment(payment);
        assertThat(dao.getPayment(user,task),is(nullValue()));
        dao.createPayment(payment);

        dao.removePayment(user,task);
        assertThat(dao.getPayment(user,task),is(nullValue()));
        dao.createPayment(payment);

        dao.removePayment(user.getUserID(),task.getTaskID());
        assertThat(dao.getPayment(user,task),is(nullValue()));
        dao.createPayment(payment);
    }

    @Test
    void markAsComplete() {
        dao.setPayed(payment,true);
        assertTrue(dao.getPayment(user,task).getPayed());

        dao.setPayed(user,task,false);
        assertFalse(dao.getPayment(user, task).getPayed());

        dao.setPayed(user.getUserID(),task.getTaskID(),true);
        assertTrue(dao.getPayment(user,task).getPayed());
    }
}