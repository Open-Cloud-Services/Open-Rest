package app.open.software.rest.parameter;

/**
 * {@link Parameter} entity
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class Parameter {

	/**
	 * Name and value of the {@link Parameter}
	 */
	private final String name, value;

	public Parameter(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public final String getName() {
		return this.name;
	}

	public final String getValue() {
		return this.value;
	}

}
