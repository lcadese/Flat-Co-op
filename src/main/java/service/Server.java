package service;

import dao.FlatDAO;
import dao.JdbiDaoFactory;
import dao.TaskDAO;
import dao.UserDAO;
import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.ServerOptions;
import io.jooby.exception.StatusCodeException;
import io.jooby.gson.GsonModule;
import io.jooby.handler.Cors;
import io.jooby.handler.CorsHandler;

import resources.UserResource;

import java.io.IOException;
import resources.FlatResource;
import resources.TaskResource;

public class Server extends Jooby {

    public Server() {
        // Initialize the data access object for users
        UserDAO userDAO = JdbiDaoFactory.getUserDAO();
        FlatDAO flatDAO = JdbiDaoFactory.getFlatDAO();
        TaskDAO taskDAO = JdbiDaoFactory.getTaskDAO();

        // Setup JSON handling with Gson
        install(new GsonModule());

        use(new CorsHandler(new Cors().setMethods("GET", "POST", "PUT", "DELETE")));

        // Setup API documentation with Swagger/OpenAPI
        install(new OpenAPIModule());
        assets("/openapi.json", "FlatHub.json");
        assets("/openapi.yaml", "FlatHub.yaml");

        // Redirect root URL to Swagger UI for easy API documentation access
        get("/", ctx -> ctx.sendRedirect("/swagger"));

        // Mount the resource 
        mount(new UserResource(userDAO));
        mount(new FlatResource(flatDAO));
        mount(new TaskResource(taskDAO));

        //error handling
        error(StatusCodeException.class, (ctx, cause, statusCode) -> {
            ctx.setResponseCode(statusCode).send(cause.getMessage());
        });
    }

    public static void main(String[] args) throws IOException {
        new Server()
            .setServerOptions(new ServerOptions().setPort(8080))
            .start();
    }
}
