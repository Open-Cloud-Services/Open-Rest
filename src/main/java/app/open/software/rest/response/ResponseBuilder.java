package app.open.software.rest.response;

import io.netty.handler.codec.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * {@link ResponseBuilder} to build easier a response
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class ResponseBuilder {

	/**
	 * {@link FullHttpResponse} as default response entity
	 */
	private final FullHttpResponse response;

	/**
	 * Creates the default response
	 *
	 * @param request {@link FullHttpRequest} to get the protocol version
	 * @param status {@link HttpResponseStatus} to response with a status
	 */
	public ResponseBuilder(final FullHttpRequest request, final HttpResponseStatus status) {
		this.response = new DefaultFullHttpResponse(request.protocolVersion(), status);
	}

	/**
	 * Set a header to the {@link FullHttpResponse}
	 *
	 * @param key Key of the header
	 * @param value Value of the header
	 */
	public final ResponseBuilder setHeader(final String key, final String value) {
		this.response.headers().set(key, value);
		return this;
	}

	/**
	 * Set more headers to the {@link FullHttpResponse}
	 *
	 * @param headers Map of all header values
	 */
	public final ResponseBuilder setHeaders(final Map<String, String> headers) {
		headers.forEach(this::setHeader);
		return this;
	}

	/**
	 * Write a string into the content of the {@link FullHttpResponse}
	 *
	 * @param content Content to write
	 */
	public final ResponseBuilder writeContent(final byte[] content) {
		this.response.content().writeBytes(content);
		return this;
	}

	/**
	 * Write a string into the content of the {@link FullHttpResponse}
	 *
	 * @param content Content to write
	 */
	public final ResponseBuilder writeContent(final String content) {
		this.response.content().writeBytes(content.getBytes());
		return this;
	}

	/**
	 * Write into the content of the {@link FullHttpResponse} from a {@link File}
	 *
	 * @param content {@link File} which is holding the content
	 *
	 * @throws IOException An I/O error occurs
	 */
	public final ResponseBuilder writeContent(final File content) throws IOException {
		return this.writeContent(Files.readAllBytes(content.toPath()));
	}

	/**
	 * Write into the content of the {@link FullHttpResponse} from a {@link Path}
	 *
	 * @param content {@link Path} which is holding the content
	 *
	 * @throws IOException An I/O error occurs
	 */
	public final ResponseBuilder writeContent(final Path content) throws IOException {
		return this.writeContent(Files.readAllBytes(content));
	}

	/**
	 * @return The fully configured {@link FullHttpResponse} entity
	 */
	public final FullHttpResponse getResponse() {
		return this.response;
	}

}
