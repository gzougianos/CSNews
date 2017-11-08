package cs.news.swing;

import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs.news.teachers.Teacher;
import cs.news.teachers.TeacherManager;
import cs.news.util.OpenLinkActionListener;
import cs.news.util.Options;

public class TeachersPopUpMenu extends PopupMenu {
	private static final long serialVersionUID = 7395061034799793743L;

	public TeachersPopUpMenu() {
		super("’νοιγμα Ιστοθεσίας Καθηγητή");
		for (int i = 0; i < TeacherManager.teachers.size(); i++) {
			Teacher t = TeacherManager.teachers.get(i);
			final int index = i;
			MenuItem item = new MenuItem(t.getName() + " (" + t.getEmail() + ")");
			item.addActionListener(new OpenLinkActionListener(t.getWebsite()));
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//Update last teacher visited.
					Options.INDEX_OF_LAST_TEACHER_VISITED.update(index);
					TrayIcon.getInstance().reBuild();
				}
			});
			//Last teacher visited has bold font
			if (i == Options.INDEX_OF_LAST_TEACHER_VISITED.toInt()) {
				item.setFont(new Font(null, Font.BOLD, 12));
			}
			add(item);
		}
	}

}
