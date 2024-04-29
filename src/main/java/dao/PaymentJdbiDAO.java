/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Assigned;
import domain.Payments;
import domain.Task;
import domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Collection;

/**
 *
 * @author haydenaish
 */
public interface PaymentJdbiDAO extends PaymentDAO{
    @Override
    @SqlUpdate("INSERT INTO payment (taskID, UserID, Amount, Payed) values (:taskID,:userID, :amount, :payed)")
    void createPayment(@BindBean Payments payment);

    @Override
    @SqlUpdate("UPDATE payment SET payed = :payed WHERE taskID = :payment.taskID AND userID = :payment.userID")
    void setPayed (@BindBean("payment") Payments payment, @Bind("payed") Boolean payed);

    @Override
    @SqlUpdate("UPDATE payment SET payed = :payed WHERE taskID = :taskID AND userID = :userID")
    void setPayed(@Bind("userID") String user,@Bind("taskID") String task, @Bind("payed") Boolean payed);

    @Override
    @SqlUpdate("UPDATE payment SET payed = :payed WHERE taskID = :task.taskID AND userID = :user.userID")
    void setPayed(@BindBean("user") User user,@BindBean("task") Task task, @Bind("payed") Boolean payed);

    @Override
    @SqlQuery("select * from payment where taskID = :taskID")
    @RegisterBeanMapper(Payments.class)
    Collection<Payments> getMultPayment(@BindBean Task task);

    @Override
    @SqlQuery("select * from payment where userID = :userID")
    @RegisterBeanMapper(Payments.class)
    Collection<Payments> getMultPayment(@BindBean User user);

    @Override
    @SqlQuery("select * from payment where userID = :user.userID and taskID = :task.taskID")
    @RegisterBeanMapper(Payments.class)
    Payments getPayment(@BindBean("user") User user,@BindBean("task") Task task);

    @Override
    @SqlQuery("select * from payment where userID = :userID and taskID = :taskID")
    @RegisterBeanMapper(Payments.class)
    Payments getPayment(@Bind("userID") String userID,@Bind("taskID") String taskID);

    @Override
    @SqlUpdate("delete from payment where userID = :user.userID and taskID = :task.taskID")
    void removePayment(@BindBean("user") User user,@BindBean("task") Task task);

    @Override
    @SqlUpdate("delete from payment where userID = :userID and taskID = :taskID")
    void removePayment(@Bind("userID") String userID,@Bind("taskID") String taskID);

    @Override
    @SqlUpdate("delete from payment where userID = :userID and taskID = :taskID")
    void removePayment(@BindBean Payments payment);
}