package app.open.software.rest.path;

import app.open.software.rest.parameter.Parameter;
import app.open.software.rest.type.TypeHandler;
import com.google.common.base.Preconditions;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.*;

public class PathHandler {

	private final TypeHandler typeHandler = new TypeHandler();

	public final String handlePath(final FullHttpRequest request) {
		Preconditions.checkNotNull(request.uri());

		//TODO compare to standard uri (.../api/v1/)

		var path = request.uri();

		if (path.contains("?")) {
			//TODO decode query
		}

		path = path.split("\\?")[0];

		if (path.endsWith("/") && path.length() > 1) {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	private List<Parameter> decodeQuery(final String query) {
		final var list = new ArrayList<Parameter>();
		Arrays.stream(query.split("&"))
				.map(s -> s.split("="))
				.filter(strings -> strings.length == 2)
				.forEach(parameter -> list.add(new Parameter(parameter[0], parameter[1])));
		return list;
	}

}
