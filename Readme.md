# Open-Rest

Open-Rest is a Rest framework which works with an style of many annotations, to configure easily your Rest API. 

You are welcome to use it in your own project, if a working version was released.

## Maven

You have to include this repository:
```xml
<repositories>
    <repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
    </repository>
</repositories>
```
And then you can use the latest version as dependency:
```xml
<dependency>
    <groupId>com.github.Open-Cloud-Services</groupId>
    <artifactId>Open-Rest</artifactId>
    <version>1.0</version>
</dependency>
```

## Example

Here is a little Example, what you can already do:

>**Main Class**

```java 
package app.open.software.rest;

import app.open.software.rest.handler.RequestHandlerProvider;
import app.open.software.rest.route.Router;
import app.open.software.rest.version.ApiVersion;

public class RestAPI {

	public static void main(String... args) {

		//Create an instance of an ExampleHandler
		final ExampleHandler exampleHandler = new ExampleHandler();

		//Create an instance of an RequestHandlerProvider and add the ExampleHandler to it
		final RequestHandlerProvider requestHandlerProvider = new RequestHandlerProvider().add(exampleHandler);

		//Create a first ApiVersion (/api/v1/...)
		final ApiVersion version = new ApiVersion(1, requestHandlerProvider);

		//Create a Router and add the ApiVersion to it
		final Router router = new Router().addVersion(version);

		//And finally create the WebServer with port and an AuthHandler
		final WebServer webServer = new WebServer(80, router, request -> true);

		//Starting the WebServer
		webServer.start();
	}

}
```

>**ExampleHandler**
```java
package app.open.software.rest;

import app.open.software.rest.handler.RequestHandler;
import app.open.software.rest.method.Method;
import app.open.software.rest.parameter.PathParam;
import app.open.software.rest.route.Route;
import app.open.software.rest.type.ResponseType;

public class ExampleHandler implements RequestHandler {

	//Declare HttpMethod default GET
	@Method
	//Declare type of the response default Json
	@ResponseType
	//Declare the route of the response
	@Route("/example")
	public String getResponse(@PathParam("parameter") final String parameter) {
		return WebServer.GSON.toJson(parameter);
	}

}
```
