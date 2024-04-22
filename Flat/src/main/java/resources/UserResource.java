package resources;

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

    public UserResource(UserDAO dao) {

        path("/user", () -> {

            post("/signup", ctx -> {
                User user = ctx.body().to(User.class);
               if (user.getUserID() == null || user.getUserID().isEmpty()) {
                  user.setUserID(UUID.randomUUID().toString()); // Set a new UUID
                }
                User existingUser = dao.getUserByUsername(user.getUsername());
                if (existingUser == null) {
                    dao.addUser(user);
                    return ctx.send(StatusCode.CREATED);
                } else {
                    return ctx
                            .setResponseCode(StatusCode.CONFLICT)
                            .render(new ErrorMessage("That username already exists"));
                }
            });

            get("/login/{userId}", ctx -> {
                String userId = ctx.path("userId").value();
                User user = dao.getUserById(userId);
                if (user != null) {
                    return user;
                } else {
                    return ctx
                            .setResponseCode(StatusCode.NOT_FOUND)
                            .render(new ErrorMessage("User login is incorrect"));
                }
            });

            get("/test", ctx -> {
                return dao.getUserById("1");
            });

        });

    }
}
