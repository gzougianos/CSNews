package cs.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AnnounceManager {
	public static Stack<Announce> announces = new Stack<>();
	public static final int MAX_ANNOUNCEMENTS = 10;
	private static final String PROPERTIES_LOCATION = System.getenv("APPDATA") + "\\CsNewsAnnounceData.tmp";

	public static int getNumOfUnreadAnnounces() {
		int c = 0;
		for (Announce a : announces)
			if (!a.isRead())
				c++;
		return c;
	}

	public static void removeReadAnnounces() {
		List<Announce> read = new ArrayList<>();
		for (int i = announces.size() - 1; i >= MAX_ANNOUNCEMENTS; i--) {
			Announce a = announces.get(i);
			if (a.isRead())
				read.add(a);
		}
		System.out.println(read.size() + " Announces have been removed.");
		announces.removeAll(read);
	}

	public static boolean announceAlreadyExists(Announce a) {
		for (Announce announce : announces) {
			if (announce.getId() == a.getId())
				return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
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
			announces = (Stack<Announce>) ois.readObject();
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
