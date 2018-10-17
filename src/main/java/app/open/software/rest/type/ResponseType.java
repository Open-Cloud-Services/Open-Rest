package app.open.software.rest.type;

public enum ResponseType {

	JSON("application/json; charset=utf-8"),
	HTML("text/html; charset=utf-8"),
	UNKNOWN("text/plain; charset=utf-8");

	private final String header;

	ResponseType(final String header) {
		this.header = header;
	}

	public final String getHeader() {
		return this.header;
	}

}
