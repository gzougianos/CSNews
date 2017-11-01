package cs.news;

import java.io.Serializable;

public class Announce implements Serializable {
	private static final long serialVersionUID = -4337075888035939207L;
	private String date;
	private String type;
	private String title;
	private int id;
	private boolean read;
	private boolean colorized;
	public Announce(String date, String type, String title, int id, boolean read,boolean colorized) {
		this.date = date;
		this.type = type;
		this.title = title;
		this.id = id;
		this.read = read;
		this.colorized = colorized;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isColorized()
	{
		return this.colorized;
	}
	public String toString() {
		return date + " , " + type + " , " + title + " , " + id;
	}

	public void print() {
		System.out.println(this.toString());
	}
}
