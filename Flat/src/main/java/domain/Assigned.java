package domain;

public class Assigned {
    private String taskID;
    private String userID;
    private boolean completed;

    public Assigned(String taskID, String userID, boolean completed) {
        this.taskID = taskID;
        this.userID = userID;
        this.completed = completed;
    }

    public Assigned() {
    }

    public String getTask() {
        return taskID;
    }

    public void setTask(String task) {
        this.taskID = task;
    }

    public String getUser() {
        return userID;
    }

    public void setUser(String user) {
        this.userID = user;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    
}
