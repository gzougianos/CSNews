package cs.news.tasks;

import java.util.TimerTask;
import static cs.news.util.InternetChecker.AvailableInternetConnection;

import cs.news.AnnounceManager;
import cs.news.swing.TrayIcon;

public class RemindTask extends TimerTask {

	@Override
	public void run() {
		int unreadAnnounces = AnnounceManager.getUnreadAnnounces();
		if (!AvailableInternetConnection()) {
			TrayIcon.getInstance().reBuild();
			return; // If no connection, don't remind anything.
		}
		if (unreadAnnounces <= 0)
			return;
		else if (unreadAnnounces == 1)
			TrayIcon.getInstance().showMessage("Υπενθύμιση", "Υπάρχει 1 Νέα Ανακοίνωση.", true);
		else
			TrayIcon.getInstance().showMessage("Υπενθύμιση", "Υπάρχουν " + unreadAnnounces + " Νέες Ανακοινώσεις.",
					true);
	}

}
