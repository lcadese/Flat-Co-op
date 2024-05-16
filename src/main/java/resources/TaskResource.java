package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.TaskDAO;
import domain.ErrorMessage;
import domain.Task;
import io.jooby.Jooby;
import io.jooby.StatusCode;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public class TaskResource extends Jooby {

    private final Gson gson;

    public TaskResource(TaskDAO taskDAO) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        this.gson = gsonBuilder.create();

        path("/tasks", () -> {

            post("/", ctx -> {
                System.out.println("Received request to create task: " + ctx.body().value());
                Task task = gson.fromJson(ctx.body().value(), Task.class);
                if (task.getTaskID() == null || task.getTaskID().isEmpty()) {
                    task.setTaskID(UUID.randomUUID().toString()); // Set a new UUID
                }
                try {
                    taskDAO.createTask(task);
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.CREATED).send(gson.toJson(task));
                } catch (Exception e) {
                    System.out.println(e);
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error creating task: " + e.getMessage())));
                }
            });

            get("/{taskId}", ctx -> {
                String taskId = ctx.path("taskId").value();
                Task task = taskDAO.getTask(taskId);
                if (task != null) {
                    return ctx.setResponseType("application/json").send(gson.toJson(task));
                } else {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.NOT_FOUND)
                            .send(gson.toJson(new ErrorMessage("Task not found")));
                }
            });

            delete("/{taskId}", ctx -> {
                String taskId = ctx.path("taskId").value();
                try {
                    taskDAO.removeTask(taskId);
                    return ctx.send(StatusCode.NO_CONTENT);
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error deleting task: " + e.getMessage())));
                }
            });

            put("/{taskId}", ctx -> {
                String taskId = ctx.path("taskId").value();
                Task updatedTask = gson.fromJson(ctx.body().value(), Task.class);
                if (!taskId.equals(updatedTask.getTaskID())) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Task ID in the URL does not match the Task ID in the body")));
                }
                try {
                    // Update the task in the database
                    taskDAO.setCompleteTask(taskId, updatedTask.getCompleted());
                    if (updatedTask.getCompleted()) {
                        return ctx.setResponseType("application/json").send(gson.toJson(new ErrorMessage("Task marked as completed")));
                    } else {
                        return ctx.setResponseType("application/json").send(gson.toJson(new ErrorMessage("Task marked as not completed")));
                    }
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.SERVER_ERROR)
                            .send(gson.toJson(new ErrorMessage("Error updating task: " + e.getMessage())));
                }
            });
            get("/flatID/{flatID}",ctx ->{
                String flatID = ctx.path("flatID").value();
                Collection<Task> tasks = taskDAO.getTaskByFlat(flatID);
                return ctx.setResponseType("application/json").send(gson.toJson(tasks));
            });

            get("/userID/{userID}",ctx ->{
                String userID = ctx.path("userID").value();
                Collection<Task> tasks = taskDAO.getTaskByUser(userID);
                return ctx.setResponseType("application/json").send(gson.toJson(tasks));
            });

        });
    }
}

    
