package app.open.software.rest.version;

import app.open.software.rest.handler.RequestHandlerProvider;

public class ApiVersion {

	private final int version;

	private final RequestHandlerProvider provider;

	public ApiVersion(final int version, final RequestHandlerProvider provider) {
		this.version = version;
		this.provider = provider;
	}

	public final String getVersionUri() {
		return "/api/v" + this.version;
	}

	public final RequestHandlerProvider getProvider() {
		return this.provider;
	}

}
