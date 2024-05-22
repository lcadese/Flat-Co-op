package resources;

import com.google.gson.Gson;
import dao.FlatDAO;
import dao.UserDAO;
import domain.ErrorMessage;
import domain.Flat;
import domain.User;
import io.jooby.Jooby;
import io.jooby.StatusCode;

import java.util.Collection;
import java.util.UUID;

/**
 * FlatResource handles the HTTP requests related to flat operations.
 *
 * @author Liam
 */
public class FlatResource extends Jooby {

    // Create a Gson instance
    private final Gson gson = new Gson();

    public FlatResource(FlatDAO flatDAO, UserDAO userDAO) {

        path("/flat", () -> {

            post("/create", ctx -> {
                System.out.println("Received request to create flat: " + ctx.body().value());
                Flat flat = gson.fromJson(ctx.body().value(), Flat.class);
                if (flat.getflatID() == null || flat.getflatID().isEmpty()) {
                    flat.setflatID(UUID.randomUUID().toString()); // Set a new UUID
                }

                try {
                    flatDAO.addFlat(flat);
                    userDAO.setFlat(flat.getHost(),flat.getflatID());
                    return ctx.setResponseCode(StatusCode.CREATED).setResponseType("application/json").send(gson.toJson(flat));
                } catch (Exception e) {
                    System.out.println(e);
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson("Error adding flat: " + e.getMessage()));
                }
            });

            get("/{flatId}", ctx -> {
                String flatId = ctx.path("flatId").value();
                Flat flat = flatDAO.getFlat(flatId);
                if (flat != null) {
                    String jsonResponse = gson.toJson(flat);
                    return ctx.setResponseType("application/json").send(jsonResponse);
                } else {
                    String errorResponse = gson.toJson(new ErrorMessage("Flat not found"));
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.NOT_FOUND).send(errorResponse);
                }
            });

            delete("/{flatId}", ctx -> {
                String flatId = ctx.path("flatId").value();
                try {
                    flatDAO.removeFlat(flatId);
                    return ctx.send(StatusCode.NO_CONTENT);
                } catch (Exception e) {
                    String errorResponse = gson.toJson(new ErrorMessage("Error deleting flat: " + e.getMessage()));
                    return ctx.setResponseType("application/json").setResponseCode(StatusCode.BAD_REQUEST).send(errorResponse);
                }
            });

        });
        path("/flatUsers", () -> {
            get("/{flatId}", ctx -> {
                String flatId = ctx.path("flatId").value();
                Collection<User> users = flatDAO.getAllUsers(flatId);
                return ctx.setResponseType("application/json").send(gson.toJson(users));
            });
        });

    }
}
