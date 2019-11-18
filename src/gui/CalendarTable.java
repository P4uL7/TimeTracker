package gui;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CalendarTable extends JScrollPane {

	private static final long serialVersionUID = 5L;

	private DateFormat yearFormat = new SimpleDateFormat("yyyy");
	private DateFormat monthFormat = new SimpleDateFormat("MM");
	private Date date = new Date();
	int _year = Integer.parseInt(yearFormat.format(date));
	int _month = Integer.parseInt(monthFormat.format(date));

	String[][] data = MyCalendar.runClass(_month, _year);
	String[] columnNames = { "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su" };

	JTable calendarTable = null;

	public CalendarTable() {
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
		// sp = new JScrollPane(calendarTable);
		this.setViewportView(calendarTable);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBounds(425 - BasePanel.LEFT_OFFSET, BasePanel.TOP_OFFSET + 80, 200, 120);
	}

	void incMonth(JLabel monthYear) {
		if (++this._month == 13) {
			this._month = 1;
			this._year++;
		}
		this.data = MyCalendar.runClass(this._month, this._year);
		monthYear.setText(MyCalendar.monthYear);

		TableModel tableModel = new DefaultTableModel(this.data, this.columnNames);
		this.calendarTable.setModel(tableModel);
	}

	void decMonth(JLabel monthYear) {
		if (--this._month == 0) {
			this._month = 12;
			this._year--;
		}
		this.data = MyCalendar.runClass(this._month, this._year);
		monthYear.setText(MyCalendar.monthYear);

		TableModel tableModel = new DefaultTableModel(this.data, this.columnNames);
		this.calendarTable.setModel(tableModel);
	}

}
