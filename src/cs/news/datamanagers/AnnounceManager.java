package cs.news.datamanagers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.model.Announce;
import cs.news.swing.TrayIcon;
import cs.news.util.Options;
import cs.news.util.WebUtils;

public class AnnounceManager extends DataManager {
	private Stack<Announce> announces;

	@SuppressWarnings("unchecked")
	protected AnnounceManager() {
		super("CsNewsAnnounceData.tmp");
		announces = (Stack<Announce>) loadData();
		if (announces == null)
			announces = new Stack<>();
	}

	public int getNumOfUnreadAnnounces() {
		int c = 0;
		for (Announce a : announces)
			if (!a.isRead())
				c++;
		return c;
	}

	public void removeReadAnnounces() {
		List<Announce> read = new ArrayList<>();
		for (int i = announces.size() - 1; i >= Options.ANNOUNCES_MAX_NUMBER.toInt(); i--) {
			Announce a = announces.get(i);
			if (a.isRead())
				read.add(a);
		}
		System.out.println(read.size() + " Announces have been removed.");
		announces.removeAll(read);
	}

	public boolean announceAlreadyExists(Announce a) {
		for (Announce announce : announces) {
			if (announce.getId() == a.getId())
				return true;
		}
		return false;
	}

	@Override
	protected boolean parseWebDataCondition() {
		return true; //No condition
	}

	@Override
	protected void parseData() {
		TrayIcon.getInstance().showSyncImage();
		int pageNumber = 1;
		int extracts = 0;
		int announcesRead = 0;
		try {
			while (announcesRead < Options.ANNOUNCES_MAX_NUMBER.toInt()) {
				final String currentPageLink = WebUtils.NEWS_LIST_MAINPAGE + pageNumber;
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
					if (!announceAlreadyExists(a)) {
						extracts++;
						int size = AnnounceManager.getInstance().getData().size();
						if (size >= Options.ANNOUNCES_MAX_NUMBER.toInt())
							announces.add(0, a); // To the top of the stack
						else
							announces.add(a);
					}
				}
				pageNumber++;//next Page
			}
		} catch (IOException e) {
			System.out.println("Error parsing announcements.");
		} finally {
			removeReadAnnounces();
			saveData();
			if (extracts > 0) {
				TrayIcon.getInstance().showMessage("CS News",
						extracts + (extracts == 1 ? " νέα ανακοίνωση!" : " νέες ανακοινώσεις!"), true);
			}
			TrayIcon.getInstance().reBuild();
		}
	}

	@Override
	public Stack<Announce> getData() {
		return announces;
	}

	public static final AnnounceManager getInstance() {
		return SingletonHolder._instance;
	}

	private static final class SingletonHolder {
		protected static final AnnounceManager _instance = new AnnounceManager();
	}

}
