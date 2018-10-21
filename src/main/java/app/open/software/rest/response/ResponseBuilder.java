package app.open.software.rest.response;

import io.netty.handler.codec.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ResponseBuilder {

	private final FullHttpResponse response;

	public ResponseBuilder(final FullHttpRequest request, final HttpResponseStatus status) {
		this.response = new DefaultFullHttpResponse(request.protocolVersion(), status);
	}

	public final ResponseBuilder writeContent(final String content) {
		this.response.content().writeBytes(content.getBytes());
		return this;
	}

	public final ResponseBuilder setHeader(final String key, final String value) {
		this.response.headers().set(key, value);
		return this;
	}

	public final ResponseBuilder setHeaders(final Map<String, String> headers) {
		headers.forEach(this::setHeader);
		return this;
	}

	public final ResponseBuilder writeContent(final File content) throws IOException {
		return this.writeContent(Files.readString(content.toPath()));
	}

	public final ResponseBuilder writeContent(final Path content) throws IOException {
		return this.writeContent(Files.readString(content));
	}

	public final FullHttpResponse getResponse() {
		return this.response;
	}

}
