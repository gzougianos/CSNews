package cs.news;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import cs.news.announce.AnnounceManager;
import cs.news.swing.TrayIcon;
import cs.news.teachers.TeacherManager;
import cs.news.util.BatchWriter;

public class Main {

	public static void main(String arguments[]) throws IOException {
		checkOSCompatibility();
		SetUpUIManager();
		AnnounceManager.loadAnnounces();
		TeacherManager.Initialize();
		TrayIcon.getInstance();
		showHelloMessage(arguments);
		new Timer();
		BatchWriter.handleState();
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
			TrayIcon.getInstance().showMessage("Κατάσταση λειτουργίας Tray",
					"Για κάθε νέα ανακοίνωση της ιστοσελίδας (www.cs.uoi.gr) "
							+ "θα λάβετε άμεση ειδοποιήση και θα προστεθεί στη λίστα \"Τελευταίες Ανακοινώσεις\".",
					false);
	}

	private static void SetUpUIManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
