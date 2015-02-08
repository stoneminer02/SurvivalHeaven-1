/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
