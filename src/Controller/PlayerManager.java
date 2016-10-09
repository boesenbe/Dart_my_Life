package Controller;

import java.util.List;

import javafx.scene.paint.Color;

public class PlayerManager {

	private List<Player> AllPlayer;
	private Player player;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public PlayerManager() {

	}

	public void createPlayer(String name, Color color, int Start, int End, int Start_value) {
		player = new Player(name, color, Start, End, Start_value);
	}

	public void addPlayer() {
		AllPlayer.add(player);
	}

	public List<Player> getPlayer() {
		return AllPlayer;
	}

}
