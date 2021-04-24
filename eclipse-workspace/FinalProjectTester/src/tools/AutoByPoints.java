/*Name: Jermel Watson
 *Name: Allan Muir 
 *Name: Julio Castro
 *
 *Date: April 23, 2021 
 *
 *This class sorts the players by highest players first. The algorithm aims to
 *select the highest point players that will successfully create a list of 15 players
 *
 ****************************************************************************************************************************************************************
 *Functions implemented
 ****************************************************************************************************************************************************************
 *
 *getFileData2 - This is an exact copy of getFileData() and it returns a stack of Players from all four files.
 ****************************************************************************************************************************************************************
 *getLessPointPlayer(Player high, Stack<Player> transferStack)  -  This function return a Stack of type players with all the higher point players removed if the 
 *																   budget can't support their purchase.
 ****************************************************************************************************************************************************************
 *countNumTeamA(Player playerTeam, Stack<String> teamStack)     -  takes two parameter. 
 *																   A String which is the team of players and a team Stack that stores all the 
 *																   teams that have already been encountered. This function then counts the number of occurrences
 *																   of this team and returns a boolean value of true if 3 members from that team already exists.
 ****************************************************************************************************************************************************************
 *findHigh(Player current, Stack<Player> newPlayers)  		    -  finds and returns the player with the highest points to be added in the new Stack. Returns a 
 *														           player of type Player.
 ****************************************************************************************************************************************************************
 *lessPoints()													-  Takes 5 arguments Stack<Player> players, double budget, int mAX_TEAM_SIZE, int mIN_PRICE, Stack<String> teamStack
*																   Stack<Player> players - Current members left in the stack after the highest point players are 
*																   removed.
*                                                                  double budget - The constant budget of $100 that must not be exceeded
*																   int mAX_TEAM_SIZE - The maximum number of players that should be selected for the team
*																   int mIN_PRICE - the lowest price for players in the list. 
*																   This is a recursive function that selects the most expensive players for the team. If the budget is 
*																   exceeded the list with the second highest players is passed in another call. This return a Stack of type 
*                                                                  Player
****************************************************************************************************************************************************************
 **/


package tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AutoByPoints implements ActionListener{

	public Stack <Player> getFileData2() throws FileNotFoundException {
		Stack <Player> players = new Stack<Player>();
		
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
						players.push(new Defender(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
		
					}
					if(fileName.equals("forwards.txt")) {
						players.push(new Forwards(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
		
					}
					if(fileName.equals("goalKeepers.txt")) {
						players.push(new Goalkeeper(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
				
					}
					if(fileName.equals("midfielders.txt")) {
						players.push(new Midfielder(row[0], row[1], Integer.parseInt(row[2]), Double.parseDouble(row[3]), ""));
		
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return players;
}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JPanel newPanel = new JPanel();
		JFrame newFrame = new JFrame();
		JTextArea textArea = new JTextArea();
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		newPanel.setLayout(new BorderLayout());
		newFrame.setBounds(300, 200, 700, 700);
		newFrame.setVisible(true);
		newFrame.add(newPanel, BorderLayout.CENTER);
		textArea.setEditable(false);
		newPanel.add(sp);
		
		
		JLabel newLabel = new JLabel("Automatic Selection - Highest Point Player from Last Season");
		newLabel.setFont(new Font("Times New Romans", Font.BOLD, 21));
		newLabel.setBounds(300, 78, 250, 40);
		newPanel.add(newLabel, BorderLayout.NORTH);
		
		//Allows the window to close without closing the whole program
		JButton exitProgram = new JButton("Exit");
		exitProgram.setBounds(65, 345, 300, 40);
		newPanel.add(exitProgram, BorderLayout.SOUTH);
		exitProgram.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					newFrame.dispose();
			}
		});
		
		Stack <Player>newPlayers = new Stack<Player>();
		Stack <Player>tempStack = new Stack<Player>();
		Stack <Player>newStack = new Stack<Player>();
		Stack <String>teamStack = new Stack<String>();
		//Stack <Player>transferStack = new Stack<Player>();
		//Stack <Player> make = new Stack<Player>();
		
		
		int gk = 0;
		int def = 0;
		int ford = 0;
		int mid = 0;
		int count = 0;
		int MAX_TEAM_SIZE = 15;
		int MIN_PRICE = 4;
		double BUDGET = 100.0;
		double currentBudget = BUDGET;
		
		try {
			newPlayers = getFileData2();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
//IMPLEMENT SELECTION		
			while(count < MAX_TEAM_SIZE) {
				
				if(newPlayers.isEmpty()) {
						System.out.println("Out of players!!!");
						break;
				}
				//System.out.println(currentBudget+"***BUDGET");
		
				if(currentBudget < MIN_PRICE && count < MAX_TEAM_SIZE) {
					
					while(!(newPlayers.isEmpty()) )
						{tempStack.push(newPlayers.pop());}
					
					newStack = lessPoints(tempStack, BUDGET, MAX_TEAM_SIZE, MIN_PRICE, teamStack);
					break;
				}
				else {
					Player current = newPlayers.peek();
					//System.out.println(current.getPlayerPosition()+"***Position");
					
					if(current.getPlayerPosition().equals("Defender") && def < 5) {
						if(countNumTeam(current, teamStack)) {
							newPlayers.pop();															
						}			
						else {
							teamStack.push(current.getPlayerTeam());
							Player highPointPlayer = findHigh(current, newPlayers);
							newPlayers = getLessPointPlayer(highPointPlayer, newPlayers);
							currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
							//System.out.println(highPointPlayer.getPlayerPoints() + "--Points--"+ highPointPlayer.getPlayerName());
							newStack.push(highPointPlayer);
							def++;	
							count++;
						}
					}
					else if ((current.getPlayerPosition() == "Forward")&& ford < 3) {
						if(countNumTeam(current, teamStack)) {
							newPlayers.pop();														
						}			
						else {
							teamStack.push(current.getPlayerTeam());
							Player highPointPlayer = findHigh(current, newPlayers);
							newPlayers = getLessPointPlayer(highPointPlayer, newPlayers);
							currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
							//System.out.println(highPointPlayer.getPlayerPoints() + "--Points--"+ highPointPlayer.getPlayerName());
							newStack.push(highPointPlayer);
							ford++;	
							count++;
						}
					}
					else if((current.getPlayerPosition() == "Midfielder" && mid < 5)){			
						if(countNumTeam(current, teamStack)) {
							newPlayers.pop();														
						}			
						else {
							teamStack.push(current.getPlayerTeam());
							Player highPointPlayer = findHigh(current, newPlayers);
							newPlayers = getLessPointPlayer(highPointPlayer, newPlayers);
							currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
							//System.out.println(highPointPlayer.getPlayerPoints() + "--Points--"+ highPointPlayer.getPlayerName());
							newStack.push(highPointPlayer);
							mid++;	
							count++;
						}						
					}
					else if((current.getPlayerPosition() == "Goalkeeper" && gk < 2) ) {
						if(countNumTeam(current, teamStack)) {
							newPlayers.pop();														
						}			
						else {
							teamStack.push(current.getPlayerTeam());
							Player highPointPlayer = findHigh(current, newPlayers);
							newPlayers = getLessPointPlayer(highPointPlayer, newPlayers);
							currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
							//.out.println(highPointPlayer.getPlayerPoints() + "--Points--"+ highPointPlayer.getPlayerName());
							newStack.push(highPointPlayer);
							gk++;	
							count++;
						}						
					}
					tempStack.push(newPlayers.pop());				
				}
			}
			
			newStack.sort(new PlayerComparatorPoints());
			
			textArea.setText("");
			
			
			for(Player player: newStack) {
				textArea.append("\n******************Player****************"
						+ "\n" +player.toString()+ "\n");
			}
	}


	private Stack<Player> getLessPointPlayer(Player high, Stack<Player> transferStack) {
		Stack<Player> make = new Stack<Player>();
		
		if(transferStack.isEmpty())
			return null;
		
			 for(Player newPlayer: transferStack) {
				if(!(high.equals(newPlayer))) {
					make.push(newPlayer);
				}
		}			 
		return make;
	}

	private boolean countNumTeam(Player playerTeam, Stack<String> teamStack) {
		
		int count = 0;
		if(teamStack.isEmpty())
			return false;
		for(String team: teamStack) {
			if(playerTeam.getPlayerTeam().equals(team)){
				count++;
			}
		}
		if(count < 3) {
			return false;
		}
		else {
			return true;
		}
	}

	private Player findHigh(Player current, Stack<Player> newPlayers) {
		
		Player highest = current;
		
		if(!(newPlayers.isEmpty())) {
			for(Player find: newPlayers) {
				if(find.getPlayerPosition().equals(highest.getPlayerPosition())) {
					if(find.getPlayerPoints() > highest.getPlayerPoints()) {
						highest = find;
					}					
				}
			}
		}
		return highest;
	}

	private Stack<Player> lessPoints(Stack<Player> players, double budget, int mAX_TEAM_SIZE, int mIN_PRICE, Stack<String> teamStack) {
	
		Stack <Player> Make = new Stack<Player>();
		if(players.isEmpty())
			return null;
		if(players.size() < mAX_TEAM_SIZE) {
			System.out.println("Out of players!!!");
			return null;
		}

		Stack<Player> newStack = new Stack<Player>();
		Stack<Player> tempStack = new Stack<Player>();
		Stack<Player> transfer = new Stack<Player>();
		
		for(Player move: players) {
			transfer.push(move);
		}
		
		
		int count = 0;
		int def = 0;
		int ford = 0;
		int mid = 0;
		int gk = 0;
		double currentBudget = budget;
		
		
		while(count < mAX_TEAM_SIZE) {
			
			if(players.isEmpty()) {
					System.out.println("Out of players!!!");
					break;
			}
			
			if(currentBudget < mIN_PRICE && count < mAX_TEAM_SIZE) {
				
				while(!(players.isEmpty()) )
				{tempStack.push(players.pop());}
				
				newStack = lessPoints(tempStack, budget, mAX_TEAM_SIZE, mIN_PRICE, teamStack);
				break;
			}
			Player current = players.peek();
			
			
			if(current.getPlayerPosition() == "Defender" && def < 5){
				if(countNumTeam(current, teamStack)) {
					players.pop();														
				}			
				else {
					teamStack.push(current.getPlayerTeam());
					Player highPointPlayer = findHigh(current, players);
					players = getLessPointPlayer(highPointPlayer, players);
					currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
					newStack.push(highPointPlayer);
					def++;	
					count++;
				}			
			}
			else if ((current.getPlayerPosition() == "Forward" && ford < 3)){
				if(countNumTeam(current, teamStack)) {
					players.pop();														
				}			
				else {
					teamStack.push(current.getPlayerTeam());
					Player highPointPlayer = findHigh(current, players);
					players = getLessPointPlayer(highPointPlayer, players);
					currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
					newStack.push(highPointPlayer);
					ford++;	
					count++;
				}
			}
			else if((current.getPlayerPosition() == "Midfielder" && mid < 5) ) {
				
				if(countNumTeam(current, teamStack)) {
					players.pop();														
				}			
				else {
					teamStack.push(current.getPlayerTeam());
					Player highPointPlayer = findHigh(current, players);
					players = getLessPointPlayer(highPointPlayer, players);
					currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
					newStack.push(highPointPlayer);
					mid++;	
					count++;
				}							
			}
			else if((current.getPlayerPosition() == "Goalkeeper" && gk < 2)) {
				
				if(countNumTeam(current, teamStack)) {
					players.pop();														
				}			
				else {
					teamStack.push(current.getPlayerTeam());
					Player highPointPlayer = findHigh(current, players);
					players = getLessPointPlayer(highPointPlayer, players);
					currentBudget = currentBudget - highPointPlayer.getPlayerPrice();
					newStack.push(highPointPlayer);
					gk++;	
					count++;
				}								
			}if(!(players.isEmpty())) {
				tempStack.push(players.pop());				
			}
		}
		return newStack;
	}
}
