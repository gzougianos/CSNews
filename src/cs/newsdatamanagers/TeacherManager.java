package cs.newsdatamanagers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.model.Teacher;
import cs.news.util.Options;

public class TeacherManager extends DataManager {
	private ArrayList<Teacher> teachers;

	@SuppressWarnings("unchecked")
	protected TeacherManager() {
		super("CsNewsTeacherData.tmp");
		teachers = (ArrayList<Teacher>) loadData();
		if (teachers == null)
			teachers = new ArrayList<>();
		refreshTeachersData();
	}

	private static final int REFRESH_DAYS = 60; //Every how many days refresh teachers list.
	private static final String TEACHER_LIST_MAINPAGE = "http://cs.uoi.gr/index.php?menu=m4";

	public void refreshTeachersData() {
		if (!readyToRefreshData())
			return;
		System.out.println("Synchronizing teachers list...");
		try {
			Document doc = Jsoup.connect(TEACHER_LIST_MAINPAGE).get();
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
			saveData();
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

	private boolean readyToRefreshData() {
		long lastTimeRead = Options.LAST_TIME_TEACHERS_SYNC.toLong();
		boolean daysPassed = TimeUnit.DAYS.toMillis(REFRESH_DAYS) + lastTimeRead < System.currentTimeMillis();
		return !dataFileExists() || daysPassed;
	}

	@Override
	public ArrayList<Teacher> getData() {
		return teachers;
	}

	public static final TeacherManager getInstance() {
		return SingletonHolder._instance;
	}

	private static final class SingletonHolder {
		protected static final TeacherManager _instance = new TeacherManager();
	}
}
