package domain;

public class Assigned {
    private Task task;
    private User user;
    private boolean completed;

    public Assigned(Task task, User user, boolean completed) {
        this.task = task;
        this.user = user;
        this.completed = completed;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    
}
