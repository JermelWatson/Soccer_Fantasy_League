package tools;

import java.util.Comparator;

public class TeamComparator implements Comparator<Player>{

	@Override
	public int compare(Player player1, Player player2) {
		// TODO Auto-generated method stub
		if(player1.getPlayerTeam().compareTo(player2.getPlayerTeam()) < 0) {
			return -1;
		}
		if(player1.getPlayerTeam().compareTo(player2.getPlayerTeam()) > 0){
			return 1;
		}		
		return Integer.compare(player2.getPlayerPoints(), player1.getPlayerPoints());
	}


}
