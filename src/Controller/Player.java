package Controller;

import javafx.scene.paint.Color;

public class Player {

	private String name;
	private Color color;
	private int Start;
	private int End;
	private int Start_value;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Player(String name, Color color, int Start, int End, int Start_value) {
		this.setName(name);
		this.setColor(color);
		this.setStart(Start);
		this.setEnd(End);
		this.setStart_value(Start_value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getStart() {
		return Start;
	}

	public void setStart(int start) {
		Start = start;
	}

	public int getEnd() {
		return End;
	}

	public void setEnd(int end) {
		End = end;
	}

	public int getStart_value() {
		return Start_value;
	}

	public void setStart_value(int start_value) {
		Start_value = start_value;
	}
	
	

}