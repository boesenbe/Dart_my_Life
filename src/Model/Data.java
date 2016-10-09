package Model;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;

public class Data {
	
	private Stage primaryStage = null; 
	private Map<String , String> namePwMap = null;
	
	public Data(Stage primaryStage) {
	      this.primaryStage = primaryStage;
	      this.namePwMap = new HashMap<>();
	   }
	
	 public Map<String, String> getNamePwMap() {
	      return namePwMap;
	   }
	
	public Stage getPrimaryStage() {
	      return primaryStage;
	   }
}
