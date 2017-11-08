package cs.news;

import java.io.Serializable;

public class Announce implements Serializable {
	private static final long serialVersionUID = -4337075888035939207L;
	private String date;
	private String title;
	private int id;
	private boolean read;
	private boolean colorized;
	private String PDFLink;

	public Announce(String date, String title, int id, boolean read, boolean colorized, String PDFLink) {
		this.date = date;
		this.title = title;
		this.id = id;
		this.read = read;
		this.colorized = colorized;
		this.PDFLink = PDFLink;
	}

	public String getPDFLink() {
		return PDFLink;
	}

	public String getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isColorized() {
		return this.colorized;
	}

	public String toString() {
		return date + " , " + title + " , " + id;
	}

	public void print() {
		System.out.println(this.toString());
	}
}
