package app.open.software.rest.auth;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Auth listener to control the access
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public interface AuthListener {

	boolean allowed(final FullHttpRequest request);

}