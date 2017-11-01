package cs.news.util;

import java.net.URL;
import java.net.URLConnection;

public class InternetChecker {
	public static boolean AvailableInternetConnection() {
		try {
			final URL url = new URL("http://google.com");
			final URLConnection con = url.openConnection();
			con.setConnectTimeout(2500);
			con.connect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
