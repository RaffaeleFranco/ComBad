
package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ProgressFrame {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ProgressFrame window = new ProgressFrame(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProgressFrame(String name) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		initialize(name);

	}

	private void initialize(String name) throws InterruptedException {
		frame = new JFrame("Data elaboration");

		frame.setForeground(Color.BLACK);
		frame.setResizable(false);
		
		ImageIcon icon = new ImageIcon("badCodeSmellIco.jpg");
		frame.setSize(300, 100);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setIconImage(icon.getImage());
		
		JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressBar.setStringPainted(true);
		
		
		frame.getContentPane().add(progressBar);

		Thread thread = new Thread(new Runnable() {
			public void run() {

				for (int i = 0; i <= 100; i++) {

					progressBar.setStringPainted(true);

					progressBar.setValue(i);
					frame.getContentPane().add(progressBar);

					if (progressBar.getValue() == 100) {
						progressBar.setVisible(false);
						frame.dispose();
						new CommittersBadCodeSmellsFrame(name);
					}

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		thread.start();

	}
}
