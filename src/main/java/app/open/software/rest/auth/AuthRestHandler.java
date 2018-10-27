package app.open.software.rest.auth;

import app.open.software.rest.response.ResponseBuilder;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class AuthRestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final AuthListener listener;

	public AuthRestHandler(final AuthListener listener) {
		this.listener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest request) {
		if (!listener.allowed(request)) {
			final FullHttpResponse response = new ResponseBuilder(request, HttpResponseStatus.FORBIDDEN).getResponse();
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			ctx.close();
		}
	}

}
