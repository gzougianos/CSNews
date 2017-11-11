package cs.news.swing;

import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs.news.announce.Announce;
import cs.news.announce.AnnounceManager;
import cs.news.util.OpenLinkActionListener;

public class TrayPopUp extends PopupMenu {
	private static final long serialVersionUID = -1233037255379467666L;
	private static final int MAX_CHARACTERS_IN_MENU_ITEMS = 70;

	public TrayPopUp() {
		super();
		PopupMenu news = new PopupMenu("Τελευταίες Ανακοινώσεις");
		news.addSeparator();
		for (Announce a : AnnounceManager.announces) {
			MenuItem m = new MenuItem(adjustTitleLength(a));
			m.addActionListener(new OpenLinkActionListener(a));
			m.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!a.isRead())
						a.setRead(true);
					TrayIcon.getInstance().reBuild();
				}
			});
			if (a.isColorized())
				m.setFont(new Font(null, Font.BOLD, 12));
			news.add(m);
			news.addSeparator();
		}
		MenuItem markarisma = new MenuItem("Μαρκάρισμα Ανακοινώσεων ως Αναγνωσμένες");
		markarisma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Announce a : AnnounceManager.announces) {
					a.setRead(true);
				}
				AnnounceManager.removeReadAnnounces();
				AnnounceManager.saveAnnounces();
				TrayIcon.getInstance().reBuild();
			}
		});

		MenuItem eksodos = new MenuItem("Έξοδος");
		eksodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnnounceManager.saveAnnounces();
				System.exit(0);
			}
		});
		MenuItem epiloges = new MenuItem("Ρυθμίσεις");
		epiloges.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsPanel.open();

			}
		});
		add(new LinksPopUpMenu());
		add(new TeachersPopUpMenu());
		addSeparator();
		add(news);
		add(markarisma);
		addSeparator();
		add(epiloges);
		add(eksodos);
	}

	private static String adjustTitleLength(Announce a) {
		String title = a.isRead() ? a.getDate() : "* Νέα * " + a.getDate();
		title += " - " + a.getTitle();
		if (title.length() > MAX_CHARACTERS_IN_MENU_ITEMS) {
			title = title.substring(0, Math.min(title.length(), MAX_CHARACTERS_IN_MENU_ITEMS));
			title += "...";
		}
		return title;
	}
}