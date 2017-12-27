package cs.news.swing;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs.news.Timer;
import cs.news.util.BatchWriter;
import cs.news.util.OpenLinkActionListener;
import cs.news.util.Options;

public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 6429776879534491034L;
	private static final Object[] DIALOG_OPTIONS = { "Αποθήκευση", "Ακύρωση" };
	private static final Dimension PANEL_DIMENSION = new Dimension(265, 250);
	private static final String PROGRAM_INFO_LINK = "https://github.com/GiorgosZougianos/CSNews";
	private JSpinner announceSpinner = new LimitedValueSpinner(Options.ANNOUNCES_MAX_NUMBER.toInt(), 0, 700);
	private JCheckBox openInPdfCheckBox = new JCheckBox();
	private JSpinner syncSpinner = new LimitedValueSpinner(Options.SYNC_ANNOUNCES_TIME.toInt(), 1, 5000);
	private JSpinner remindSpinner = new LimitedValueSpinner(Options.REMIND_ANNOUNCES_TIME.toInt(), 1, 5000);
	private JCheckBox windowsStartupCheckBox;
	private JSpinner maxCharactersSpinner = new LimitedValueSpinner(Options.MAX_CHARACTERS_ANNOUNCE_MENU_ITEM.toInt(), 40, 200);
	private JCheckBox enableManualSync;

	public SettingsPanel() {
		super();
		setPreferredSize(PANEL_DIMENSION);
		setLayout(null);
		JTextField announceSettingsHeader;
		announceSettingsHeader = new JTextField();
		announceSettingsHeader.setEditable(false);
		announceSettingsHeader.setHorizontalAlignment(SwingConstants.CENTER);
		announceSettingsHeader.setText("Ρυθμίσεις Ανακοινώσεων");
		announceSettingsHeader.setBounds(63, 0, 132, 20);
		announceSettingsHeader.setBorder(BorderFactory.createEmptyBorder());
		add(announceSettingsHeader);
		announceSettingsHeader.setColumns(10);
		JTextField maxAnnouncesTxt;
		maxAnnouncesTxt = new JTextField();
		maxAnnouncesTxt.setHorizontalAlignment(SwingConstants.LEFT);
		maxAnnouncesTxt.setText("Μέγιστος αριθμός:");
		maxAnnouncesTxt.setToolTipText("<html>Προτιμήστε μικρούς αριθμούς για αποφυγή καθυστέρησης συγχρονισμού." + "<br>Προτεινόμενη Τιμή: 10</html>");
		maxAnnouncesTxt.setEditable(false);
		maxAnnouncesTxt.setBorder(BorderFactory.createEmptyBorder());
		maxAnnouncesTxt.setBounds(6, 24, 201, 20);
		add(maxAnnouncesTxt);
		maxAnnouncesTxt.setColumns(10);

		announceSpinner.setBounds(217, 24, 41, 20);
		announceSpinner.setToolTipText("<html>Προτιμήστε μικρούς αριθμούς για αποφυγή καθυστέρησης συγχρονισμού." + "<br>Προτεινόμενη Τιμή: 10</html>");
		add(announceSpinner);

		openInPdfCheckBox.setBounds(2, 45, 141, 20);
		openInPdfCheckBox.setSelected(Options.OPEN_ANNOUNCEMENTS_IN_PDF.toBoolean());
		openInPdfCheckBox.setText("’νοιγμα σε μορφή PDF.");
		openInPdfCheckBox.setToolTipText("<html>Στην ιστοσελίδα συνήθως αναγράφεται ένα κομμάτι της ανακοίνωσης."
				+ "<br>Προτιμήστε αυτήν την επιλογή για προβολή της ολοκληρωμένης ανακοίνωσης.</html>");
		add(openInPdfCheckBox);

		JTextField announcementSyncTxt = new JTextField("Περίοδος Συγχρονισμού:");
		announcementSyncTxt.setToolTipText("<html>Κάθε πόσα λεπτά θα γίνεται έλεγχος για νέες ανακοινώσεις.<br>Προτεινόμενη τιμή: 30 λεπτά</html>");
		announcementSyncTxt.setHorizontalAlignment(SwingConstants.LEFT);
		announcementSyncTxt.setEditable(false);
		announcementSyncTxt.setBounds(6, 66, 201, 20);
		announcementSyncTxt.setColumns(10);
		announcementSyncTxt.setBorder(BorderFactory.createEmptyBorder());
		add(announcementSyncTxt);

		syncSpinner.setBounds(217, 66, 41, 20);
		syncSpinner.setToolTipText(announcementSyncTxt.getToolTipText());//same tool tip as announcementSyncTxt
		add(syncSpinner);

		JTextField announcementRemindTxt = new JTextField("Περίοδος Υπενθύμισης:");
		announcementRemindTxt.setToolTipText(
				"<html>Κάθε πόσα λεπτά θα γίνεται υπενθύμιση σε περίπτωση νέων ανακοινώσεων.<br>Χρησιμοποιήστε την τιμή 0 για απενεργοποίηση υπενθυμήσεων.<br>Προτεινόμενη τιμή 120 λεπτά (2 ώρες).");
		announcementRemindTxt.setEditable(false);
		announcementRemindTxt.setBounds(6, 87, 201, 20);
		announcementRemindTxt.setColumns(10);
		announcementRemindTxt.setBorder(BorderFactory.createEmptyBorder());
		add(announcementRemindTxt);

		remindSpinner.setBounds(217, 87, 41, 20);
		remindSpinner.setToolTipText(announcementRemindTxt.getToolTipText());//same tool tip as announcementRemindTxt
		add(remindSpinner);

		JTextField maxCharsInAnnounceTxT;
		maxCharsInAnnounceTxT = new JTextField("Μέγιστος αριθμός χαρακτήρων εμφάνισης:");
		maxCharsInAnnounceTxT.setEditable(false);
		maxCharsInAnnounceTxT.setBorder(BorderFactory.createEmptyBorder());
		maxCharsInAnnounceTxT.setToolTipText("<html>Αριθμός χαρακτήρων που θα εμφανίζονται στην επικεφαλίδα μιας ανακοίνωσης.<br>Προτεινόμενη τιμή: 70</html>");
		maxCharsInAnnounceTxT.setHorizontalAlignment(SwingConstants.LEFT);
		maxCharsInAnnounceTxT.setBounds(6, 108, 211, 20);
		add(maxCharsInAnnounceTxT);
		maxCharsInAnnounceTxT.setColumns(10);

		maxCharactersSpinner.setBounds(217, 108, 41, 20);
		maxCharactersSpinner.setToolTipText(maxCharsInAnnounceTxT.getToolTipText());//same tool tip as maxCharsInAnnounceTxT
		add(maxCharactersSpinner);

		//General Settings
		JTextField generalSettingsHeader = new JTextField("Γενικές Ρυθμίσεις");
		generalSettingsHeader.setHorizontalAlignment(SwingConstants.CENTER);
		generalSettingsHeader.setEditable(false);
		generalSettingsHeader.setBounds(63, 146, 132, 20);
		generalSettingsHeader.setBorder(BorderFactory.createEmptyBorder());
		generalSettingsHeader.setColumns(10);
		add(generalSettingsHeader);

		windowsStartupCheckBox = new JCheckBox("Αυτόματη εκκίνηση με τα Windows.");
		windowsStartupCheckBox.setSelected(Options.WINDOWS_STARTUP.toBoolean());
		windowsStartupCheckBox.setBounds(6, 164, 252, 23);
		add(windowsStartupCheckBox);

		JButton programInfoButton = new JButton("Πληροφορίες σχετικά με το Πρόγραμμα");
		programInfoButton.addActionListener(new OpenLinkActionListener(PROGRAM_INFO_LINK));
		programInfoButton.setFont(new Font(programInfoButton.getFont().getFontName(), 0, 9));
		programInfoButton.setBounds(30, 216, 201, 23);
		add(programInfoButton);

		enableManualSync = new JCheckBox("Μενού για Χειροκίνητο Συγχρονισμό");
		enableManualSync.setSelected(Options.MANUAL_SYNC_MENU.toBoolean());
		enableManualSync.setToolTipText(
				"<html>Αυτή η επιλογή θα ενεργοποιήσει το μενού (κουμπί) για χεροκίνητο συγχρονισμό ανακοινώσεων.<br>Έπειτα από έναν χειροκίνητο συγχρονισμό ο επόμενος αυτόματος συγχρονισμός θα γίνει μετά απο την επιλεγμένη περίοδο αυτόματου συγχρονισμού.");
		enableManualSync.setBounds(6, 186, 252, 23);
		add(enableManualSync);
	}

	public static void open() {
		SettingsPanel panel = new SettingsPanel();
		//Exit == 1, Save == 0
		int answer = JOptionPane.showOptionDialog(null, panel, "Ρυθμίσεις", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, DIALOG_OPTIONS, 1);
		if (answer == 0) {
			boolean somethingChanged = false;
			somethingChanged |= Options.ANNOUNCES_MAX_NUMBER.update(panel.getOptionMaxAnnouncements());
			somethingChanged |= Options.OPEN_ANNOUNCEMENTS_IN_PDF.update(panel.getOptionPDFAnnouncements());
			somethingChanged |= Options.SYNC_ANNOUNCES_TIME.update(panel.getOptionSyncAnnouncesTime());
			somethingChanged |= Options.REMIND_ANNOUNCES_TIME.update(panel.getOptionRemindAnnouncesTime());
			somethingChanged |= Options.WINDOWS_STARTUP.update(panel.getOptionWindowsStartUp());
			somethingChanged |= Options.MAX_CHARACTERS_ANNOUNCE_MENU_ITEM.update(panel.getOptionMaxCharsInAnnouncements());
			somethingChanged |= Options.MANUAL_SYNC_MENU.update(panel.getManualSync());
			if (somethingChanged) {
				Timer.restart();
				BatchWriter.handleState();
			}
		}
	}

	private boolean getManualSync() {
		return enableManualSync.isSelected();
	}

	private int getOptionMaxAnnouncements() {
		return (int) announceSpinner.getValue();
	}

	private boolean getOptionPDFAnnouncements() {
		return openInPdfCheckBox.isSelected();
	}

	private int getOptionSyncAnnouncesTime() {
		return (int) syncSpinner.getValue();
	}

	private int getOptionRemindAnnouncesTime() {
		return (int) remindSpinner.getValue();
	}

	private boolean getOptionWindowsStartUp() {
		return windowsStartupCheckBox.isSelected();
	}

	private int getOptionMaxCharsInAnnouncements() {
		return (int) maxCharactersSpinner.getValue();
	}
}
