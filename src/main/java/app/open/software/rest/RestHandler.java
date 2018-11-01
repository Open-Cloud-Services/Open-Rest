package app.open.software.rest;

import app.open.software.rest.handler.RequestHandler;
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

	RestHandler(final Router router) {
		this.router = router;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest request) throws Exception {
		ctx.writeAndFlush(this.router.createResponse(request)).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * {@inheritDoc}
	 */
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
	}
}
