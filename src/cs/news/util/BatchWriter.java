package cs.news.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class BatchWriter {
	private static final String BATCH_LOCATION = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\";
	private static final String BATCH_NAME = "runcs.bat";
	private static final String BATCH_COMMAND = "\"JAR_LOCATION\" /sil";

	private static void writeBatch() {
		final File file = new File(BATCH_LOCATION + BATCH_NAME);
		try {
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			String jarLoc = BatchWriter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			jarLoc = jarLoc.substring(1); //Substring the first slash
			writer.write("@echo off\r\n");
			boolean hasNonLatinChars = !Charset.forName("US-ASCII").newEncoder().canEncode(jarLoc);
			if (hasNonLatinChars) //Command required to run something from non-latin path.
				writer.write("chcp 65001\r\n");
			writer.write("IF NOT EXIST \"" + jarLoc + "\" Exit\r\n");
			writer.write(BATCH_COMMAND.replaceAll("JAR_LOCATION", jarLoc));
			writer.flush();
			writer.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			Debugger.showException(e, BatchWriter.class, "Error writing the .bat file.", false);
		}
	}

	private static void deleteBatch() {
		final File file = new File(BATCH_LOCATION + BATCH_NAME);
		file.delete();
	}

	public static void handleState() {
		boolean enable = Options.WINDOWS_STARTUP.toBoolean();
		if (enable)
			writeBatch();
		else
			deleteBatch();
	}
}
