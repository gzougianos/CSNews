package cs.news;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class AnnounceMap extends HashMap<Integer, Announce> implements Serializable {
	private static final long serialVersionUID = 1369674128860734933L;

	public AnnounceMap() {
		super();
	}

	public ArrayList<Announce> getSortedAnnounces() {
		ArrayList<Announce> list = new ArrayList<Announce>();
		list.addAll(AnnounceManager.announces.values());
		Collections.sort(list, new Comparator<Announce>() {

			public int compare(Announce o1, Announce o2) {
				return o2.getId() - o1.getId();
			}
		});
		return list;
	}

}
