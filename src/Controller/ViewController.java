package Controller;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import sun.tools.tree.NewInstanceExpression;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.sun.javafx.tools.ant.Callback;
import com.sun.source.tree.NewClassTree;

import View.DartBoard;
import View.DartBoardPoints;

public class ViewController implements Initializable {

	private int countPlayerThrow = 1;
	private int sumPoints = 0;
	private double[] rgb = { 1, 1, 1 };
	private Boolean flagPlayerInGame = false;
	private int countPlayer = 0;
	private int countThrow = 0;
	private int DartThrowCount;
	private int[] DartThrows = new int[3];
	private int Points;
	private ArrayList<Color> PlayerColors = new ArrayList<>();
	private ArrayList<int[]> AllPoints = new ArrayList<>();
	private DartBoard dart;
	private DartBoardPoints points;
	private PlayerManager playermanager = new PlayerManager();
	private ObservableList<String> items = FXCollections.observableArrayList();
	@SuppressWarnings("rawtypes")
	private ArrayList<ListView> PlayerLists = new ArrayList<>();

	private ObservableList<String> ls;
	private List<List<String>> PlayerColumns = new ArrayList<List<String>>();

	@FXML
	private Canvas DartboardCanvas;

	@FXML
	private Pane PaneGameInformation;

	@FXML
	private Label ShowSumPoints;

	@FXML
	private Label ShowDartThrow;

	@FXML
	private Label ShowOverallPoints;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	protected void DartboarClick(MouseEvent e) {

		int x = (int) e.getX();
		int y = (int) e.getY();

		points = new DartBoardPoints(DartboardCanvas.getHeight(), x, y);

		if (points.check_bulls_eye() != 0) {
			Points = points.check_bulls_eye();
		} else if (points.check_outer_bull() != 0) {
			Points = points.check_outer_bull();
		} else if (points.check_inner_ring() != 0) {
			Points = points.check_inner_ring();
		} else if (points.check_triple_ring() != 0) {
			Points = points.check_triple_ring();
		} else if (points.check_outer_ring() != 0) {
			Points = points.check_outer_ring();
		} else if (points.check_double_ring() != 0) {
			Points = points.check_double_ring();
		} else
			Points = 0;

		if (countThrow % (items.size() * 3) == 0) {
			countPlayer = 0;
		} else if (countThrow % 3 == 0) {
			countPlayer++;
		}

		Player CurrentPlayer = playermanager.getPlayer().get(countPlayer);

		int OldPoints = Integer.parseInt(PlayerColumns.get(countPlayer).get(PlayerColumns.get(countPlayer).size() - 1));
		String CurrentPoints = Integer.toString(CurrentPlayer.subPoints(Points, OldPoints));
		PlayerColumns.get(countPlayer).add(CurrentPoints);
		ls = FXCollections.observableList(PlayerColumns.get(countPlayer));

		PlayerLists.get(countPlayer).setItems(ls);

		sumPoints = sumPoints + Points;

		ShowOverallPoints.setText(CurrentPoints);
		ShowDartThrow.setText(Integer.toString(countPlayerThrow));
		ShowSumPoints.setText(Integer.toString(sumPoints));

		double Scatterheight = barChart.getHeight();
		int Xscatterplot = (int) (x / Scatterheight);
		double Yscatterplot = y / Scatterheight;

		xAxis = new NumberAxis(0, 700, 0.001);
		yAxis = new NumberAxis(0, 700, 0.001);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("2003");
		series1.getData().add(new XYChart.Data(Integer.toString(Xscatterplot), Yscatterplot));

		Scatterplot.getData().addAll(series1);
		
		if (countPlayerThrow == 3) {
			if (playermanager.getPlayer().size() >= 2) {
				//ShowOverallPoints.setText("");
				//ShowDartThrow.setText("");
				//ShowSumPoints.setText("");
				countPlayerThrow = 1;
				sumPoints = 0;
				this.Showdialog("NEXT PLAYER", "NEXT PLAYERS MOVE!");
				//if ok event
				return;
			}
		}
		
		countThrow++;
		countPlayerThrow++;

	}

	@FXML
	private Slider StartGameScore;

	@FXML
	protected void StartGameScoreClicked(MouseEvent e) {
	}

	@FXML
	private Slider EndGameScore;

	@FXML
	protected void EndGameScoreClicked(MouseEvent e) {
	}

	@FXML
	private Slider StartValueGameScore;

	@FXML
	protected void StartValueGameScoreClicked(MouseEvent e) {
	}

	@FXML
	private Button StartGame;

	@FXML
	private TabPane OverviewTabPane;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	protected void Start_Game(MouseEvent e) {

		if (flagPlayerInGame == false) {
			this.Showdialog("No Player in the Game", "Please create a player that is in the game");
			return;
		}

		PlayerName.setDisable(true);
		AddPlayer.setDisable(true);
		StartGameScore.setDisable(true);
		EndGameScore.setDisable(true);
		EndGameScore.setDisable(true);
		StartValueGameScore.setDisable(true);
		DownPlayer.setDisable(true);
		UpPlayer.setDisable(true);
		PlayerColor.setDisable(true);
		PlayerCollection.setDisable(true);
		DartboardCanvas.setVisible(true);

		PointOverview.setText("POINTS");
		// Add something in Tab
		HBox tabC_vBox = new HBox();

		int i = 0;
		for (Player player : playermanager.getPlayer()) {
			PlayerColumns.add(new ArrayList<String>());
			PlayerColumns.get(i).add(player.getName());
			PlayerColumns.get(i).add(Integer.toString(player.getStart()));
			PlayerLists.add(new ListView<String>());
			rgb[0] = player.getColor().getRed() * 100;
			rgb[1] = player.getColor().getGreen() * 100;
			rgb[2] = player.getColor().getBlue() * 100;
			ls = FXCollections.observableList(PlayerColumns.get(i));
			PlayerLists.get(i).setItems(ls);

			PlayerLists.get(i).setStyle("-fx-background-color: rgba(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ",0.9)");
			tabC_vBox.getChildren().add(PlayerLists.get(i));
			i++;
		}

		PointOverview.setContent(tabC_vBox);
		OverviewTabPane.getTabs().add(PointOverview);
		// OverviewTabPane.select(1);
	}

	@FXML
	private Button AddPlayer;

	@FXML
	private Button DownPlayer;

	@FXML
	protected void ChangePlayerDown(MouseEvent e) {
		if (items.size() <= 1) {
			return;
		}

		int index = PlayerCollection.getFocusModel().getFocusedIndex();
		items.add(index + 2, items.get(index));
		items.remove(index);

	}

	@FXML
	private Button UpPlayer;

	@FXML
	protected void ChangePlayerUp(MouseEvent e) {
		if (items.size() <= 1) {
			return;
		}

		int index = PlayerCollection.getFocusModel().getFocusedIndex();
		items.add(index - 1, items.get(index));
		items.remove(index + 1);

	}

	@FXML
	private ColorPicker PlayerColor;

	@FXML
	protected void PlayerColorClick(MouseEvent e) {

	}

	@FXML
	private ListView<String> PlayerCollection;

	@FXML
	private Tab PointOverview;

	@SuppressWarnings("unchecked")
	@FXML
	protected void addPlayer(MouseEvent e) {

		if (PlayerName.getText().isEmpty()) {
			this.Showdialog("Player Name", "Please enter the players name");
			return;
		}

		String NamePlayer = PlayerName.getText();

		if (items.contains(NamePlayer)) {
			this.Showdialog("Player Name", "Please enter a unique name");
			return;
		}

		javafx.scene.paint.Color color = PlayerColor.getValue();

		if (PlayerColors.contains(color)) {
			this.Showdialog("Player Colour", "Please define a unique Player Color");
			return;
		}

		PlayerColors.add(color);

		int Start = (int) (StartGameScore.getValue());
		int End = (int) (EndGameScore.getValue());
		int Start_value = (int) (StartValueGameScore.getValue());

		items.add(NamePlayer);
		PlayerCollection.setItems(items);

		playermanager.createPlayer(NamePlayer, color, Start, End, Start_value);

		flagPlayerInGame = true;
	}

	@FXML
	private TextField PlayerName;

	@FXML
	private NumberAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private ScatterChart<Number, Number> Scatterplot;

	@FXML
	protected void ShowPLayer(MouseEvent e) {

	}

	@FXML
	protected void ShowPoints(MouseEvent e) {

	}

	final static String itemA = "A";
	final static String itemB = "B";
	final static String itemC = "F";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Change static second canvas
		String austria = "Austria";
		String brazil = "Brazil";
		String france = "France";
		String italy = "Italy";
		String usa = "USA";

		int height = (int) DartboardCanvas.getHeight();
		dart = new DartBoard(DartboardCanvas, height);
		dart.DrawDartBoard();
		DartboardCanvas.setVisible(false);

		// GameInfoCanvas = new Canvas(720, 172);
		// PaneGameInformation.getChildren().add(GameInfoCanvas);

		this.setSlider(StartGameScore, 50);
		this.setSlider(EndGameScore, 0);
		this.setSlider(StartValueGameScore, 0);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("2003");
		series1.getData().add(new XYChart.Data(austria, 25601.34));
		series1.getData().add(new XYChart.Data(brazil, 20148.82));
		series1.getData().add(new XYChart.Data(france, 10000));
		series1.getData().add(new XYChart.Data(italy, 35407.15));
		series1.getData().add(new XYChart.Data(usa, 12000));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("2004");
		series2.getData().add(new XYChart.Data(austria, 57401.85));
		series2.getData().add(new XYChart.Data(brazil, 41941.19));
		series2.getData().add(new XYChart.Data(france, 45263.37));
		series2.getData().add(new XYChart.Data(italy, 117320.16));
		series2.getData().add(new XYChart.Data(usa, 14845.27));

		XYChart.Series series3 = new XYChart.Series();
		series3.setName("2005");
		series3.getData().add(new XYChart.Data(austria, 45000.65));
		series3.getData().add(new XYChart.Data(brazil, 44835.76));
		series3.getData().add(new XYChart.Data(france, 18722.18));
		series3.getData().add(new XYChart.Data(italy, 17557.31));
		series3.getData().add(new XYChart.Data(usa, 92633.68));

		barChart.getData().addAll(series1, series2, series3);

	}

	private void Showdialog(String title, String HeaderText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(HeaderText);
		alert.showAndWait();
	}

	private int CountDartThrow() {
		if (DartThrowCount == 3) {
			AllPoints.add(DartThrows);
			DartThrowCount = 0;
			sumup = 0;
		}
		return DartThrowCount++;
	}

	private void setSlider(Slider slider, int StartPosition) {
		slider.setMin(0);
		slider.setMax(100);
		slider.setValue(StartPosition);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setSnapToTicks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
	}

}
