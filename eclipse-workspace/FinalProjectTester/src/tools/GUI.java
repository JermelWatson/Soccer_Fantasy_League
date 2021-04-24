package tools;


import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI{
	//Declares the components that will be used to design the home frame
	private static JLabel label;
	private static JButton autoPrice;
	private static JButton autoPoints;
	private static JButton manual;
	private static JButton exitProgram;
	
	public static void main(String [] args) {
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		//frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 100, 700, 700);
		frame.setVisible(true);
		
		frame.add(panel);
		
		panel.setLayout(null);
		
		label = new JLabel("Welcome to our Fanasy league system");
		label.setFont(new Font("Times New Romans", Font.BOLD, 30));
		label.setBounds(60, 25, 550, 40);
		panel.add(label);
		
		autoPrice = new JButton("Automatic selection based on price");
		autoPrice.setBounds(185, 105, 300, 40);
		autoPrice.addActionListener(new Readfile());
		panel.add(autoPrice);
		
		
		autoPoints = new JButton("Automatic selection based on points in the previous season");
		autoPoints.setBounds(135, 185, 400, 40);
		autoPoints.addActionListener(new AutoByPoints());
		panel.add(autoPoints);
		
		
		manual = new JButton("Manual selection");
		manual.setBounds(185, 265, 300, 40);
		manual.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SelectionWindow newWindoe = new SelectionWindow();
			}
			
		});
		panel.add(manual);
		
		exitProgram = new JButton("Exit");
		exitProgram.setBounds(185, 345, 300, 40);
		panel.add(exitProgram);
		exitProgram.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Component frame = null;
				int response = JOptionPane.showConfirmDialog(frame, "Do you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if (response == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
			
		});
		
		
				
		
	}

}
