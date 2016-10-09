package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import View.DartBoard;
import View.DartBoardPoints;

public class ViewController implements Initializable {

	private int DartThrowCount;
	private int[] DartThrows = new int[3];
	private int Points;
	private int sumup = 0;
	private ArrayList<int[]> AllPoints = new ArrayList<>();
	private DartBoard dart;
	private DartBoardPoints points;
	private PlayerManager playermanager = new PlayerManager();
	private ObservableList<String> items = FXCollections.observableArrayList();
	@SuppressWarnings("rawtypes")
	private ObservableList<TableColumn> PlayerColumn = FXCollections.observableArrayList();

	@FXML
	private Canvas DartboardCanvas;

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

		System.out.println("Points Currently ::" + this.getCurrentPoints());

		double Scatterheight = barChart.getHeight();
		int Xscatterplot = (int) (x / Scatterheight);
		double Yscatterplot = y / Scatterheight;

		xAxis = new NumberAxis(0, 700, 0.001);
		yAxis = new NumberAxis(0, 700, 0.001);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("2003");
		series1.getData().add(new XYChart.Data(Integer.toString(Xscatterplot), Yscatterplot));

		Scatterplot.getData().addAll(series1);

	}

	private int getCurrentPoints() {
		int DartThrow = this.CountDartThrow();

		DartThrows[DartThrow] = Points;

		sumup = DartThrows[DartThrow] + sumup;

		return sumup;
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
	protected void Start_Game(MouseEvent e) {
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

		// add Table column to player
		@SuppressWarnings("rawtypes")
		TableColumn firstNameCol = new TableColumn(NamePlayer);
		PlayerColumn.add(firstNameCol);

		items.add(NamePlayer);
		PlayerCollection.setItems(items);
		ScoreHis.getColumns().add(firstNameCol);

		// playermanager.createPlayer(NamePlayer, color, Start, End,
		// Start_value);
	}

	@FXML
	private TextField PlayerName;

	@FXML
	private TableView<String> ScoreHis;

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
		String austria = "Austria";
		String brazil = "Brazil";
		String france = "France";
		String italy = "Italy";
		String usa = "USA";

		int height = (int) DartboardCanvas.getHeight();
		dart = new DartBoard(DartboardCanvas, height);
		dart.DrawDartBoard();

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

}
