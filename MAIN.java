import View.VIEW;
import Model.Data;

import javafx.application.Application;
import javafx.stage.Stage;
 
public class MAIN extends Application {
	
	 public static void main(String[] args) {
	        launch(args);
	    }
	 
    @Override
    public void start(Stage primaryStage) {
    	
    	Data data = new Data(primaryStage);
    	
    	VIEW viewcontroller = new VIEW(data);
    	viewcontroller.show();
    }

}
