package app.open.software.rest;

import app.open.software.rest.handler.RequestHandler;
import app.open.software.rest.method.Method;
import app.open.software.rest.parameter.Parameter;
import app.open.software.rest.route.Route;

public class TestHandler implements RequestHandler {

	@Route
	@Method
	public final String response() {
		return WebServer.GSON.toJson(new Parameter("Test", "KLion"));
	}

}
