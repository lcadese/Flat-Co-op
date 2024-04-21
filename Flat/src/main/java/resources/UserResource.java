package resources;

import dao.UserDAO;
import domain.ErrorMessage;
import domain.User;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 * UserResource handles the HTTP requests related to user operations.
 * @author Liam
 */
public class UserResource extends Jooby {

    public UserResource(UserDAO dao) {

        path("/user", () -> {

            post("/signup", ctx -> {
                User user = ctx.body().to(User.class);
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
