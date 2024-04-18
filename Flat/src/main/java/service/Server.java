package service;

import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import java.io.IOException;

public class Server extends Jooby {

	public Server() {
		// add dao import
		// add support for JSON
		install(new GsonModule());

		// add the resources
		// mount(dao);

		// add the OpenAPI module for Swagger support
		install(new OpenAPIModule());

		// provide our OAS specification to the Swagger UI
		assets("/FlatHub.json", "FlatHub.json");
		assets("/FlatHub.yaml", "FlatHub.yaml");

		// redirect requests to / to /swagger
		get("/", ctx -> ctx.sendRedirect("/swagger"));
	}

	public static void main(String[] args) throws IOException {
		// start the server
		new Server()
				.setServerOptions(new ServerOptions().setPort(8080))
				.start();
	}

}
