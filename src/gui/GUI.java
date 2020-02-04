package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private DateFormat yearFormat = new SimpleDateFormat("yyyy");
	private DateFormat monthFormat = new SimpleDateFormat("MM");
	private DateFormat dayFormat = new SimpleDateFormat("dd");
	private Date date = new Date();
	private int _year = Integer.parseInt(yearFormat.format(date));
	private int _month = Integer.parseInt(monthFormat.format(date));
	private int _day = Integer.parseInt(dayFormat.format(date));

	private String[][] data = MyCalendar.runClass(_month, _year);
	private String[] columnNames = { "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su" };
	private String[] hours;
	private String[] currentWeek;

	private JPanel basePanel = new JPanel();
	private JLabel monthYear = new JLabel();
	private JLabel statusBar = new JLabel();
	private JLabel[] weekLabel = new JLabel[5];
	@SuppressWarnings("rawtypes")
	private JComboBox[] fromCombo = new JComboBox[5];
	@SuppressWarnings("rawtypes")
	private JComboBox[] toCombo = new JComboBox[5];
	private JTable calendarTable;

	public GUI() {
		initUI();
	}

	private void incMonth() {
		if (++_month == 13) {
			_month = 1;
			_year++;
		}
		data = MyCalendar.runClass(_month, _year);
		monthYear.setText(MyCalendar.monthYear);

		TableModel tableModel = new DefaultTableModel(data, columnNames);
		calendarTable.setModel(tableModel);

		basePanel.repaint();
		basePanel.revalidate();
	}

	private void decMonth() {
		if (--_month == 0) {
			_month = 12;
			_year--;
		}
		data = MyCalendar.runClass(_month, _year);
		monthYear.setText(MyCalendar.monthYear);

		TableModel tableModel = new DefaultTableModel(data, columnNames);
		calendarTable.setModel(tableModel);

		basePanel.repaint();
		basePanel.revalidate();
	}

	private void createTable() {
		// Initializing the JTable
		calendarTable = new JTable(data, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}

			DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();

			{ // initializer block
				renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
			}

			@Override
			public TableCellRenderer getCellRenderer(int arg0, int arg1) {
				return renderRight;
			}
		};

		TableColumnModel colMdl = calendarTable.getColumnModel();
		colMdl.getColumn(0).setMaxWidth(30);
		for (int i = 1; i < 7; i++) {
			colMdl.getColumn(i).setMaxWidth(30);
		}

		calendarTable.getTableHeader().setReorderingAllowed(false);
		calendarTable.getTableHeader().setResizingAllowed(false);

		// adding it to JScrollPane
		JScrollPane sp = new JScrollPane(calendarTable);
		sp.setBorder(BorderFactory.createLineBorder(Color.black));
		sp.setBounds(20, 45, 200, 120);
		basePanel.add(sp);
	}

	private void createLabel() {
		monthYear.setFont(new Font(monthYear.getName(), Font.BOLD, 18));
		monthYear.setText(MyCalendar.monthYear);
		monthYear.setBounds(45, 15, 150, 20);
		basePanel.add(monthYear);

		statusBar = new JLabel("Current date:   " + dayFormat.format(date) + "-" + monthFormat.format(date) + "-" + yearFormat.format(date));
		statusBar.setBorder(BorderFactory.createEtchedBorder());
		add(statusBar, BorderLayout.SOUTH);

	}

	private void createButtons() {
		JButton rightButton = new JButton(">");
		rightButton.addActionListener((event) -> incMonth());
		rightButton.setBounds(123, 175, 50, 25);
		basePanel.add(rightButton);

		JButton leftButton = new JButton("<");
		leftButton.addActionListener((event) -> decMonth());
		leftButton.setBounds(63, 175, 50, 25);
		basePanel.add(leftButton);
	}

	private void playing() {
		currentWeek = new String[7];
		TableModel tm = calendarTable.getModel();
		for (int i = 0; i < tm.getRowCount(); i++) {
			for (int j = 0; j < tm.getColumnCount(); j++) {
				String o = (String) tm.getValueAt(i, j);
				System.out.printf(" %2s ", o);
				if (o.equals(_day + "")) {
					for (int k = 0; k < 7; k++) {
						currentWeek[k] = (String) tm.getValueAt(i, k);
					}
				}
			}
			System.out.println();
		}

		for (String s : currentWeek)
			System.out.print(" " + s);
	}

	private void createWeekLabels() {
		JLabel fromL = new JLabel();
		fromL.setText("From");
		fromL.setFont(new Font(fromL.getName(), Font.BOLD, 16));
		fromL.setBounds(350, 15, 100, 25);
		basePanel.add(fromL);

		JLabel toL = new JLabel();
		toL.setText("To");
		toL.setFont(new Font(fromL.getName(), Font.BOLD, 16));
		toL.setBounds(440, 15, 100, 25);
		basePanel.add(toL);

		String[] wNames = { "Mon", "Tue", "Wed", "Thu", "Fri" };
		for (int i = 0; i < 5; i++) {
			weekLabel[i] = new JLabel();
			weekLabel[i].setFont(new Font(monthYear.getName(), Font.BOLD, 16));
			weekLabel[i].setText(wNames[i]);
			weekLabel[i].setBounds(260, i * 32 + 50, 100, 25);
			basePanel.add(weekLabel[i]);
		}
	}

	private void createComboBox() {
		hours = new String[46];
		hours[0] = "-----";
		for (int i = 0; i < 11; i++) {
			hours[4 * i + 1] = (i + 8) + ".00";
			hours[4 * i + 2] = (i + 8) + ".15";
			hours[4 * i + 3] = (i + 8) + ".30";
			hours[4 * i + 4] = (i + 8) + ".45";
		}
		hours[44] = "18.45";
		hours[45] = "19.00";

		for (int i = 0; i < 5; i++) {
			fromCombo[i] = new JComboBox<String>(hours);
			fromCombo[i].setBounds(340, i * 32 + 50, 65, 25);
			basePanel.add(fromCombo[i]);

			toCombo[i] = new JComboBox<String>(hours);
			toCombo[i].setBounds(420, i * 32 + 50, 65, 25);
			basePanel.add(toCombo[i]);
		}

	}

	private void initUI() {

		try {
			UIManager.setLookAndFeel(MainGUI.THEME);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setResizable(false);
		basePanel = new JPanel();
		basePanel.setLayout(null);
		basePanel.setPreferredSize(new Dimension(500, 225));
		basePanel.setBorder(new EmptyBorder(30, 30, 30, 30));

		createLabel();
		createTable();
		createButtons();
		createWeekLabels();
		createComboBox();

		playing();

		add(basePanel);

		pack();

		setTitle("Time Tracking App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			GUI ex = new GUI();
			ex.setVisible(true);
		});
	}
}