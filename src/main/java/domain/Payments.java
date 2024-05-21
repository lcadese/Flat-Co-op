package domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author haydenaish
 */
public class Payments {
    private String paymentID;
    private String userID;
    private BigDecimal amount;
    private boolean payed;
    private String description;

    public Payments(String paymentID, String userID, BigDecimal amount, boolean payed, String description) {
        this.paymentID = paymentID;
        this.userID = userID;
        this.amount = amount;
        this.payed = payed;
        this.description = description;
    }

    public Payments() {
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean getPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payments payments = (Payments) o;
        return payed == payments.payed && Objects.equals(paymentID, payments.paymentID) && Objects.equals(userID, payments.userID) && Objects.equals(amount, payments.amount);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.paymentID);
        hash = 17 * hash + Objects.hashCode(this.userID);
        hash = 17 * hash + Objects.hashCode(this.amount);
        hash = 17 * hash + (this.payed ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Payments{" + "paymentID=" + paymentID + ", userID=" + userID + ", amount=" + amount + ", payed=" + payed + '}';
    }

}
