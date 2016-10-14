package Controller;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class Player {

	private String name;
	private Color color;
	private int Start;
	private int End;
	private int Start_value;
	private ArrayList<Integer> AllPoints;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Player(String name, Color color, int Start, int End, int Start_value) {
		this.setName(name);
		this.setColor(color);
		this.setStart(Start);
		this.setEnd(End);
		this.setStart_value(Start_value);
		AllPoints = new ArrayList<>();
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
	
	public void addPoints(int CurrentPoints){
		AllPoints.add(CurrentPoints);
	}
	
	public  ArrayList<Integer> getPoints(){
		return AllPoints;
	}
	
	public int subPoints(int CurrentPoints, int OldPoints) {
		int NewScore = OldPoints - CurrentPoints;
		return NewScore;
	}
	
	public String getPointstoString() {
        return email;
    }
	
	public Boolean checkEnd() {
		if(Start == End){
			return true;
		}else{
			return false;
		}
	}
	

}
