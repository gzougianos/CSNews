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

import cs.news.util.Options;

public class TeacherManager {
	private static final int REFRESH_DAYS = 60; //Every how many days refresh teachers list.
	private static final String PROPERTIES_LOCATION = System.getenv("APPDATA") + "\\CsNewsTeacherData.tmp";
	public static ArrayList<Teacher> teachers = new ArrayList<>();

	public static void refreshTeachersData() {
		long lastTimeRead = Options.LAST_TIME_TEACHERS_SYNC.toLong();
		boolean daysPassed = TimeUnit.DAYS.toMillis(REFRESH_DAYS) + lastTimeRead < System.currentTimeMillis();
		boolean fileExists = new File(PROPERTIES_LOCATION).exists();
		if (fileExists && !daysPassed)
			return;//There is no reason to sync yet.
		System.out.println("Synchronizing teachers list...");
		try {
			Document doc = Jsoup.connect("http://cs.uoi.gr/index.php?menu=m4").get();
			teachers.clear();//clear all teachers
			Element content = doc.getElementsByClass("content").get(0);
			Elements teacherElements = content.getElementsByClass("name");
			for (Element teacherElement : teacherElements) {
				String teacherName = teacherElement.select("a").text().toString();
				String teachersLinkCS = "http://cs.uoi.gr/" + teacherElement.select("a").attr("href");
				String[] linkAndEmail = getTeachersLinkAndEmail(teachersLinkCS);
				String teacherLink = linkAndEmail[0];
				String teacherEmail = linkAndEmail[1];
				teacherEmail = teacherEmail.trim();
				Teacher teacher = new Teacher(teacherName, teacherLink, teacherEmail);
				teachers.add(teacher);
			}
			Options.LAST_TIME_TEACHERS_SYNC.update(System.currentTimeMillis());
			saveTeachersData();
		} catch (Exception e) {
			e.printStackTrace();
			//	return;
		}

	}

	private static final String[] getTeachersLinkAndEmail(String teachersLink) throws IOException {
		String[] linkAndMail = new String[2];
		Document doc = Jsoup.connect(teachersLink).get();
		Elements dataTexts = doc.getElementsByClass("dataText");
		for (Element element : dataTexts) {
			if (element.select("a") != null && !element.select("a").toString().equals("")) {//Link
				linkAndMail[0] = element.select("a").attr("href").toString();
			}
			if (element.text().contains("(at)")) { //Email
				linkAndMail[1] = element.text().replaceAll(" \\(at\\) ", "@");
			}
		}
		return linkAndMail;
	}

	public static void Initialize() {
		loadTeachersData();
		refreshTeachersData();
	}

	@SuppressWarnings("unchecked")
	private static void loadTeachersData() {
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

	private static void saveTeachersData() {
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
