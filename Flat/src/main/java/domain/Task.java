package domain;

/**
 *
 * @author haydenaish
 */
public class Task {
    private String taskID;
    private String taskName;
    private String description;
    private String date;
    private String flatID;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlat() {
        return flatID;
    }

    public void setFlat(String flatID) {
        this.flatID = flatID;
    }
    
}
