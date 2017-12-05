package cs.news;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import cs.news.datamanagers.AnnounceManager;
import cs.news.datamanagers.TeacherManager;
import cs.news.swing.TrayIcon;
import cs.news.util.BatchWriter;
import cs.news.util.Debugger;

public class Main {
	private static final String SILENT_MODE_ARGUMENT = "/sil";
	private static final String ADMIN_MODE_ARGUMENT = "/adm";
	private static List<String> arguments;

	public static void main(String args[]) {
		checkOSCompatibility();
		SetUpUIManager();
		arguments = Arrays.asList(args);
		if (!arguments.contains(SILENT_MODE_ARGUMENT) && !arguments.contains(SILENT_MODE_ARGUMENT.toUpperCase())) //Silent Mode
			showHelloMessage();
		if (arguments.contains(ADMIN_MODE_ARGUMENT) || arguments.contains(ADMIN_MODE_ARGUMENT.toUpperCase())) //Admin mode
			Debugger.enable();
		AnnounceManager.getInstance();
		TeacherManager.getInstance();
		TrayIcon.getInstance();
		new Timer();
		BatchWriter.handleState();
	}

	private static void showHelloMessage() {
		TrayIcon.getInstance().showMessage("Κατάσταση λειτουργίας Tray",
				"Για κάθε νέα ανακοίνωση της ιστοσελίδας (www.cs.uoi.gr) " + "θα λάβετε άμεση ειδοποιήση και θα προστεθεί στη λίστα \"Τελευταίες Ανακοινώσεις\".", false);
	}

	private static void checkOSCompatibility() {
		String OS = System.getProperty("os.name");
		if (!OS.toLowerCase().contains("windows")) {
			JOptionPane.showMessageDialog(null, "This program is not compatible with your Operating System", "OS Failure", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private static void SetUpUIManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
