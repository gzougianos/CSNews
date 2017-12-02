package cs.news.util;

import java.awt.TrayIcon.MessageType;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import cs.news.swing.TrayIcon;

public class Debugger {
	private static boolean ENABLED = false;

	public static void showException(Exception e, Class<?> clazz, String description, boolean onTray) {
		if (!ENABLED)
			return;
		if (onTray) {
			TrayIcon.getInstance().displayMessage(clazz.getSimpleName() + ": " + description, getStackTraceAsString(e), MessageType.ERROR);
		} else {
			String stackTrace = getStackTraceAsString(e);
			String redColor = "<html><font color=\"FF0000\"><b>";
			String windowTitle = clazz.getName() + ": ";
			windowTitle += description == null ? "No error description." : description;
			JOptionPane.showMessageDialog(null, redColor + stackTrace, windowTitle, JOptionPane.PLAIN_MESSAGE);
		}
	}

	public static void showException(Exception e, Class<?> clazz, boolean onTray) {
		showException(e, clazz, null, onTray);
	}

	public static void debug(String message, Class<?> clazz) {
		if (!ENABLED)
			return;
		TrayIcon.getInstance().displayMessage(clazz.getSimpleName() + " Debug", message, MessageType.ERROR);
	}

	public static void enable() {
		System.out.println("Admin Mode - Exception Handling enabled.");
		ENABLED = true;
		debug("Admin Mode - Exception Handling enabled.", Debugger.class);
	}

	private static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
