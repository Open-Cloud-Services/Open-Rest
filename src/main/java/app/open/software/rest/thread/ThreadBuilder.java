package app.open.software.rest.thread;

public class ThreadBuilder {

	private final Thread thread;

	public ThreadBuilder(final String name, final Runnable runnable) {
		this.thread = new Thread(runnable, name);
	}

	public ThreadBuilder setDaemon() {
		this.thread.setDaemon(true);
		return this;
	}

	public final Thread startAndGet() {
		this.thread.start();
		return this.thread;
	}

	public void start() {
		this.thread.start();
	}

}
