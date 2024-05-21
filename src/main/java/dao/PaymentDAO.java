package dao;

import domain.Payments;
import domain.Payments;
import domain.Task;
import domain.User;

import java.util.Collection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author haydenaish
 */
public interface PaymentDAO {
    Collection<Payments> getMultPayment(Task task);
    Collection<Payments> getMultPayment(User user);
    Payments getPayment(String paymentID);
    void createPayment(Payments payment);
    void removePayment(String paymentID);
    void removePayment(Payments payment);
    void setPayed(Payments payment,Boolean payed);
    void setPayed(String paymentID,Boolean payed);
    Collection<Payments> getAllPayments();
    Collection<Payments> getPaymentsByUserID(String UserID);
    

}
