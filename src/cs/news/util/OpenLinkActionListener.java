package cs.news.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cs.news.announce.Announce;

public class OpenLinkActionListener implements ActionListener {
	private static final String ANNOUNCE_LINK_PREFIX = "\"http://cs.uoi.gr/index.php?menu=m58&id=";
	private static final String PDF_LINK_PREFIX = "cs.uoi.gr";
	private String link;

	public OpenLinkActionListener(String link) {
		this.link = link;
	}

	public OpenLinkActionListener(Announce announce) {
		if ((Options.OPEN_ANNOUNCEMENTS_IN_PDF.toBoolean()) && (announce.getPDFLink().length() > 1)) {
			this.link = PDF_LINK_PREFIX + announce.getPDFLink();
		} else {
			this.link = ANNOUNCE_LINK_PREFIX + announce.getId();
		}
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
