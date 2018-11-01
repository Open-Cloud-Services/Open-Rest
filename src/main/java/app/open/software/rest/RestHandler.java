package app.open.software.rest;

import app.open.software.rest.auth.AuthListener;
import app.open.software.rest.handler.RequestHandler;
import app.open.software.rest.response.ResponseBuilder;
import app.open.software.rest.route.Router;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

/**
 * Handler for netty to create and send the response
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class RestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	/**
	 * {@link Router} to direct to thr right {@link RequestHandler}
	 */
	private final Router router;

	/**
	 * {@link AuthListener} to check the permissions
	 */
	private final AuthListener listener;

	RestHandler(final Router router, final AuthListener listener) {
		this.router = router;
		this.listener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest request) throws Exception {
		if (this.listener.allowed(request)) {
			ctx.writeAndFlush(this.router.createResponse(request)).addListener(ChannelFutureListener.CLOSE);
		} else {
			final FullHttpResponse response = new ResponseBuilder(request, HttpResponseStatus.FORBIDDEN).getResponse();
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}

		ctx.close();
	}

	/**
	 * {@inheritDoc}
	 */
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
	}
}
