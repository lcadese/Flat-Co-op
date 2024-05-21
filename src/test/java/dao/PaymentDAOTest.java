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
        flat = new Flat("1","10 downing street",user.getUserID(),"car bomb");

      
        userDAO.addUser(user);
        flatdao.addFlat(flat);

    }

    @AfterAll
    public static void clean() {
        flatdao.removeFlat(flat);
        userDAO.removeUser(user.getUserID());
    }


    @BeforeEach
    public void setUp() {
        dao = JdbiDaoFactory.getPaymentDAO();
        
//        payment = new Payments(user.getUserID(),flat.getflatID(),new BigDecimal(1.0),false);
        payment = new Payments("payment3", user.getUserID(),new BigDecimal(1.0),false,"description");
        
        dao.createPayment(payment);
    }

    @AfterEach
    void tearDown() {
        dao.removePayment(payment);
    }

    @Test
    void getPayment() {
        assertThat(dao.getPayment("payment3"),is(payment));
    }
    
    @Test
    void getAllPayments() {
//        assertThat(dao.getPayment(user,task),is(payment));
                // Assuming dao.getAllPayments() returns a List<Payment>
        Collection<Payments> payments = dao.getAllPayments();
        System.out.println(payments.toString());
        
        // Check that the size of the returned list is 1
//        assertThat(payments, hasSize(1));
        assertThat(payments, hasItem(payment));
    }

    @Test
    void createPayment() {
        Payments payment1 = new Payments("payment4", user.getUserID(), new BigDecimal(2.0), false, "description");
        Collection<Payments> payments = dao.getAllPayments();
        System.out.println(payments.toString());
        
        // Check that the size of the returned list is 1
//        assertThat(payments, hasSize(1));
        assertThat(payments, not(hasItem(payment1)));
        dao.createPayment(payment1);
        System.out.println(payments.toString());
        assertThat(dao.getPayment("payment4"),is(payment1));
        dao.removePayment(payment1);
    }
//
    @Test
    void removePayment() {
        dao.removePayment(payment);
        assertThat(dao.getPayment(payment.getPaymentID()),is(nullValue()));
        dao.createPayment(payment);

        dao.removePayment(payment.getPaymentID());
        assertThat(dao.getPayment(payment.getPaymentID()),is(nullValue()));
//        dao.createPayment(payment);
    }

    @Test
    void markAsComplete() {
        dao.setPayed(payment,true);
        assertTrue(dao.getPayment(payment.getPaymentID()).getPayed());

        dao.setPayed(payment.getPaymentID(),false);
        assertFalse(dao.getPayment(payment.getPaymentID()).getPayed());

    }

    @Test
    void getPaymentsByUserID(){
        assertThat(dao.getPaymentsByUserID(user.getUserID()),hasItem(payment));
    }
}