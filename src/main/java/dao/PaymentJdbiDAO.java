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
    @SqlUpdate("INSERT INTO payment (paymentID, userID, amount, payed, description) values (:paymentID,:userID, :amount, :payed, :description)")
    void createPayment(@BindBean Payments payment);

    @Override
    @SqlUpdate("UPDATE payment SET payed = :payed WHERE paymentID = :payment.paymentID")
    void setPayed (@BindBean("payment") Payments payment, @Bind("payed") Boolean payed);

    @Override
    @SqlUpdate("UPDATE payment SET payed = :payed WHERE paymentID = :paymentID")
    void setPayed(@Bind("paymentID") String paymentID, @Bind("payed") Boolean payed);

    
    @Override
    @SqlQuery("select * from payment")
    @RegisterBeanMapper(Payments.class)
    Collection<Payments> getAllPayments();


    @Override
    @SqlQuery("select * from payment where paymentID = :paymentID")
    @RegisterBeanMapper(Payments.class)
    Payments getPayment(@Bind("paymentID") String paymentID);


    @Override
    @SqlUpdate("delete from payment where paymentID = :paymentID")
    void removePayment(@Bind("paymentID") String paymentID);

    @Override
    @SqlUpdate("delete from payment where paymentID = :paymentID")
    void removePayment(@BindBean Payments payment);

    @Override
    @SqlQuery("select * from payment where userID = :userID")
    @RegisterBeanMapper(Payments.class)
    Collection<Payments> getPaymentsByUserID(@Bind("userID") String UserID);
}
