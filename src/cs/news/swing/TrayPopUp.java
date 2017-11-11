package cs.news.swing;

import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs.news.announce.Announce;
import cs.news.announce.AnnounceManager;
import cs.news.util.OpenLinkActionListener;
import cs.news.util.Options;

public class TrayPopUp extends PopupMenu {
	private static final long serialVersionUID = -1233037255379467666L;

	public TrayPopUp() {
		super();
		PopupMenu announcesMenu = new PopupMenu("Τελευταίες Ανακοινώσεις");
		announcesMenu.addSeparator();
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
			announcesMenu.add(m);
			announcesMenu.addSeparator();
		}
		MenuItem markAllAsReadMenu = new MenuItem("Μαρκάρισμα Ανακοινώσεων ως Αναγνωσμένες");
		markAllAsReadMenu.addActionListener(new ActionListener() {
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

		MenuItem exitMenu = new MenuItem("Έξοδος");
		exitMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnnounceManager.saveAnnounces();
				System.exit(0);
			}
		});
		MenuItem settingsMenu = new MenuItem("Ρυθμίσεις");
		settingsMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsPanel.open();
			}
		});
		add(new LinksPopUpMenu());
		add(new TeachersPopUpMenu());
		addSeparator();
		add(announcesMenu);
		add(markAllAsReadMenu);
		addSeparator();
		add(settingsMenu);
		add(exitMenu);
	}

	private static String adjustTitleLength(Announce a) {
		String title = a.isRead() ? a.getDate() : "* Νέα * " + a.getDate();
		title += " - " + a.getTitle();
		int maxChars = Options.MAX_CHARACTERS_ANNOUNCE_MENU_ITEM.toInt();
		if (title.length() > maxChars) {
			title = title.substring(0, Math.min(title.length(), maxChars));
			title += "...";
		}
		return title;
	}
}