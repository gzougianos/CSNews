package cs.news.swing;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LinksPopUpMenu extends PopupMenu {
	private static final long serialVersionUID = -4769635746269221841L;

	public LinksPopUpMenu() {
		super("’νοιγμα Ιστοθεσίας");
		for (Link listener : Link.class.getEnumConstants()) {
			MenuItem link = new MenuItem(listener.getName());
			link.addActionListener(listener);
			add(link);
		}
	}

	private enum Link implements ActionListener {
		//@formatter:off
		HOMEPAGE("Αρχική (cs.uoi.gr)","http://cs.uoi.gr/"),
		ECOURSE("E-Course (ecourse.uoi.gr)","http://ecourse.uoi.gr/"),
		EUDOXUS("Εύδοξος (eudoxus.gr)","https://service.eudoxus.gr/student/"),
		CHRONOS("Chronos (cronos.cc.uoi.gr)","https://cronos.cc.uoi.gr/unistudent/"),
		STACKOVERFLOW("Stack Overflow (stackoverflow.com)","https://stackoverflow.com/");
		//@formatter:on
		private String name, link;

		private Link(String name, String link) {
			this.name = name;
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

		public String getName() {
			return name;
		}
	}
}
