package cs.newsdatamanagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cs.news.model.Announce;
import cs.news.util.Options;

public class AnnounceManager extends DataManager {
	private Stack<Announce> announces;

	@SuppressWarnings("unchecked")
	protected AnnounceManager() {
		super("CsNewsAnnounceData.tmp");
		announces = (Stack<Announce>) loadData();
		if (announces == null)
			announces = new Stack<>();
	}

	public int getNumOfUnreadAnnounces() {
		int c = 0;
		for (Announce a : announces)
			if (!a.isRead())
				c++;
		return c;
	}

	public void removeReadAnnounces() {
		List<Announce> read = new ArrayList<>();
		for (int i = announces.size() - 1; i >= Options.ANNOUNCES_MAX_NUMBER.toInt(); i--) {
			Announce a = announces.get(i);
			if (a.isRead())
				read.add(a);
		}
		System.out.println(read.size() + " Announces have been removed.");
		announces.removeAll(read);
	}

	public boolean announceAlreadyExists(Announce a) {
		for (Announce announce : announces) {
			if (announce.getId() == a.getId())
				return true;
		}
		return false;
	}

	@Override
	public Stack<Announce> getData() {
		return announces;
	}

	public static final AnnounceManager getInstance() {
		return SingletonHolder._instance;
	}

	private static final class SingletonHolder {
		protected static final AnnounceManager _instance = new AnnounceManager();
	}
}
