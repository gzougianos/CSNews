package cs.news.datamanagers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.util.Debugger;
import cs.news.util.WebUtils;

public class ScheduleManager extends DataManager {
	private String scheduleKey;
	private static final String FEEDING_SCHEDULES_MAINPAGE = "http://www.uoi.gr/tag/menou-lesxis/";

	protected ScheduleManager(String fileName, String scheduleKey) {
		super(fileName);
		this.scheduleKey = scheduleKey;
		parseWebData();
	}

	@Override
	public Object getData() {
		return null;
	}

	@Override
	protected final void parseData() {
		int pageNumber = 1;
		try {
			whileloop: while (true) {
				final String currentPageLink = WebUtils.NEWS_LIST_MAINPAGE + pageNumber;
				Document document = Jsoup.connect(currentPageLink).get();
				Elements divs = document.getElementsByClass("newPaging");
				for (Element div : divs) {
					Element hyperLink = div.select("a").first();
					String title = hyperLink.text();
					if (title.contains(this.scheduleKey)) {
						int id = Integer.parseInt(hyperLink.attr("href").split("id=")[1]);
						String pdf = WebUtils.FetchPDFLink(id);
						WebUtils.DownloadPDF(pdf, getDataFile());
						break whileloop;
					}
				}
				pageNumber++;//next Page
			}
		} catch (IOException e) {
			Debugger.showException(e, getClass(), "Error parsing schedule: " + this.scheduleKey, true);
		}
	}

	@Override
	protected final boolean parseWebDataCondition() {
		return true;
	}

	private static String getFeedingScheduleURL() {
		Document doc;
		try {
			doc = Jsoup.connect(FEEDING_SCHEDULES_MAINPAGE).get();
			Element mainElement = doc.getElementsByClass("blog-content wf-td").first();
			Elements hrefs = mainElement.getElementsByAttribute("href");
			for (Element href : hrefs) {
				Element actionElement = href.select("a").first();
				String titleAttr = actionElement.attr("title");
				String linkAttr = actionElement.attr("href");
				if (titleAttr.contains("Πρόγραμμα Σίτισης")) {
					doc = Jsoup.connect(linkAttr).get();
					mainElement = doc.getElementsByClass("entry-content").first();
					Elements hrefs2 = mainElement.getElementsByAttribute("href");
					for (Element href2 : hrefs2) {
						String linkAttr2 = href2.select("a").first().attr("href");
						if (linkAttr2.contains("Menu_Lesxis")) {
							return linkAttr2;
						}
					}
				}
			}
		} catch (IOException e) {
			Debugger.showException(e, ScheduleManager.class, "Error getting feeding schedule URL.", true);
		}
		return null;
	}

	public static void openFeedingSchedule() throws IOException {
		File f = new File(DataManager.HOME_DIRECTORY + "//lesxi.pdf");
		WebUtils.DownloadPDF(getFeedingScheduleURL(), f);//Sync schedule before open
		if (f.exists())
			Desktop.getDesktop().open(f);
		else
			throw new IOException();
	}

	public enum Schedule {
		//@formatter:off
		LECTURES("lectures.pdf","Πρόγραμμα Μαθημάτων"),
		LABS("labs.pdf","Πρόγραμμα Εργαστηρίων"),
		EXAMS("exams.pdf","Πρόγραμμα Εξεταστικής");
		//@formatter:on
		private String fileName, key;

		Schedule(String fileName, String announcementKey) {
			this.fileName = fileName;
			this.key = announcementKey;
		}

		public String getKey() {
			return key;
		}

		public void open() throws IOException {
			ScheduleManager sm = new ScheduleManager(fileName, key);
			if (sm.getDataFile().exists())
				Desktop.getDesktop().open(sm.getDataFile());
			else
				throw new IOException();
		}
	}

}
