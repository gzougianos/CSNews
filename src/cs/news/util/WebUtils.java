package cs.news.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebUtils {
	public static final String ANNOUNCEMENT_URL_PREFIX = "http://cs.uoi.gr/index.php?menu=m58&id=";
	public static final String NEWS_LIST_MAINPAGE = "http://cs.uoi.gr/index.php?menu=m5&page=";
	private static final String CONNECTION_CHECK_URL = "http://google.com";

	public static String FetchPDFLink(int announceId) throws IOException {
		final String announceLink = ANNOUNCEMENT_URL_PREFIX + announceId;
		Document doc = Jsoup.connect(announceLink).get();
		Element attached = doc.getElementsByClass("newsMoreAttached").first();
		String href = attached.select("a").attr("href");
		return href.length() > 1 ? "http://cs.uoi.gr" + href : null;//length = 0 when PDF link doesn't exist
	}

	public static void DownloadPDF(String pdfLink, File file) throws IOException {
		final URL url = new URL(pdfLink);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);
		int length = -1;
		byte[] buffer = new byte[1024];
		while ((length = in.read(buffer)) > -1) {
			fos.write(buffer, 0, length);
		}
		fos.close();
		in.close();
	}

	public static boolean AvailableInternetConnection() {
		try {
			final URL url = new URL(CONNECTION_CHECK_URL);
			final URLConnection con = url.openConnection();
			con.setConnectTimeout(4500);
			con.connect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
