/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Payments;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
/**
 *
 * @author haydenaish
 */
public interface PaymentJdbiDAO extends PaymentDAO{
    @Override
    @SqlUpdate("INSERT INTO payment (taskID, UserID, Amount, FlatID, Payed) values (:payment.taskID,:payment.userID, :payment.amount, :payment.payed)")
    void createPayment(@Bind("payment") Payments payment);
    
    @Override
    @SqlUpdate("UPDATE payment SET payed = true WHERE taskID = :payment.taskID AND userID = :payment.userID")
    void markAsComplete (@Bind("payment") Payments payment);
    
}
