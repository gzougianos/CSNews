package cs.news.teachers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.Main;

public class TeacherManager {
	private static final int REFRESH_DAYS = 60; //Every how many days refresh teachers list.
	private static final String PROPERTIES_LOCATION = System.getenv("APPDATA") + "\\CsNewsTeacherData.tmp";
	public static ArrayList<Teacher> teachers = new ArrayList<>();

	public static void SyncTeachers() {
		long lastTimeRead = Main.PREFERENCES.getLong("TEACHERSREADTIME", 0);
		boolean daysPassed = TimeUnit.DAYS.toMillis(REFRESH_DAYS) + lastTimeRead < System.currentTimeMillis();
		boolean fileExists = new File(PROPERTIES_LOCATION).exists();
		if (fileExists && !daysPassed)
			return;//There is no reason to sync yet.
		System.out.println("yncing teachers");
		try {
			teachers.clear();//clear all teachers
			Document doc = Jsoup.connect("http://cs.uoi.gr/index.php?menu=m4").get();
			Element content = doc.getElementsByClass("content").get(0);
			Elements teacherElements = content.getElementsByClass("name");
			for (Element teacherElement : teacherElements) {
				String teacherName = teacherElement.select("a").text().toString();
				String tempLink = "http://cs.uoi.gr/" + teacherElement.select("a").attr("href");
				String teachersLink = getLinkFromHome(tempLink);
				String email = getEmailFromHome(tempLink);
				email = email.trim();
				Teacher teacher = new Teacher(teacherName, teachersLink, email);
				teachers.add(teacher);
			}
			Main.PREFERENCES.putLong("TEACHERSREADTIME", System.currentTimeMillis()); //save the time
			saveTeachers();
		} catch (Exception e) {
			e.printStackTrace();
			//	return;
		}

	}

	private static String getEmailFromHome(String homeLink) throws IOException {
		Document doc = Jsoup.connect(homeLink).get();
		Elements dataTexts = doc.getElementsByClass("dataText");
		for (Element element : dataTexts) {
			if (element.text().contains("(at)")) {
				return element.text().replaceAll(" \\(at\\) ", "@");
			}
		}
		return null;
	}

	private static String getLinkFromHome(String homeLink) throws IOException {
		Document doc = Jsoup.connect(homeLink).get();
		Elements dataTexts = doc.getElementsByClass("dataText");
		for (Element element : dataTexts) {
			if (element.select("a") != null && !element.select("a").toString().equals("")) {
				return element.select("a").attr("href").toString();
			}
		}
		return null;
	}

	public static void Initialize() {
		loadTeachers();
		SyncTeachers();
	}

	@SuppressWarnings("unchecked")
	private static void loadTeachers() {
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
			teachers = (ArrayList<Teacher>) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	private static void saveTeachers() {
		try {
			FileOutputStream fos = new FileOutputStream(PROPERTIES_LOCATION);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(teachers);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't save teachers.");
		}
	}
}
