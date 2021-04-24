/*Name: Jermel Watson
 *Name: Allan Muir 
 *Name: Julio Castro
 *
 *
 *
 *This class allowsy the user to manual select a their team from a list of presented players. The budget is clearly displayed and decreases per selection
 *Users can also only chose a set number of players, such as Middle fielders - 5, Defenders - 5, Forwards - 3, Goal keepers - 2.
 *The user can also search for players by name and sort the given list by teams in alphabetic order.
 *
 *Functions Implemented
 ****************************************************************************************************************************************************************
 *getFileData3()                                                   -   A copy of the getFileData and getFileData2 but this function returns an ArrayList of players.
 ****************************************************************************************************************************************************************
 *initialize()                                                     -   initializes the components of the window.
 ****************************************************************************************************************************************************************
 *getPlayer(String playerName, ArrayList<Player> playerList)       -   This function checks to see if the player that the user is looking for xists and returns 
 *																	   that player to the list model to be added. 
 ****************************************************************************************************************************************************************
 ****************************************************************************************************************************************************************
 *
 *Date: April 23, 2021 */
package tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField;

public class SelectionWindow{

	private JFrame frame;
	private JTextField textField;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectionWindow window = new SelectionWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public  ArrayList <Player> getFileData3() throws FileNotFoundException {
		ArrayList <Player> players = new ArrayList<Player>();
		
		FileReader findFile;
		BufferedReader buffer  = null;
		try {			
			String newLine;
			File sourceFolder = new File("C:\\Users\\User\\eclipse-workspace\\FinalProjectTester\\TextFiles");
			for( File sourceFile: sourceFolder.listFiles()) {
				String fileName = sourceFile.getName();
				
				findFile = new FileReader(fileName);
				buffer = new BufferedReader(findFile);
			
				while((newLine = buffer.readLine()) != null) {
					String[] row = newLine.split("#");
					if(fileName.equals("defenders.txt")) {//equals might not work
						players.add(new Defender(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
					}
					if(fileName.equals("forwards.txt")) {
						players.add(new Forwards(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
					}
					if(fileName.equals("goalKeepers.txt")) {
						players.add(new Goalkeeper(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
					}
					if(fileName.equals("midfielders.txt")) {
						players.add(new Midfielder(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
					}
				}
			}
		} catch (IOException e1) {
			// catch block
			e1.printStackTrace();
		}
		finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e1) {
					//catch block
					e1.printStackTrace();
				}
			}
		}
		return players;
}
	
	/**
	 * Create the application.
	 */
	public SelectionWindow() {
		
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	double budget = 100.00;

	private void initialize() {		
		//Initializes playerList with the list of players from the files
		ArrayList<Player> playerList = new ArrayList<Player>();
		try {
			playerList = getFileData3();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Sorts players by points and by price if there is a tie
		playerList.sort(new PlayerComparatorPoints());		
		
		//Set list of players on Available lists panel
		ArrayList<Player> setList = new ArrayList<Player>();
		int trace = playerList.size()/2;
		for(int i =0 ; i < trace ; i++) {
			setList.add(playerList.get(i));
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 908, 642);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		//ListModel to hold and display the list of players that can be selected
		DefaultListModel defList = new DefaultListModel();
		for(int i = 0; i < setList.size(); i++) {
			defList.addElement(setList.get(i));
		}
		
		//List that will contain the list of players that can  be selected
		JList listAvailable = new JList();		
		listAvailable.setModel(defList);
		JScrollPane jp = new JScrollPane(listAvailable);
		jp.setBounds(10, 32, 392, 430);
		frame.getContentPane().add(jp);		
		
		//ListModel to hold the selected players to be added to the users team list
		DefaultListModel <Player>selectList = new DefaultListModel<Player>();
		
		//List used to hold the user selections
		JList <Player>listSelected = new JList<Player>();
		listSelected.setModel(selectList);
		JScrollPane sp = new JScrollPane(listSelected);
		sp.setBounds(601, 32, 263, 430);
		frame.getContentPane().add(sp);
		
		//TextArea to display the budget value to user
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(468, 443, 66, 19);
		textArea.setText("$"+String.valueOf(budget));
		frame.getContentPane().add(textArea);
		
		//Button used to add players from displayed list pane to selected list pane
		JButton btnAdd = new JButton("Add Players");
		btnAdd.setBounds(449, 134, 122, 45);
		btnAdd.addActionListener(new ActionListener() {
			int gk = 0;
			int def = 0;
			int ford = 0;
			int mid = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(listAvailable.getSelectedIndex() != -1) {
				Player selected = (Player) listAvailable.getSelectedValue();
				if(selectList.size() == 15 || budget - selected.getPlayerPrice() < 0) {
					JOptionPane.showMessageDialog(frame, "You cannot add any more players...See budget or Player limit reached");
				}
				else {
					
				if(selected.getPlayerPosition() == "Defender") {
					if(def < 5) {
						selectList.addElement(selected);
						budget -= selected.getPlayerPrice();
						listSelected.setModel(selectList);
						def+=1; 
					}
					else {JOptionPane.showMessageDialog(frame, "You cannot add any more Defenders - Player limit reached");def = 0;}
				}else if(selected.getPlayerPosition() == "Forward") {
					if(ford < 3) {
						selectList.addElement(selected);
						budget -= selected.getPlayerPrice();
						listSelected.setModel(selectList);
						ford+=1;
					}
					else {JOptionPane.showMessageDialog(frame, "You cannot add any more Forwards - Player limit reached");ford = 0;}
				}else if(selected.getPlayerPosition() == "Midfielder") {
					if(mid < 5) {
						selectList.addElement(selected);
						budget -= selected.getPlayerPrice();
						listSelected.setModel(selectList);
						mid+=1;
						System.out.println(mid);
					}
					else {JOptionPane.showMessageDialog(frame, "You cannot add any more Midfielders - Player limit reached"); mid = 0;}				
				}else if(selected.getPlayerPosition() == "Goalkeeper") {
					if(gk < 2) {
						selectList.addElement(selected);
						budget -= selected.getPlayerPrice();
						listSelected.setModel(selectList);
						gk+=1;
					}
					else {JOptionPane.showMessageDialog(frame, "You cannot add any more Goalkeepers - Player limit reached");gk = 0;}				
				}
				textArea.setText(String.valueOf(budget));
				}
				}
			}
		});
		frame.getContentPane().add(btnAdd);
		
		//Button to remove selected players from selected list pane
		JButton btnRemove = new JButton("Remove Player");
		btnRemove.setBounds(449, 200, 122, 45);
		btnRemove.addActionListener(new ActionListener() {
			int gk = 0;
			int def = 0;
			int ford = 0;
			int mid = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				Player selected = (Player) listAvailable.getSelectedValue();
				int index = listSelected.getSelectedIndex();
				if(index != -1) {
					if(budget < 100 && budget + selected.getPlayerPrice() <= 100)
						budget += selected.getPlayerPrice();
				
					if(selected.getPlayerPosition() == "Defender") {
						budget -= selected.getPlayerPrice();
						selectList.removeElementAt(index);
						listSelected.setModel(selectList);
						if(def < 0) {
							def+=1;
						}
					}else if(selected.getPlayerPosition() == "Forward") {
						budget -= selected.getPlayerPrice();
						selectList.removeElementAt(index);
						listSelected.setModel(selectList);
						if(ford < 3) {
							ford+=1;
						}
					}else if(selected.getPlayerPosition() == "Midfielder") {
						System.out.println(mid);
						budget -= selected.getPlayerPrice();
						selectList.removeElementAt(index);
						listSelected.setModel(selectList);
						if(mid < 5) {
							mid+=1;
							System.out.println(mid);
						}				
					}else if(selected.getPlayerPosition() == "Goalkeeper") {
						budget -= selected.getPlayerPrice();
						selectList.removeElementAt(index);
						listSelected.setModel(selectList);
						if(gk < 2) {
							gk+=1;
						}				
					}
					else {System.out.println("Position not found");}
					textArea.setText(String.valueOf(budget));
				}else {
				budget = 100;
				textArea.setText(String.valueOf(budget));
				}
			}
			
		});
		frame.getContentPane().add(btnRemove);
		
		JLabel label_1 = new JLabel("Available Players");
		label_1.setBounds(64, 0, 175, 38);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Selected Players");
		label_2.setBounds(591, 3, 164, 32);
		frame.getContentPane().add(label_2);
		
		JLabel budgetLabel = new JLabel("Budget: ");
		budgetLabel.setBounds(412, 448, 46, 14);
		frame.getContentPane().add(budgetLabel);
		
		
		JLabel lblNewLabel_3 = new JLabel("Sort by: ");
		lblNewLabel_3.setBounds(54, 473, 46, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		//Button used to sort the current list of displayed players by team
		JButton teamSort = new JButton("Show by team");
		teamSort.setBounds(54, 505, 149, 19);
		teamSort.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				setList.clear();
				for(int i =0; i < defList.size(); i++) {
					setList.add((Player) defList.getElementAt(i));
				}
				defList.removeAllElements();
				
				setList.sort(new TeamComparator());
				for(int i = 0; i < setList.size(); i++) {
					defList.addElement(setList.get(i));
				}
				listAvailable.setModel(defList);
			}
		});
		frame.getContentPane().add(teamSort);
		
		//TextArea for the user to enter the name of the player they would like to search for
		textField = new JTextField();
		textField.setToolTipText("Enter Player Name");
		textField.setBounds(220, 542, 158, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//Button used to initiate search for entered player by name
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(54, 542, 149, 19);
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Player> listSet = new ArrayList<Player>();
				try {
					listSet = getFileData3();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			String playerName = textField.getText();
			if(playerName.length() == 0) 
				JOptionPane.showMessageDialog(frame, "Please add a Player Name");
			else {
				defList.removeAllElements();
				Player found = getPlayer(playerName, listSet);
				if(found == null) {
					JOptionPane.showMessageDialog(frame, "Player not found!!");
					textField.setText("");
				}
				else {
				defList.addElement(found);
				listAvailable.setModel(defList);	
				textField.setText("");}
				}
			}
			
			//RETURNS THE PLAYER THAT THE USER SEARCH FOR IF THE PLAYER IS NOT FOUND, RETURNS NULL
			private Player getPlayer(String playerName, ArrayList<Player> playerList) {
				// TODO Auto-generated method stub
				boolean isFound = false;
				Player foundPlayer = null;
				if(playerList.isEmpty())
					return null;
				
				for(Player in: playerList) {
					if(in.getPlayerName().equals(playerName)) {
						isFound = true;
						foundPlayer = in;
					}
						
				}
				if(isFound)
					return foundPlayer;
				else
					return null;
			}
		
		});
		frame.getContentPane().add(btnSearch);
		
		
		//Disposes the window and returns to main window
		JButton btnNewButton = new JButton("EXIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(129, 580, 637, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton removeAll = new JButton("Remove All");
		removeAll.setBounds(449, 271, 122, 45);
		removeAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				budget = 100;
				selectList.removeAllElements();	
				listSelected.setModel(selectList);
				textArea.setText(String.valueOf(budget));
			}
			
		});
		frame.getContentPane().add(removeAll);
		
		//When clicked, generates a new list of players for the user to select from
		JButton morePlayers = new JButton("Show more players");
		morePlayers.setBounds(431, 356, 153, 38);
		morePlayers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int tracer = setList.size();
				ArrayList<Player> more = new ArrayList<Player>();
				setList.clear();
				defList.removeAllElements();
				try {
					more = getFileData3();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i = tracer; i < tracer*2; i++) {
					setList.add(more.get(i));
				}
				for(int i = 0; i < setList.size(); i++) {
					defList.addElement(setList.get(i));
				}
				listAvailable.setModel(defList);				
			}			
		});
		frame.getContentPane().add(morePlayers);
		
		//Allows the user to load the initial list they started with
		JButton loadList = new JButton("Load initial list");
		loadList.setBounds(449, 74, 122, 38);
		loadList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Player> initial = new ArrayList<Player>();
				setList.clear();
				defList.removeAllElements();
				try {
					initial = getFileData3();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i = 0; i < initial.size()/2; i++) {
					setList.add(initial.get(i));
				}
				for(int i = 0; i < setList.size(); i++) {
					defList.addElement(setList.get(i));
				}
				listAvailable.setModel(defList);
			}
			
		});
		frame.getContentPane().add(loadList);
			
	}
}
