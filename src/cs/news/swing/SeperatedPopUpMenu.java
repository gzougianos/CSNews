package cs.news.swing;

import java.awt.MenuItem;
import java.awt.PopupMenu;

public class SeperatedPopUpMenu extends PopupMenu {

	private static final long serialVersionUID = -3620163459616120978L;

	public SeperatedPopUpMenu(String title) {
		super(title);
		addSeparator();
	}

	public void addItem(MenuItem mi) {
		super.add(mi);
		addSeparator();
	}
}
