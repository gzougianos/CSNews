package cs.news.swing;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import cs.news.teachers.Teacher;
import cs.news.teachers.TeacherManager;
import cs.news.util.OpenLinkActionListener;

public class TeachersPopUpMenu extends PopupMenu {
	private static final long serialVersionUID = 7395061034799793743L;

	public TeachersPopUpMenu() {
		super("’νοιγμα Ιστοθεσίας Καθηγητή");
		for (Teacher t : TeacherManager.teachers) {
			MenuItem item = new MenuItem(t.getName() + " (" + t.getEmail() + ")");
			item.addActionListener(new OpenLinkActionListener(t.getWebsite()));
			add(item);
		}
	}

}
