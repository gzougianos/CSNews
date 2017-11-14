package cs.news;

import java.io.IOException;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.announce.Announce;
import cs.news.announce.AnnounceManager;
import cs.news.swing.TrayIcon;
import cs.news.util.Options;
import cs.news.util.WebUtils;

public class ReadAnnouncesTask extends TimerTask {
	private static final String NEWS_LIST_MAINPAGE = "http://cs.uoi.gr/index.php?menu=m5&page=";

	@Override
	public void run() {
		TrayIcon.getInstance().showSyncImage();
		int pageNumber = 1;
		int extracts = 0;
		int announcesRead = 0;
		try {
			while (announcesRead < Options.ANNOUNCES_MAX_NUMBER.toInt()) {
				final String currentPageLink = NEWS_LIST_MAINPAGE + pageNumber;
				Document document = Jsoup.connect(currentPageLink).get();
				Elements divs = document.getElementsByClass("newPaging");
				for (Element div : divs) {
					Element hyperLink = div.select("a").first();
					boolean color = hyperLink.select("font").first() != null;
					String date = div.select("h3").first().text().split(" - ")[0];
					String title = hyperLink.text();
					int id = Integer.parseInt(hyperLink.attr("href").split("id=")[1]);
					String pdf = WebUtils.FetchPDFLink(id);
					Announce a = new Announce(date, title, id, false, color, pdf);
					announcesRead++;
					if (announcesRead > Options.ANNOUNCES_MAX_NUMBER.toInt())
						break;
					if (!AnnounceManager.announceAlreadyExists(a)) {
						extracts++;
						int size = AnnounceManager.announces.size();
						if (size >= Options.ANNOUNCES_MAX_NUMBER.toInt())
							AnnounceManager.announces.add(0, a); // To the top of the stack
						else
							AnnounceManager.announces.add(a);
					}
				}
				pageNumber++;//next Page

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			AnnounceManager.removeReadAnnounces();
			AnnounceManager.saveAnnounces();
			if (extracts > 0) {
				TrayIcon.getInstance().showMessage("CS News",
						extracts + (extracts == 1 ? " νέα ανακοίνωση!" : " νέες ανακοινώσεις!"), true);
			}
			TrayIcon.getInstance().reBuild();
		}
	}
}
