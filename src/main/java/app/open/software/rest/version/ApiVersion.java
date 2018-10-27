package app.open.software.rest.version;

import app.open.software.rest.handler.RequestHandler;
import app.open.software.rest.handler.RequestHandlerProvider;

/**
 * {@link ApiVersion} entity to create more than one version without crashing the other
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class ApiVersion {

	/**
	 * Version id
	 */
	private final int version;

	/**
	 * Specific {@link RequestHandlerProvider} which holds all {@link RequestHandler} from this version
	 */
	private final RequestHandlerProvider provider;

	public ApiVersion(final int version, final RequestHandlerProvider provider) {
		this.version = version;
		this.provider = provider;
	}

	/**
	 * @return The base url start from the version
	 */
	public final String getVersionUri() {
		return "/api/v" + this.version;
	}

	public final RequestHandlerProvider getProvider() {
		return this.provider;
	}

}
