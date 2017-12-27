package cs.news.util;

import java.util.prefs.Preferences;

public enum Options {
	//@formatter:off
	MANUAL_SYNC_MENU(false),
	MAX_CHARACTERS_ANNOUNCE_MENU_ITEM(70),
	REMIND_ANNOUNCES_TIME(120),
	SYNC_ANNOUNCES_TIME(30),
	OPEN_ANNOUNCEMENTS_IN_PDF(true),
	ANNOUNCES_MAX_NUMBER(10),
	INDEX_OF_LAST_TEACHER_VISITED(-1),
	LAST_TIME_TEACHERS_SYNC(0), //The time of the last synchronize teachers
	WINDOWS_STARTUP(true); //Run app with windows
	//@formatter:on
	private String key;

	private Object defaultValue;

	private Options(Object defaultValue) {
		this.key = this.toString();
		this.defaultValue = defaultValue;
	}

	public boolean toBoolean() {
		return Boolean.parseBoolean(reloadValue());
	}

	public long toLong() {
		return Long.parseLong(reloadValue());
	}

	public int toInt() {
		return Integer.parseInt(reloadValue());
	}

	public String reloadValue() {
		return PrefsHolder.PREFERENCES.get(key, String.valueOf(defaultValue));
	}

	public boolean update(Object newValue) {
		String oldVal = PrefsHolder.PREFERENCES.get(key, String.valueOf(defaultValue));
		String newVal = String.valueOf(newValue);
		if (newVal.equals(oldVal))
			return false; // Value did not change
		PrefsHolder.PREFERENCES.put(key, newVal);
		return true;
	}

	private static final class PrefsHolder {
		private static final Preferences PREFERENCES = Preferences.userNodeForPackage(PrefsHolder.class);
	}

}
