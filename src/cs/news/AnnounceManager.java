package cs.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AnnounceManager {
	public static AnnounceMap announces = new AnnounceMap();
	public static final int MAX_ANNOUNCEMENTS = 40;
	private static final String PROPERTIES_LOCATION = System.getenv("APPDATA") + "\\CsNewsAnnounceData.tmp";
	private static final String ANNOUNCEMENTS_URL = "\"http://cs.uoi.gr/index.php?menu=m58&id=";

	public static void openAnnouncement(Announce a) {
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", ("start chrome " + ANNOUNCEMENTS_URL + a.getId()) });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getUnreadAnnounces() {
		int c = 0;
		for (Announce a : announces.values())
			if (!a.isRead())
				c++;
		return c;
	}

	public static void loadAnnounces() {
		try {
			File f = new File(PROPERTIES_LOCATION);
			if (!f.exists()) {
				//Create the folder
				f.getParentFile().mkdir();
				return;
			}
			FileInputStream fis;
			fis = new FileInputStream(PROPERTIES_LOCATION);
			ObjectInputStream ois = new ObjectInputStream(fis);
			announces = (AnnounceMap) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public static void saveAnnounces() {
		try {
			FileOutputStream fos = new FileOutputStream(PROPERTIES_LOCATION);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(announces);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't update announces.");
		}
	}
}
