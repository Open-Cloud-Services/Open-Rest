package app.open.software.rest;

import app.open.software.rest.route.Router;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class RestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final Router router;

	RestHandler(final Router router) {
		this.router = router;
	}

	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest request) throws Exception {
		ctx.writeAndFlush(this.router.createResponse(request)).addListener(ChannelFutureListener.CLOSE);
	}

}
