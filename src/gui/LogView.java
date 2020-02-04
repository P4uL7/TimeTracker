package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LogView extends JFrame {
	private static final long serialVersionUID = 3L;
	String PATH = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\TimeTrackerData\\";

	LogViewPanel weekLog;

	private int hoursThisWeek;
	private int minutesThisWeek;

	public int getHoursThisWeek() {
		String[] temp = weekLog.getTotal().split(":");
		this.hoursThisWeek = Integer.parseInt(temp[0]);
		return this.hoursThisWeek;
	}

	public int getMinutesThisWeek() {
		String[] temp = weekLog.getTotal().split(":");
		this.minutesThisWeek = Integer.parseInt(temp[1]);
		return this.minutesThisWeek;
	}

	public LogView(String fileName) {
		PATH += fileName;
		weekLog = new LogViewPanel(fileName);
		initUI(fileName);
	}

	private void initUI(String fileName) {
		try {
			UIManager.setLookAndFeel(MainGUI.THEME);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		add(new JLabel(fileName));
		add(weekLog);

		pack();

		setTitle("Log View for " + fileName);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
