package Controller;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class Player {

	private String name;
	private Color color;
	private Boolean State;
	private int StartThrow;
	private int EndThrow;
	private int Start;
	private int CurrentThrow;

	public Player(String name, Color color, int StartThrow, int EndThrow, int Start) {
		this.setName(name);
		this.setColor(color);
		this.setStartThrow(StartThrow);
		this.setEndThrow(EndThrow);
		this.setStart(Start);
		this.setStartEndState(false);
		this.setCurrentThrow(0);
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

	public int getStartThrow() {
		return StartThrow;
	}

	public void setStartThrow(int startthrow) {
		StartThrow = startthrow;
	}

	public int getEndThrow() {
		return EndThrow;
	}

	public void setEndThrow(int endthrow) {
		EndThrow = endthrow;
	}

	public int getStart() {
		return Start;
	}

	public void setStart(int start) {
		Start = start;
	}

	public Boolean getStartEndState() {
		return State;
	}

	public void setStartEndState(Boolean state) {
		State = state;
	}

	public void setCurrentThrow(int currentthrow) {
		CurrentThrow = currentthrow;
	}

	public int getCurrentThrow() {
		return CurrentThrow;
	}

	public int subPoints(int CurrentPoints, int OldPoints) {
		int NewScore = OldPoints - CurrentPoints;
		return NewScore;
	}

}
