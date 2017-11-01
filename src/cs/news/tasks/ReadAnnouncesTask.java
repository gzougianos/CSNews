package cs.news.tasks;

import java.io.IOException;
import java.util.Collections;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.Announce;
import cs.news.AnnounceManager;
import cs.news.swing.TrayIcon;

public class ReadAnnouncesTask extends TimerTask {

	@Override
	public void run() {
		int pageNumber = 1;
		int extracts = 0;
		int announcesRead = 0;
		while (announcesRead < AnnounceManager.MAX_ANNOUNCEMENTS) {
			Document document;
			try {
				document = Jsoup.connect("http://cs.uoi.gr/index.php?menu=m5&page=" + pageNumber).get();
				Elements divs = document.getElementsByClass("newPaging");
				for (Element div : divs) {
					boolean color = div.select("a").first().select("font").first() != null;
				//	if (color)
					//	System.out.println(div.text());
					String[] dateAndType = div.select("h3").first().text().split(" - ");
					String date = dateAndType[0];
					String type = dateAndType[1];
					String title = div.select("a").first().text();
					int id = Integer.parseInt(div.select("a").first().attr("href").split("id=")[1]);
					Announce a = new Announce(date, type, title, id, false, color);
					announcesRead++;
					if (announcesRead > AnnounceManager.MAX_ANNOUNCEMENTS)
						break;
					if (extractAnnounce(a))
						extracts++;
				}
				pageNumber++;//next Page
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		if (extracts > 0) {
			TrayIcon.getInstance().showMessage("CS News",
					extracts + (extracts == 1 ? " νέα ανακοίνωση!" : " νέες ανακοινώσεις!"), true);
		}
		TrayIcon.getInstance().reBuild();
	}

	private boolean extractAnnounce(Announce a) {
		if (AnnounceManager.announces.containsKey(a.getId()))
			return false;
		if (AnnounceManager.announces.size() == AnnounceManager.MAX_ANNOUNCEMENTS)
			AnnounceManager.announces.remove(Collections.min(AnnounceManager.announces.keySet())); //remove last announce
		AnnounceManager.announces.put(a.getId(), a);
		return true;
	}
}
