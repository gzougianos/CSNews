package cs.news.util;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cs.news.model.Announce;

public class OpenLinkActionListener implements ActionListener {
	private String link;

	public OpenLinkActionListener(String link) {
		this.link = link;
	}

	public OpenLinkActionListener(Announce announce) {
		if (Options.OPEN_ANNOUNCEMENTS_IN_PDF.toBoolean() && announce.getPDFLink() != null) {
			this.link = announce.getPDFLink();
		} else {
			this.link = WebUtils.ANNOUNCEMENT_URL_PREFIX + announce.getId();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Desktop.getDesktop().browse(new URI(link));
			//Runtime.getRuntime().exec(new String[] { "cmd", "/c", ("start chrome " + "\"" + link + "\"") });
		} catch (IOException | URISyntaxException exc) {
			exc.printStackTrace();
		}
	}
}
