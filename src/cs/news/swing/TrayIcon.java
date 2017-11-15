package cs.news.swing;

import static cs.news.util.WebUtils.AvailableInternetConnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cs.news.datamanagers.AnnounceManager;

public class TrayIcon extends java.awt.TrayIcon {
	private static final String TRAY_NAME = "CSE News";

	public TrayIcon() {
		super(createImage(""));
		setImageAutoSize(true);
		addToSystem();
		reBuild();
	}

	public void reBuild() {
		setPopupMenu(new TrayPopUp());
		showSyncImage();
		if (!AvailableInternetConnection()) {
			setToolTip("Δεν υπάρχει σύνδεση στο Internet.");
			setImage(createImage("--"));
		} else {
			int unread = AnnounceManager.getInstance().getNumOfUnreadAnnounces();
			setToolTip(TRAY_NAME + "\n" + +unread + (unread == 1 ? " Νέα Ανακοίνωση" : " Νέες Ανακοινώσεις"));
			setImage(createImage(unread == 0 ? "" : String.valueOf(unread)));
		}
	}

	public void showSyncImage() {
		setImage(createImage("*"));
		setToolTip("Συγχρονισμός Ανακοινώσεων...");
	}

	private static Image createImage(String writtenText) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(TrayIcon.class.getResourceAsStream("/cs/news/resources/favicon.png"));
			Graphics g = img.getGraphics();
			g.setFont(new Font("Arial", Font.PLAIN | Font.BOLD, 452));
			g.setColor(Color.ORANGE);
			g.drawString(writtenText, -10, 380);
			g.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void showMessage(String title, String message, boolean isWarning) {
		displayMessage(title, message, isWarning ? TrayIcon.MessageType.WARNING : TrayIcon.MessageType.INFO);
	}

	private void addToSystem() {
		try {
			if (SystemTray.isSupported()) {
				SystemTray traySystem = SystemTray.getSystemTray();
				traySystem.add(this);
			}
		} catch (Exception e) {
			System.out.println("Error adding the tray.");
		}
	}

	public static TrayIcon getInstance() {
		return SingletonHolder._instance;
	}

	private static class SingletonHolder {
		protected static final TrayIcon _instance = new TrayIcon();
	}
}
