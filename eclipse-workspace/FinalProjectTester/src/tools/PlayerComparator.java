package tools;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player>{

	@Override
	public int compare(Player player1, Player player2) {
		// TODO Auto-generated method stub
		if(player1.getPlayerPrice() < player2.getPlayerPrice()) {
			return 1;
		}
		if(player1.getPlayerPrice() > player2.getPlayerPrice()) {
			return -1;
		}		
		return Integer.compare(player1.getPlayerPoints(), player2.getPlayerPoints());
	}

}
