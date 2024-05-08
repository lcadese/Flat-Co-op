package domain;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Task(String taskID, String taskName, String description, LocalDateTime requestedDate, String flatID, boolean completed) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.description = description;
        this.requestedDate = requestedDate;
        this.flatID = flatID;
        this.completed = completed;
    }

    public Task() {
    }
    
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String id) {
        this.taskID = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getFlatID() {
        return flatID;
    }

    public void setFlatID(String flatID) {
        this.flatID = flatID;
    }


    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed && Objects.equals(taskID, task.taskID) && Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && Objects.equals(flatID, task.flatID);
    }
}
