package resources;

import com.google.gson.Gson;
import dao.UserDAO;
import domain.ErrorMessage;
import domain.User;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.util.UUID;

/**
 * UserResource handles the HTTP requests related to user operations.
 * 
 * @author Liam
 */
public class UserResource extends Jooby {

    // Create a Gson instance
    private final Gson gson = new Gson();

    public UserResource(UserDAO dao) {

        path("/user", () -> {

            post("/signup", ctx -> {
                User user = gson.fromJson(ctx.body().value(), User.class);
                if (user.getUserID() == null || user.getUserID().isEmpty()) {
                    user.setUserID(UUID.randomUUID().toString()); // Set a new UUID
                }
                User existingUser = dao.getUserByUsername(user.getUsername());
                if (existingUser == null) {
                    dao.addUser(user);
                    return ctx.setResponseType("application/json").send(StatusCode.CREATED);
                } else {
                    String errorResponse = gson.toJson(new ErrorMessage("That username already exists"));
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.CONFLICT).send(errorResponse);
                }
            });

            post("/login", ctx -> {
                User loginUser = gson.fromJson(ctx.body().value(), User.class);
                boolean credentialsValid = dao.checkCredentials(loginUser.getUsername(), loginUser.getPassword());
                if (credentialsValid) {
                    User user = dao.getUserByUsername(loginUser.getUsername());
                    String jsonResponse = gson.toJson(user);
                    return ctx.setResponseType("application/json").send(jsonResponse);
                } else {
                    String errorResponse = gson.toJson(new ErrorMessage("Invalid username or password"));
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.UNAUTHORIZED).send(errorResponse);
                }
            });

            get("/{userID}", ctx -> {
                String userID = ctx.path("userID").value();
                User user = dao.getUserById(userID);
                if (user != null) {
                    String jsonResponse = gson.toJson(user);
                    return ctx.setResponseType("application/json").send(jsonResponse);
                } else {
                    String errorResponse = gson.toJson(new ErrorMessage("User not found"));
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.NOT_FOUND).send(errorResponse);
                }
            });
            
            delete("/{userID}", ctx -> {
                String userID = ctx.path("userID").value();
                dao.removeUser(userID);
                return ctx.send(StatusCode.NO_CONTENT);
            });

        });

    }
}
