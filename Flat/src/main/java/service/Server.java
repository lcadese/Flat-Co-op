package service;

import dao.JdbiDaoFactory;
import dao.UserDAO;
import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import resources.UserResource;

import java.io.IOException;

public class Server extends Jooby {

	public Server() {

		UserDAO userDAO = JdbiDaoFactory.getUserDAO();

		// add dao import
		// add support for JSON
		install(new GsonModule());

		// add the resources
		// mount(dao);

		// add the OpenAPI module for Swagger support
		install(new OpenAPIModule());

		// provide our OAS specification to the Swagger UI
		assets("/openapi.json", "FlatHub.json");
		assets("/openapi.yaml", "FlatHub.yaml");

		// redirect requests to / to /swagger
		get("/", ctx -> ctx.sendRedirect("/swagger"));

		mount(new UserResource(userDAO));
	}

	public static void main(String[] args) throws IOException {
		// start the server
		new Server()
				.setServerOptions(new ServerOptions().setPort(8080))
				.start();
	}

}
