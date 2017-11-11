package cs.news.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cs.news.announce.Announce;

public class OpenLinkActionListener implements ActionListener {
	private static final String ANNOUNCEMENTS_URL = "\"http://cs.uoi.gr/index.php?menu=m58&id=";
	private String link;

	public OpenLinkActionListener(String link) {
		this.link = link;
	}

	public OpenLinkActionListener(Announce announce) {
		this.link = Options.OPEN_ANNOUNCEMENTS_IN_PDF.toBoolean() ? announce.getPDFLink()
				: ANNOUNCEMENTS_URL + announce.getId();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", ("start chrome " + link) });
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
}
