package cs.news.swing;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import cs.news.util.OpenLinkActionListener;

public class LinksPopUpMenu extends PopupMenu {
	private static final long serialVersionUID = -4769635746269221841L;

	public LinksPopUpMenu() {
		super("’νοιγμα Ιστοθεσίας");
		for (Link link : Link.class.getEnumConstants()) {
			MenuItem item = new MenuItem(link.getName());
			item.addActionListener(new OpenLinkActionListener(link.getLink()));
			add(item);
		}
	}

	private enum Link {
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

		public String getLink() {
			return link;
		}

		public String getName() {
			return name;
		}
	}
}
