package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LogViewPanel extends JPanel {
	private static final long serialVersionUID = 6L;

	private JLabel[] weekLabel = new JLabel[5];
	private JLabel[] startLabel = new JLabel[5];
	private JLabel[] endLabel = new JLabel[5];
	private JLabel[] totalLabel = new JLabel[6];
	private String[] startHour = { "---", "---", "---", "---", "---" };
	private String[] endHour = { "---", "---", "---", "---", "---" };
	private String[] totalHour = { "---", "---", "---", "---", "---", "---" };

	private String PATH = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\TimeTrackerData\\";

	final static int LEFT_OFFSET = 40;
	final static int TOP_OFFSET = 25;

	public LogViewPanel(String fileName) {

		PATH += fileName;
		setLayout(null);
		setPreferredSize(new Dimension(575, 325));
		setBorder(new EmptyBorder(30, 30, 30, 30));

		checkFile();
		createLabels();
		createWeekLabels();

		updateTotal();

	}

	public String getTotal() {
		return this.totalHour[5];
	}

	private void createLabels() {
		for (int i = 0; i < 5; i++) {
			startLabel[i] = new JLabel(startHour[i], SwingConstants.CENTER);
			startLabel[i].setFont(new Font(startLabel[i].getName(), Font.BOLD, 16));
			startLabel[i].setBorder(BorderFactory.createEtchedBorder());
			startLabel[i].setBounds(LEFT_OFFSET + 100, TOP_OFFSET + i * 40 + 50, 100, 25);
			this.add(startLabel[i]);
		}

		for (int i = 0; i < 5; i++) {
			endLabel[i] = new JLabel(endHour[i], SwingConstants.CENTER);
			endLabel[i].setFont(new Font(endLabel[i].getName(), Font.BOLD, 16));
			endLabel[i].setBorder(BorderFactory.createEtchedBorder());
			endLabel[i].setBounds(LEFT_OFFSET + 235, TOP_OFFSET + i * 40 + 50, 100, 25);
			this.add(endLabel[i]);
		}

		for (int i = 0; i < 6; i++) {
			totalLabel[i] = new JLabel(totalHour[i], SwingConstants.CENTER);
			totalLabel[i].setFont(new Font(totalLabel[i].getName(), Font.BOLD, 16));
			totalLabel[i].setBorder(BorderFactory.createEtchedBorder());
			totalLabel[i].setBounds(LEFT_OFFSET + 380, TOP_OFFSET + i * 40 + 50, 100, 25);
			this.add(totalLabel[i]);
		}
	}

	private void updateGrandTotal() {
		int totalHr = 0;
		int totalMin = 0;
		for (int i = 0; i < 5; i++) {
			if (!totalHour[i].equals("---")) {
				String[] totalTime = totalHour[i].split(":");
				int hour = Integer.parseInt(totalTime[0]);
				int min = Integer.parseInt(totalTime[1]);

				totalHr += hour;
				totalMin += min;
			}
		}

		totalHr += (totalMin / 60);
		totalMin = (totalMin % 60);

		if (totalHr != 0 || totalMin != 0) {
			if (totalMin < 10)
				totalHour[5] = totalHr + ":0" + totalMin;
			else
				totalHour[5] = totalHr + ":" + totalMin;

			totalLabel[5].setText(totalHour[5]);
		}
	}

	private void updateTotal() {
		for (int i = 0; i < 5; i++) {
			if (!startHour[i].equals("---") && !endHour[i].equals("---")) {
				String[] startTime = startHour[i].split(":");
				String[] endTime = endHour[i].split(":");
				int startHr = Integer.parseInt(startTime[0]);
				int endHr = Integer.parseInt(endTime[0]);
				int startMin = Integer.parseInt(startTime[1]);
				int endMin = Integer.parseInt(endTime[1]);
				int totalHr = 0;
				int totalMin = 0;

				totalHr = endHr - startHr;
				totalMin = endMin - startMin;
				if (totalMin < 0) {
					totalHr--;
					totalMin += 60;
				}
				if (totalMin < 10) {
					totalHour[i] = totalHr + ":0" + totalMin;
					totalLabel[i].setText(totalHr + ":0" + totalMin);
				} else {
					totalHour[i] = totalHr + ":" + totalMin;
					totalLabel[i].setText(totalHr + ":" + totalMin);
				}
			}
		}

		updateGrandTotal();
	}

	private void createWeekLabels() {
		JLabel fromL = new JLabel();
		fromL.setText("From");
		fromL.setFont(new Font(fromL.getName(), Font.BOLD, 20));
		fromL.setBounds(LEFT_OFFSET + 125, TOP_OFFSET, 100, 25);
		this.add(fromL);

		JLabel toL = new JLabel();
		toL.setText("To");
		toL.setFont(new Font(toL.getName(), Font.BOLD, 20));
		toL.setBounds(LEFT_OFFSET + 270, TOP_OFFSET, 100, 25);
		this.add(toL);

		JLabel totalL = new JLabel();
		totalL.setText("Total");
		totalL.setFont(new Font(totalL.getName(), Font.BOLD, 20));
		totalL.setBounds(LEFT_OFFSET + 405, TOP_OFFSET, 100, 25);
		this.add(totalL);

		String[] wNames = { "Mon", "Tue", "Wed", "Thu", "Fri" };
		for (int i = 0; i < 5; i++) {
			weekLabel[i] = new JLabel(wNames[i], SwingConstants.CENTER);
			weekLabel[i].setFont(new Font(weekLabel[i].getName(), Font.BOLD, 18));
			weekLabel[i].setBounds(LEFT_OFFSET - 15, TOP_OFFSET + i * 40 + 50, 100, 25);
			this.add(weekLabel[i]);
		}
	}

	private void checkFile() {
		if ((new File(PATH)).exists()) {
			try {
				Scanner scanner = new Scanner(new File(PATH));
				String[] tempArray = new String[10];
				int cnt = 0;
				while (scanner.hasNextLine()) {
					tempArray[cnt++] = scanner.nextLine();
				}
				scanner.close();

				for (int i = 0; i < 5; i++) {
					startHour[i] = tempArray[i];
					endHour[i] = tempArray[i + 5];
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
