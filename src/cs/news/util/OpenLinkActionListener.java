package cs.news.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OpenLinkActionListener implements ActionListener {
	private String link;

	public OpenLinkActionListener(String link) {
		this.link = link;
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
