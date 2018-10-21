package app.open.software.rest.type;

public enum EncodingType {

	JSON("application/json; charset=utf-8"),
	UNKNOWN("text/plain; charset=utf-8");

	private final String header;

	EncodingType(final String header) {
		this.header = header;
	}

	public final String getHeader() {
		return this.header;
	}

}
