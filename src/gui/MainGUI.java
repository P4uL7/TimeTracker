package gui;


import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainGUI() {
		initUI();
	}

	private void setIcon() {
		String imagePath = "calendar_icon.png";
		InputStream imgStream = MainGUI.class.getResourceAsStream(imagePath);
		BufferedImage myImg;
		try {
			myImg = ImageIO.read(imgStream);
			setIconImage(myImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initUI() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setIcon();

		setJMenuBar(new TopMenu());
		add(new BasePanel());

		pack();

		setTitle("Time Tracking App");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MainGUI ex = new MainGUI();
			ex.setVisible(true);
		});
	}

}
