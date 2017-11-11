package cs.news;

import java.util.concurrent.TimeUnit;

import cs.news.util.Options;

public class Timer extends java.util.Timer {
	private static final long ANNOUNCES_READ_TASK_INITIAL = 1; //1 Second
	private static Timer instance;

	public Timer() {
		schedule(new ReadAnnouncesTask(), ANNOUNCES_READ_TASK_INITIAL * 1000,
				TimeUnit.MINUTES.toMillis(Options.SYNC_ANNOUNCES_TIME.toInt()));

		long remindTime = TimeUnit.MINUTES.toMillis(Options.REMIND_ANNOUNCES_TIME.toInt());
		if (remindTime > 0)//If 0, reminder is disabled.
			schedule(new RemindTask(), remindTime / 2, remindTime); //Initial time is the half.

		instance = this;
	}

	public static void restart() {
		instance.cancel();
		new Timer();
	}
}
