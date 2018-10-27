package app.open.software.rest.type;

/**
 * Enum of the encoding type of the requested or responded content
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public enum EncodingType {

	JSON("application/json; charset=utf-8"),
	UNKNOWN("text/plain; charset=utf-8");

	/**
	 * Header value to identify the type of the content
	 */
	private final String header;

	EncodingType(final String header) {
		this.header = header;
	}
	
	public final String getHeader() {
		return this.header;
	}

}
