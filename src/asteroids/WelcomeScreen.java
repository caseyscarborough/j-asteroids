package asteroids;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Displays a new welcome screen at the start of the game with
 * options for the user to select the number of asteroids and 
 * whether sound should be on or off.
 * @author Casey Scarborough
 * @see GameBoard
 */
@SuppressWarnings("serial")
public class WelcomeScreen extends JFrame {
	private JTextField tfQuantity;
	private JLabel welcome, lQuantity, sound;
	private JRadioButton onButton, offButton;
	private JPanel settingsPanel;
	private JButton startGame;
	private int quantity;
	
	
	public WelcomeScreen() {
		this.setSize(300,250);
		
		startGame = new JButton("Start Game");
		settingsPanel = new JPanel();
		tfQuantity = new JTextField(10);
		lQuantity = new JLabel("Number of Asteroids:");
		
		sound = new JLabel("Sound:");
		onButton = new JRadioButton("On");
		offButton = new JRadioButton("Off");
		
		onButton.setSelected(true);
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(onButton);
		btnGroup.add(offButton);
		
		welcome = new JLabel("    Welcome to Asteroids!");
		welcome.setFont(new Font("Sans-serif", Font.PLAIN, 22));
		
		this.add(welcome, BorderLayout.NORTH);
		settingsPanel.add(tfQuantity, FlowLayout.LEFT);
		settingsPanel.add(lQuantity, FlowLayout.LEFT);
		
		settingsPanel.add(offButton, FlowLayout.RIGHT);
		settingsPanel.add(onButton, FlowLayout.RIGHT);
		settingsPanel.add(sound, FlowLayout.RIGHT);
		
		
		this.add(startGame, BorderLayout.SOUTH);
		this.add(settingsPanel, BorderLayout.CENTER);
		
		this.centerWindow();
		
		startGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == startGame) {
					if (!tfQuantity.getText().equals("")) {
						try {
							quantity = Integer.parseInt(tfQuantity.getText());
						} catch (NumberFormatException e1) {
							quantity = 15;
						}
					} else {
						quantity = 15;
					}
					closeWindow();
					if (onButton.isSelected()) { new GameBoard(quantity, true); }
					else { new GameBoard(quantity, false); }
				}
			}
			
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void closeWindow() { this.dispose(); }
	
	private void centerWindow() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int width = this.getSize().width;
		int height = this.getSize().height;
		int x = (dim.width - width) / 2;
		int y = (dim.height - height) / 2;
		this.setLocation(x, y);
	}
}
