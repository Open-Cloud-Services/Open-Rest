package app.open.software.rest;

import app.open.software.rest.route.Router;
import app.open.software.rest.thread.ThreadBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebServer {

	public final static Gson GSON = new GsonBuilder().create();

	public final static Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

	private final boolean EPOLL = Epoll.isAvailable();

	private final int port;

	private final Router router;

	private EventLoopGroup bossGroup, workerGroup;

	public WebServer(final int port, final Router router) {
		this.port = port;
		this.router = router;

		this.bossGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
	}

	public void start() {
		new ThreadBuilder("Web Server", () -> {

			new ServerBootstrap()
					.group(this.bossGroup, this.workerGroup)
					.channel(WebServer.this.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<>() {

						protected void initChannel(final Channel channel) {
							channel.pipeline().addLast(
									new HttpServerCodec(),
									new HttpObjectAggregator(Integer.MAX_VALUE),
									new RestHandler(WebServer.this.router));
						}

					}).bind(this.port).syncUninterruptibly();

		}).setDaemon().start();
	}

	public void stop() {
		this.bossGroup.shutdownGracefully();
		this.workerGroup.shutdownGracefully();
	}

}
