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
	 * Main version url
	 */
	private static String versionUrl = "/api/v%version%";

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
	 * Set a new format for the version url for example if you have api as subdomain
	 *
	 * @param versionUrl New version url
	 */
	public static void setVersionUrl(final String versionUrl) {
		ApiVersion.versionUrl = versionUrl;
	}

	/**
	 * @return The base url start from the version
	 */
	public final String getVersionUri() {
		return versionUrl.replace("%version%", String.valueOf(this.version));
	}

	public final RequestHandlerProvider getProvider() {
		return this.provider;
	}

}
