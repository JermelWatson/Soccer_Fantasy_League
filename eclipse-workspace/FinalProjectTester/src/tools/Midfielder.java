package tools;

public class Midfielder extends Player{
	String name;
	String team;
	int points;
	double price;
	String position;
	
	public Midfielder(String name, String team, int points, double price, String position) {
		this.name = name;
		this.team = team;
		this.points =points;
		this.price = price;
		this.position = "Midfielder";
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Player Name: "+ name +
				"\n__Team: " + team +
				"\n__Points: " + points +
				"\n__Price: " + price +
				"\n__Position: " + position;
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getPlayerTeam() {
		// TODO Auto-generated method stub
		return team;
	}

	@Override
	public String getPlayerPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getPlayerPoints() {
		// TODO Auto-generated method stub
		return points;
	}

	@Override
	public double getPlayerPrice() {
		// TODO Auto-generated method stub
		return price;
	}

}
