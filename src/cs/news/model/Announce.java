package cs.news.model;

import java.io.Serializable;
import java.util.Calendar;

public class Announce implements Serializable, Comparable<Announce> {
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

	@Override
	public int compareTo(Announce announce) {
		String[] date1 = this.getDate().split(",")[1].trim().split("/");
		String[] date2 = announce.getDate().split(",")[1].trim().split("/");
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[0]));
		c1.set(Calendar.MONTH, Integer.parseInt(date1[1]));
		c1.set(Calendar.YEAR, Integer.parseInt(date1[2]));
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date2[0]));
		c2.set(Calendar.MONTH, Integer.parseInt(date2[1]));
		c2.set(Calendar.YEAR, Integer.parseInt(date2[2]));
		return (int) (c2.getTimeInMillis() - c1.getTimeInMillis());
	}
}
