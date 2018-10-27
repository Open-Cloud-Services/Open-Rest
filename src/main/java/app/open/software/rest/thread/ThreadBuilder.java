package app.open.software.rest.thread;

/**
 * Util to build {@link Thread}s easier
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class ThreadBuilder {

	/**
	 * Thread entity to configure
	 */
	private final Thread thread;

	public ThreadBuilder(final String name, final Runnable runnable) {
		this.thread = new Thread(runnable, name);
	}

	/**
	 * Configure the {@link Thread} entity as a daemon
	 */
	public ThreadBuilder setDaemon() {
		this.thread.setDaemon(true);
		return this;
	}

	/**
	 * Start the {@link Thread}
	 *
	 * @return {@link Thread} entity
	 */
	public final Thread startAndGet() {
		this.thread.start();
		return this.thread;
	}

	/**
	 * Start the {@link Thread}
	 */
	public void start() {
		this.thread.start();
	}

}
