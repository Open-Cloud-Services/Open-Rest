package app.open.software.rest;

import app.open.software.rest.handler.RequestHandler;
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
import java.net.URI;

/**
 * {@link WebServer} to provide the information with rest
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class WebServer {

	/**
	 * {@link Gson} constant to serialize json text
	 */
	public static final Gson GSON = new GsonBuilder().create();

	/**
	 * {@link Epoll} constant to check if epoll is available
	 */
	private final boolean EPOLL = Epoll.isAvailable();

	/**
	 * Defines the port of the {@link WebServer}
	 */
	private final int port;

	/**
	 * {@link Router} ro route the {@link URI} to the right {@link RequestHandler}
	 */
	private final Router router;

	/**
	 * {@link EventLoopGroup}s for the netty server
	 */
	private EventLoopGroup bossGroup, workerGroup;

	public WebServer(final int port, final Router router) {
		this.port = port;
		this.router = router;

		this.bossGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
	}

	/**
	 * Starts the {@link WebServer}
	 */
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

	/**
	 * Stops the {@link WebServer}
	 */
	public void stop() {
		this.bossGroup.shutdownGracefully();
		this.workerGroup.shutdownGracefully();
	}

}
