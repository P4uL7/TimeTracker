package gui;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class TopMenu extends JMenuBar {
	private static final long serialVersionUID = 2L;

	public TopMenu() {
		final String PATH = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\TimeTrackerData";

		// Where the GUI is created:
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		// Build the first menu.
		menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		this.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Open Logs Folder", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Opens the folder containing the log data.");
		menuItem.addActionListener((event) -> {
			try {
				Runtime.getRuntime().exec("explorer.exe " + PATH + "\\");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("View Balance");//, new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_V);
		menuItem.addActionListener((event) -> {

		});
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("Show logs from");
		submenu.setMnemonic(KeyEvent.VK_S);

		// menuItem = new JMenuItem("List 1");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		// menuItem.addActionListener((event) -> {
		// try (Stream<Path> paths = Files.walk(Paths.get(PATH))) {
		// paths.filter(Files::isRegularFile).forEach(System.out::println);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// });
		// submenu.add(menuItem);

		for (final File fileEntry : new File(PATH).listFiles()) {
			if (fileEntry.isFile()) {
				System.out.println(fileEntry.getName());
				menuItem = new JMenuItem(fileEntry.getName());
				menuItem.addActionListener((event) -> {
					System.out.println("Clicked on " + fileEntry.getName());

					EventQueue.invokeLater(() -> {
						LogView ex = new LogView(fileEntry.getName());
						ex.setVisible(true);
					});

				});
				submenu.add(menuItem);
			}
		}

		menu.add(submenu);

		// Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		this.add(menu);
	}

}
