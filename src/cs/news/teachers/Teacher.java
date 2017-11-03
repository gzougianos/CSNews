package cs.news.teachers;

import java.io.Serializable;

public class Teacher implements Serializable {
	private static final long serialVersionUID = 8434619189681605144L;
	private String name, website, email;

	public Teacher(String name, String website, String email) {
		super();
		this.name = name;
		this.website = website;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getWebsite() {
		return website;
	}

	public String getEmail() {
		return email;
	}

}
