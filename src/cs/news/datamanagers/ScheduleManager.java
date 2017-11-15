package cs.news.datamanagers;

import java.awt.Desktop;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.util.WebUtils;

public class ScheduleManager extends DataManager {
	private String scheduleKey;

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
			System.out.println("Error parsing schedule: " + this.scheduleKey);
		}
	}

	@Override
	protected final boolean parseWebDataCondition() {
		return true;
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
