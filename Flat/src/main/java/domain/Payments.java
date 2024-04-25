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

    public String getTask() {
        return taskID;
    }

    public void setTask(String task) {
        this.taskID = task;
    }

    public String getToUser() {
        return userID;
    }

    public void setToUser(String toUser) {
        this.userID = toUser;
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
