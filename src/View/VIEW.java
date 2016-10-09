package View;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import Model.Data;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VIEW {

	public static final GraphicsDevice Monitorsize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
	private Scene scene;
	private Data data;

	public VIEW(Data data) {
		this.data = data;

		try {
			VBox page = (VBox) FXMLLoader.load(VIEW.class.getResource("GUI_MAIN.fxml"));
			scene = new Scene(page);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void show() {
		data.getPrimaryStage().setTitle("DART For the Free!");
		data.getPrimaryStage().setScene(scene);
		data.getPrimaryStage().show();
	}

}
