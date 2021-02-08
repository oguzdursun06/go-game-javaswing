import java.awt.Color;

public class Player {
	private int goStones;
	private String color;
	public static int passCount = 0;
	public Player(String color){
		this.color = color;
		goStones = 0;
	}

	public int getGoStones() {
		return goStones;
	}

	public void setGoStones(int goStones) {
		this.goStones = goStones;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	
}
