package domain;

import java.util.Objects;

public class Assigned {
    private String taskID;
    private String userID; 

    public Assigned(String taskID, String userID) {
        this.taskID = taskID;
        this.userID = userID;
    }

    public Assigned() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assigned assigned = (Assigned) o;
        return Objects.equals(taskID, assigned.taskID) && Objects.equals(userID, assigned.userID);
    }
}
