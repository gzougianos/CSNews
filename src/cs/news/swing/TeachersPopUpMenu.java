package cs.news.swing;

import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs.news.Main;
import cs.news.teachers.Teacher;
import cs.news.teachers.TeacherManager;
import cs.news.util.OpenLinkActionListener;

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
					Main.PREFERENCES.putInt("LASTTEACHERVISITED", index);
					TrayIcon.getInstance().reBuild();
				}
			});
			//Last teacher visited has bold font
			if (i == Main.PREFERENCES.getInt("LASTTEACHERVISITED", -1)) {
				item.setFont(new Font(null, Font.BOLD, 12));
			}
			add(item);
		}
	}

}
