package domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author haydenaish
 */
public class Payments {
    private String taskID;
    private String userID;
    private BigDecimal amount;
    private boolean payed;

    public Payments(String taskID, String userID, BigDecimal amount, boolean payed) {
        this.taskID = taskID;
        this.userID = userID;
        this.amount = amount;
        this.payed = payed;
    }

    public Payments() {
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
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
        return payed == payments.payed && Objects.equals(taskID, payments.taskID) && Objects.equals(userID, payments.userID) && Objects.equals(amount, payments.amount);
    }

}
