package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AssignedDAO;
import domain.Assigned;
import domain.Task;
import domain.User;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 * AssignedResource handles HTTP requests related to task-user assignments.
 */
public class AssignedResource extends Jooby {

    private final Gson gson = new GsonBuilder().serializeNulls().create();

    public AssignedResource(AssignedDAO assignedDao) {

        path("/assigned", () -> {

            // Get all assignments for a specific task using Task's taskID
            get("/task/{taskID}", ctx -> {
                Task task = new Task();
                task.setTaskID(ctx.path("taskID").value()); // Manually setting taskID
                return ctx.send(gson.toJson(assignedDao.getMultAssigned(task)));
            });

            // Get all assignments for a specific user using User's userID
            get("/user/{userID}", ctx -> {
                User user = new User();
                user.setUserID(ctx.path("userID").value()); // Manually setting userID
                return ctx.send(gson.toJson(assignedDao.getMultAssigned(user)));
            });

            post("/", ctx -> {
                try {
                    String body = ctx.body().value(); 
                    Assigned assigned = gson.fromJson(body, Assigned.class);  // Deserialize JSON to Assigned object
                    assignedDao.createAssigned(assigned.getTaskID(), assigned.getUserID());
                    return ctx.setResponseType("application/json").send(StatusCode.CREATED);
                } catch (Exception e) {
                    // Log exception and return an error response
                    e.printStackTrace();
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.SERVER_ERROR)
                            .send(gson.toJson("Error processing request"));
                }
            });

            // Remove an assignment with specified taskID and userID
            delete("/{taskID}/{userID}", ctx -> {
                String taskID = ctx.path("taskID").value();
                String userID = ctx.path("userID").value();
                Assigned assigned = new Assigned(taskID, userID); // Using the provided constructor
                assignedDao.removeAssigned(assigned);
                return ctx.send(StatusCode.NO_CONTENT);
            });

        });

    }
}
