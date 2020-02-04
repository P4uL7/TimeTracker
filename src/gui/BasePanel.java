package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class BasePanel extends JPanel {
	private static final long serialVersionUID = 4L;

	private JLabel[] weekLabel = new JLabel[5];
	private JLabel[] startLabel = new JLabel[5];
	private JLabel[] endLabel = new JLabel[5];
	private JLabel[] totalLabel = new JLabel[6];
	private String[] startHour = { "---", "---", "---", "---", "---" };
	private String[] endHour = { "---", "---", "---", "---", "---" };
	private String[] totalHour = { "---", "---", "---", "---", "---", "---" };
	private JButton[] startButton = new JButton[5];
	private JButton[] endButton = new JButton[5];
	private JButton resetButton = null;

	int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
	int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	private final String PATH = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\TimeTrackerData\\" + currentYear + "_week" + weekNumber;

	final static int LEFT_OFFSET = 50; // 350
	final static int TOP_OFFSET = 25;

	public BasePanel() {

		this.setLayout(null);
		this.setPreferredSize(new Dimension(900 - 300, 325));
		this.setBorder(new EmptyBorder(30, 30, 30, 30));

		checkFile();
		createLabels();
		createButtons();
		createWeekLabels();
		// createCalendar();

		updateTotal();
		createResetButton();

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

	private void writeToFile() {
		try {
			FileWriter fw = new FileWriter(PATH);
			for (String s : startHour)
				fw.write(s + "\n");

			for (String s : endHour)
				fw.write(s + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setDate(int id, int from_to) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String _date = dtf.format(now);
		if (from_to == 0) {
			startLabel[id].setText(_date);
			startHour[id] = _date;
		} else if (from_to == 1) {
			endLabel[id].setText(_date);
			endHour[id] = _date;
		}

		updateTotal();
		writeToFile();
	}

	private void createButtons() {
		final int id = today;

		if (startHour[today].equals("---")) {
			startButton[today] = new JButton("Log");
			startButton[today].addActionListener((event) -> {
				setDate(id, 0);
				startButton[today].setVisible(false);
			});
			startButton[today].setFont(new Font(startButton[today].getName(), Font.BOLD, 12));
			startButton[today].setBounds(LEFT_OFFSET + 118, TOP_OFFSET + 250, 60, 25);
			this.add(startButton[today]);
		} else {
			endButton[today] = new JButton("Log");
			endButton[today].addActionListener((event) -> {
				setDate(id, 1);
				endButton[today].setVisible(false);
			});
			endButton[today].setFont(new Font(endButton[today].getName(), Font.BOLD, 12));
			endButton[today].setBounds(LEFT_OFFSET + 118, TOP_OFFSET + 250, 60, 25);
			this.add(endButton[today]);
		}
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

	private void createCalendar() {
		CalendarTable calendar = new CalendarTable();
		this.add(calendar);

		JLabel monthYear = new JLabel();
		monthYear.setFont(new Font(monthYear.getName(), Font.BOLD, 18));
		monthYear.setText(MyCalendar.monthYear);
		monthYear.setBounds(450 - LEFT_OFFSET, TOP_OFFSET + 40, 150, 20);
		this.add(monthYear);

		JButton leftButton = new JButton("<");
		leftButton.addActionListener((event) -> calendar.decMonth(monthYear));
		leftButton.setBounds(470 - LEFT_OFFSET, TOP_OFFSET + 215, 50, 25);
		this.add(leftButton);

		JButton rightButton = new JButton(">");
		rightButton.addActionListener((event) -> calendar.incMonth(monthYear));
		rightButton.setBounds(530 - LEFT_OFFSET, TOP_OFFSET + 215, 50, 25);
		this.add(rightButton);
	}

	private void createNewFile() {
		Path path = Paths.get("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\TimeTrackerData");
		if (Files.exists(path)) {
			System.out.println("Folder exists!");
		} else {
			System.out.println("Folder does not exist.");

			File newFolder = path.toFile();
			if (newFolder.mkdir())
				System.out.println("Folder was created!");
			else
				System.out.println("Unable to create folder");
		}

		try {
			FileWriter fw = new FileWriter(PATH);
			for (int i = 0; i < 9; i++)
				fw.write("---\n");
			fw.write("---");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
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
					System.out.println(tempArray[cnt - 1]);
				}
				scanner.close();

				for (int i = 0; i < 5; i++) {
					startHour[i] = tempArray[i];
					endHour[i] = tempArray[i + 5];
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			createNewFile();
		}
	}

	private void createResetButton() {
		if (today == 0 && !totalHour[5].equals("---") && (!totalHour[1].equals("---") || !totalHour[3].equals("---"))) {

			resetButton = new JButton("Reset");
			resetButton.addActionListener((event) -> {

				createNewFile();
				checkFile();

				for (int i = 0; i < 5; i++) {
					startLabel[i].setText(startHour[i]);
					endLabel[i].setText(endHour[i]);
				}

				for (int i = 0; i <= 5; i++) {
					totalHour[i] = "---";
					totalLabel[i].setText(totalHour[i]);
				}

				resetButton.setVisible(false);
			});
			resetButton.setFont(new Font(resetButton.getName(), Font.BOLD, 12));
			resetButton.setBounds(LEFT_OFFSET + 248, TOP_OFFSET + 250, 70, 25);
			this.add(resetButton);
		}
	}
}
