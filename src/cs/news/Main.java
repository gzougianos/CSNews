package cs.news;

import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import cs.news.swing.TrayIcon;
import cs.news.tasks.Timer;
import cs.news.util.BatchWriter;

public class Main {
	public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);

	public static void main(String arguments[]) throws IOException {
		checkOSCompatibility();
		AnnounceManager.loadAnnounces();
		TrayIcon.getInstance();
		showHelloMessage(arguments);
		new Timer();
		if (PREFERENCES.getBoolean("WINDOWSSTARTUP", true))
			BatchWriter.writeBatch();
	}

	private static void checkOSCompatibility() {
		String OS = System.getProperty("os.name");
		if (!OS.toLowerCase().contains("windows")) {
			JOptionPane.showMessageDialog(null, "This program is not compatible with your Operating System",
					"OS Failure", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private static void showHelloMessage(String[] arguments) {
		//Check if runs in silent mode
		boolean showMessage = true;
		for (String s : arguments) {
			if (s.contains("sil"))
				showMessage = false;
		}
		if (showMessage)
			TrayIcon.getInstance()
					.showMessage("Κατάσταση λειτουργίας Tray",
							"Για κάθε νέα ανακοίνωση της ιστοσελίδας (www.cs.uoi.gr) "
									+ "θα λάβετε άμεση ειδοποιήση και θα προστεθεί στη λίστα \"Νέες Ανακοινώσεις\".",
							false);
	}
}
