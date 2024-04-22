package dao;

import domain.Payments;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author haydenaish
 */
public interface PaymentDAO {
    void createPayment(Payments payment);
    void markAsComplete (Payments payment);
    
    
}
