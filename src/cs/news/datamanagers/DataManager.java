package cs.news.datamanagers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cs.news.util.WebDataParser;

public abstract class DataManager implements WebDataParser {
	private static final String HOME_DIRECTORY = System.getenv("APPDATA") + "\\CSE News\\";
	private String filePath;

	protected DataManager(String dataFileName) {
		this.filePath = HOME_DIRECTORY + dataFileName;
	}

	public abstract Object getData();

	@Override
	public final void parseWebData() {
		if (!parseWebDataCondition())
			return;
		parseData();
	}

	protected abstract boolean parseWebDataCondition();

	protected abstract void parseData();

	protected final File getDataFile() {
		return new File(filePath);
	}

	protected final Object loadData() {
		Object readObject = null;
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				//Create the folder
				f.getParentFile().mkdir();
				return null;
			}
			FileInputStream fis;
			fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			readObject = ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return readObject;
	}

	public final void saveData() {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(getData());
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
