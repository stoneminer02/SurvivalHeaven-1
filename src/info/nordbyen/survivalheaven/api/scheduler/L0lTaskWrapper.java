/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.scheduler;

/**
 * The Class L0lTaskWrapper.
 */
public final class L0lTaskWrapper {

	/** The task. */
	private L0lTask task;
	
	/** The delay. */
	private final int delay;
	
	/** The loops_done. */
	private int loops_done = 0;

	/**
	 * Instantiates a new l0l task wrapper.
	 *
	 * @param task
	 *            the task
	 * @param delay
	 *            the delay
	 */
	public L0lTaskWrapper(final L0lTask task, final int delay) {
		this.task = task;
		this.delay = delay;
	}

	/**
	 * Execute task.
	 */
	public void executeTask() {
		task.taskToDo(loops_done);
		loops_done++;
	}

	/**
	 * Gets the delay.
	 *
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public L0lTask getTask() {
		return task;
	}

	/**
	 * Checks if is done.
	 *
	 * @return true, if is done
	 */
	public boolean isDone() {
		return !task.shouldContinue(loops_done);
	}

	/**
	 * Null all.
	 */
	public void nullAll() {
		task = null;
	}
}
