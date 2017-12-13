package cs.news.swing;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LimitedValueSpinner extends JSpinner {

	private static final long serialVersionUID = -5511585313640533850L;

	public LimitedValueSpinner(int initialValue, int minValue, int maxValue) {
		setValue(initialValue);
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (int) getValue();
				if (value > maxValue)
					setValue(maxValue);
				else if (value < minValue)
					setValue(minValue);
			}
		});
	}

}
