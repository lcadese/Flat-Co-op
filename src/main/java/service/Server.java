package service;

import dao.*;
import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.ServerOptions;
import io.jooby.exception.StatusCodeException;
import io.jooby.gson.GsonModule;
import io.jooby.handler.Cors;
import io.jooby.handler.CorsHandler;
import resources.*;

import java.io.IOException;

public class Server extends Jooby {

    public Server() {
        // Initialize the database
        initializeDatabase();

        // Initialize the data access objects
        UserDAO userDAO = JdbiDaoFactory.getUserDAO();
        FlatDAO flatDAO = JdbiDaoFactory.getFlatDAO();
        TaskDAO taskDAO = JdbiDaoFactory.getTaskDAO();
        AssignedDAO assignedDAO = JdbiDaoFactory.getAssignedDAO();
        PaymentDAO paymentDAO = JdbiDaoFactory.getPaymentDAO();

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
        mount(new FlatResource(flatDAO, userDAO));
        mount(new TaskResource(taskDAO));
        mount(new AssignedResource(assignedDAO));
        mount(new PaymentResource(paymentDAO));

        // Error handling
        error(StatusCodeException.class, (ctx, cause, statusCode) -> {
            ctx.setResponseCode(statusCode).send(cause.getMessage());
        });
    }

    private void initializeDatabase() {
        try {
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:testdb;INIT=runscript from 'src/main/java/dao/schema.sql'");
        } catch (java.lang.IllegalStateException ex) {
            // Handle the exception
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server()
            .setServerOptions(new ServerOptions().setPort(8080))
            .start();
    }
}
