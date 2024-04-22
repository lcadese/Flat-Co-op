package domain;

import java.time.LocalDateTime;

/**
 *
 * @author haydenaish
 */
public class Task {
    private String taskID;
    private String taskName;
    private String description;
    private LocalDateTime requestedDate;
    private String flatID;
    private boolean completed;

    public Task(String taskID, String taskName, String description, String date, String flatID, boolean completed) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.description = description;
        this.date = date;
        this.flatID = flatID;
        this.completed = completed;
    }

    public Task() {
    }
    
    

    public Task() {
    }

    public Task(String taskID, String taskName, String description, LocalDateTime requestedDate, String flatID) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.description = description;
        this.requestedDate = requestedDate;
        this.flatID = flatID;
    }

    public String getId() {
        return taskID;
    }

    public void setId(String id) {
        this.taskID = id;
    }

    public String getName() {
        return taskName;
    }

    public void setName(String name) {
        this.taskName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getrequestedDate() {
        return requestedDate;
    }

    public void setrequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getFlat() {
        return flatID;
    }

    public void setFlat(String flatID) {
        this.flatID = flatID;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    
}
