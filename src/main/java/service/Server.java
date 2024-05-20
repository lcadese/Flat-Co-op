package service;

import dao.AssignedDAO;
import dao.FlatDAO;
import dao.JdbiDaoFactory;
import dao.PaymentDAO;
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
import resources.AssignedResource;
import resources.PaymentResource;


public class Server extends Jooby {

    public Server()  {
        try {
            String dir = System.getProperty("user.dir") + "\\h2-windows\\h2";
            Runtime.getRuntime().exec("javaw.exe -cp "+dir+"\\h2-2.2.220-info202.jar -Duser.home=\""+dir+"\" -Dh2.baseDir=\""+dir+"\" -Dh2.bindAddress=localhost -Dh2.consoleTimeout=5400000 org.h2.tools.Console -tcp -tcpPort 9092 -web -webPort 8082 -tool");
        } catch (Exception e)
        {
            System.out.println("An error in the database");
            System.out.println(e);
        }

        // Initialize the data access object for users
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
        mount(new FlatResource(flatDAO,userDAO));
        mount(new TaskResource(taskDAO));
        mount(new AssignedResource(assignedDAO));
        mount(new PaymentResource(paymentDAO));

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
