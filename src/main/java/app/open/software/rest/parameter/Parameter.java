package app.open.software.rest.parameter;

public class Parameter {

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
