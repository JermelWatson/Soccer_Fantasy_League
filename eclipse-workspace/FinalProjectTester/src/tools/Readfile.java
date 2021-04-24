/*Name: Jermel Watson
 *Name: Allan Muir 
 *Name: Julio Castro
 *
 *Date: April 23, 2021 
 * 
 * This file implements the selection of players from four given files. These players are then chosen 
 * by the highest priced players first. This action is continued until a list of 15 players can be 
 * selected without going over the budget. The players that are selected are:
 * 
 * Middle fielders - 5, Defenders - 5, Forwards - 3, Goal keepers - 2
 * 
 * implemented functions
 * ***********************************************************************************************************************************************************
 * getFileData - 												 *returns a Stack of type Player that includes all the players from all 4 files
 * ************************************************************************************************************************************************************
 * countNumTeamA(Player playerTeam, Stack<String> teamStack)  -  *takes two parameter. 
 *																 * A String which is the team of players and a team Stack that stores all the 
 *																 * teams that have already been encountered. This function then counts the number of occurrences
 *																 * of this team and returns a boolean value of true if 3 members from that team already exists.
 ***************************************************************************************************************************************************************
*lessExpensive() 												 *Takes 5 arguments
*																 *Stack<Player> players - Current members left in the stack after the most expensive players are popped()
*																 *double budget - The constant budget of $100 that must not be exceeded
*																 *int mAX_TEAM_SIZE - The maximum number of players that should be selected for the team
*																 *int mIN_PRICE - the lowest price for players in the list. 
*																 *This is a recurssive function that selects the most expensive players for the team. If the budget is exceeded
*																  the list with the second hightest players is passed in another call. This return a Stack of type Player
******************************************************************************************************************************************************************** * */
package tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Readfile implements ActionListener{	
	
	//Returns a Stack container with all the Players from all four files
	public Stack <Player> getFileData() throws FileNotFoundException {
		Stack <Player> players = new Stack<Player>();
		
		FileReader theFile;
		BufferedReader fileBuffer  = null;

		try {			
			String line;
			File sourceFolder = new File("C:\\Users\\User\\eclipse-workspace\\FinalProjectTester\\TextFiles");
			for( File sourceFile: sourceFolder.listFiles()) {
				String fileName = sourceFile.getName();
				
				theFile = new FileReader(fileName);
				fileBuffer = new BufferedReader(theFile);
			
				while((line = fileBuffer.readLine()) != null) {
					String[] row = line.split("#");
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
			// catch block
			e1.printStackTrace();
		}
		finally {
			if (fileBuffer != null) {
				try {
					fileBuffer.close();
				} catch (IOException e1) {
					//catch block
					e1.printStackTrace();
				}
			}
		}
		return players;
}
	//Action triggered by the Automatic selection by Price button
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JPanel newPanel = new JPanel();
		JFrame newFrame = new JFrame();
		JTextArea textArea = new JTextArea();
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		newFrame.setSize(700, 700);
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setBounds(330, 200, 700, 700);
		
		newPanel.setLayout(new BorderLayout());
		newFrame.setVisible(true);
		newFrame.add(newPanel, BorderLayout.CENTER);
		textArea.setEditable(false);
		newPanel.add(sp);
		
		
		JLabel newLabel = new JLabel("Automatic Selection Sorted by Highest Priced Players Available");
		newLabel.setBounds(200, 25, 250, 40);
		newLabel.setFont(new Font("Times New Romans", Font.BOLD, 22));
		newPanel.add(newLabel, BorderLayout.NORTH);
		
		//Allows the window to close without closing the whole program
		JButton exitProgram = new JButton("Exit");
		exitProgram.setBounds(65, 345, 300, 40);
		newPanel.add(exitProgram, BorderLayout.SOUTH);
		exitProgram.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Disposing the frame when exit is hit instead of closing the whole program.
					newFrame.dispose();				
			}
			
		});
		
		Stack <Player>players = new Stack<Player>();
		Stack <Player>tempStack = new Stack<Player>();
		Stack <Player>newStack = new Stack<Player>();
		Stack <String>teamsStack = new Stack <String>();
		
		int gk = 0;
		int def = 0;
		int ford = 0;
		int mid = 0;
		int count = 0;
		double BUDGET = 100.0;
		int MAX_TEAM_SIZE = 15;
		int MIN_PRICE = 4;
		double currentBudget = BUDGET;
		try {
			players = getFileData();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
//IMPLEMENT SELECTION //        / /         //         //         //      / /    //      //    / /     / /   / /      / /     //    //  
		
		//Sorts the player stack by highest player for easier selection
		players.sort(new PlayerComparator());
		
			while(count < MAX_TEAM_SIZE) {
				if(players.isEmpty()) {
					System.out.println("Out of players!!!");
					break;
				}
				if(currentBudget < MIN_PRICE && count < MAX_TEAM_SIZE) {
					while(!(players.isEmpty()) )
						{tempStack.push(players.pop());}
					newStack = lessExpensive(tempStack, BUDGET, MAX_TEAM_SIZE, MIN_PRICE);
					break;
				}
				else {
					Player current = players.peek();
					
					if(current.getPlayerPosition() == "Defender" && def < 5 && ((currentBudget - current.getPlayerPrice()) > 0 )) {
						if(countNumTeamA(current, teamsStack)) {
							players.pop();														
						}			
						else {
							teamsStack.push(current.getPlayerTeam());
							currentBudget = currentBudget - current.getPlayerPrice();
							newStack.push(players.pop());
							def++;	
							count++;
						}					
					}
					else if ((current.getPlayerPosition() == "Forward" && ford < 3) && (currentBudget - current.getPlayerPrice() > 0 )) {
						
						if(countNumTeamA(current, teamsStack)) {
							players.pop();														
						}			
						else {
							teamsStack.push(current.getPlayerTeam());
							currentBudget = currentBudget - current.getPlayerPrice();
							newStack.push(players.pop());
							ford++;	
							count++;
						}	
					}
					else if((current.getPlayerPosition() == "Midfielder" && mid < 5) && (currentBudget - current.getPlayerPrice() > 0 )){
						
						if(countNumTeamA(current, teamsStack)) {
							players.pop();														
						}			
						else {
							teamsStack.push(current.getPlayerTeam());
							currentBudget = currentBudget - current.getPlayerPrice();
							newStack.push(players.pop());
							mid++;	
							count++;
						}						
					}
					else if((current.getPlayerPosition() == "Goalkeeper" && gk < 2) && (currentBudget - current.getPlayerPrice() > 0 )) {
						
						if(countNumTeamA(current, teamsStack)) {
							players.pop();														
						}			
						else {
							teamsStack.push(current.getPlayerTeam());
							currentBudget = currentBudget - current.getPlayerPrice();
							newStack.push(players.pop());
							gk++;	
							count++;
						}						
					}
					tempStack.push(players.pop());				
				}
			}
			newStack.sort(new PlayerComparator());
			
			textArea.setText("");
			
			for(Player player: newStack) {
				textArea.append("\n******************Player****************"
						+ "\n" +player.toString()+ "\n");
			}
	}
	
	//RETURNS TRUE IS THREE MEMBERS OF A TEAM ALREADY EXISTS
	private boolean countNumTeamA(Player playerTeam, Stack<String> teamStack) {
		// TODO Auto-generated method stub
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
	
//RECURSSIVE CALL OF SELECTION ALGORITHM
	private Stack<Player> lessExpensive(Stack<Player> players, double budget, int mAX_TEAM_SIZE, int mIN_PRICE) {
		
		if(players.isEmpty())
			return null;                           
		if(players.size() < mAX_TEAM_SIZE) {
			System.out.println("Out of players!!!");
			return null;
		}

		Stack<Player> newStack = new Stack<Player>();
		Stack<Player> tempStack = new Stack<Player>();
		Stack<String> teamsStack = new Stack<String>();
		
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
				newStack = lessExpensive(tempStack, budget, mAX_TEAM_SIZE, mIN_PRICE);
				break;
			}
			Player current = players.peek();
			
			if(current.getPlayerPosition() == "Defender" && def < 5 && ((currentBudget - current.getPlayerPrice()) > 0 )) {
				if(countNumTeamA(current, teamsStack)) {
					players.pop();														
				}			
				else {
					teamsStack.push(current.getPlayerTeam());
					currentBudget = currentBudget - current.getPlayerPrice();
					newStack.push(players.pop());
					def++;	
					count++;
				}				
			}
			else if ((current.getPlayerPosition() == "Forward" && ford < 3) && (currentBudget - current.getPlayerPrice() > 0 )) {
				if(countNumTeamA(current, teamsStack)) {
					players.pop();														
				}			
				else {
					teamsStack.push(current.getPlayerTeam());
					currentBudget = currentBudget - current.getPlayerPrice();
					newStack.push(players.pop());
					ford++;	
					count++;
				}	
			}
			else if((current.getPlayerPosition() == "Midfielder" && mid < 5) && (currentBudget - current.getPlayerPrice() > 0 )){
				
				if(countNumTeamA(current, teamsStack)) {
					players.pop();														
				}			
				else {
					teamsStack.push(current.getPlayerTeam());
					currentBudget = currentBudget - current.getPlayerPrice();
					newStack.push(players.pop());
					mid++;	
					count++;
				}							
			}
			else if((current.getPlayerPosition() == "Goalkeeper" && gk < 2) && (currentBudget - current.getPlayerPrice() > 0 )) {
				
				if(countNumTeamA(current, teamsStack)) {
					players.pop();														
				}			
				else {
					teamsStack.push(current.getPlayerTeam());
					currentBudget = currentBudget - current.getPlayerPrice();
					newStack.push(players.pop());
					gk++;	
					count++;
				}								
			}
			tempStack.push(players.pop());
		}
		return newStack;
	}
}
