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
 * The Interface L0lTask.
 */
public interface L0lTask {

	/**
	 * Should continue.
	 *
	 * @param loopNum
	 *            the loop num
	 * @return true, if successful
	 */
	public boolean shouldContinue(int loopNum);

	/**
	 * Task to do.
	 *
	 * @param loopNum
	 *            the loop num
	 */
	public void taskToDo(int loopNum);
}
