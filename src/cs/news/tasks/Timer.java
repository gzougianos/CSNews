package cs.news.tasks;

public class Timer extends java.util.Timer {
	private static final long ANNOUNCES_READ_TASK_INITIAL = 1; //1 Second
	private static final long ANNOUNCES_READ_TASK_PERIOD = 30; //30 Minutes
	private static final long REMIND_TASK_INITIAL = 60; //60 Minutes
	private static final long REMIND_TASK_PERIOD = 120; //2 hours

	public Timer() {
		schedule(new ReadAnnouncesTask(), ANNOUNCES_READ_TASK_INITIAL * 1000, ANNOUNCES_READ_TASK_PERIOD * 60 * 1000);
		schedule(new RemindTask(), REMIND_TASK_INITIAL * 60 * 1000, REMIND_TASK_PERIOD * 60 * 1000);
	}
}
