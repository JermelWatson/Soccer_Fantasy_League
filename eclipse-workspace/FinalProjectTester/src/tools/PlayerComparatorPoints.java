package tools;

import java.util.Comparator;

public class PlayerComparatorPoints implements Comparator<Player> {

	@Override
	public int compare(Player player1, Player player2) {
		// TODO Auto-generated method stub
		if(player1.getPlayerPoints() < player2.getPlayerPoints()) {
			return 1;
		}
		if(player1.getPlayerPoints() > player2.getPlayerPoints()) {
			return -1;
		}		
		return Double.compare(player1.getPlayerPrice(), player2.getPlayerPrice());
	}

}
