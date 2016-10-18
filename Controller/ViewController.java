package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;

import java.util.ResourceBundle;

import com.sun.javafx.charts.Legend;

import View.DartBoard;
import View.DartBoardPoints;

public class ViewController implements Initializable {

	private int countPlayerThrow = 1;
	private int sumPoints = 0;
	private Boolean flagPlayerInGame = false;
	private int countPlayer = 0;
	private int countThrow = 0;
	private String CurrentPointsString;
	private ArrayList<Color> PlayerColors = new ArrayList<>();
	private DartBoard dart;
	private DartBoardPoints points;
	private PlayerManager playermanager = new PlayerManager();
	private ObservableList<String> items = FXCollections.observableArrayList();
	@SuppressWarnings("rawtypes")
	private ArrayList<ListView> PlayerLists = new ArrayList<>();

	private ObservableList<String> ls;
	private List<List<String>> PlayerColumns = new ArrayList<List<String>>();
	@SuppressWarnings("rawtypes")
	private ArrayList<XYChart.Series> BarChartData = new ArrayList<>();
	private XYChart.Data<String, Number> ChartData;
	@SuppressWarnings("restriction")
	private ArrayList<Legend> Legend = new ArrayList<>();

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
	
	@FXML
	private Label NamePlayer;

	@FXML
	private Label ShowThrowHint;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	protected void DartboarClick(MouseEvent e) {

		if (countThrow % (items.size() * 3) == 0) {
			countPlayer = 0;
		} else if (countThrow % 3 == 0) {
			countPlayer++;
		}

		int x = (int) e.getX();
		int y = (int) e.getY();

		points = new DartBoardPoints(DartboardCanvas.getHeight(), x, y);
		GraphicsContext gc = DartboardCanvas.getGraphicsContext2D();
		Player CurrentPlayer = playermanager.getPlayer().get(countPlayer);
		
		

		// Check normal Dart throw Points
		int Point = checknomalPoints(points);

		// Method to check the start Condition
		Point = checkStartCondition(CurrentPlayer, Point);

		int OldPoints = Integer.parseInt(PlayerColumns.get(countPlayer).get(PlayerColumns.get(countPlayer).size() - 1));
		int CurrentPoints = CurrentPlayer.subPoints(Point, OldPoints);
		CurrentPointsString = Integer.toString(CurrentPoints);

		// Set Bar chart Data

		XYChart.Data<String, Number> data = new XYChart.Data("", Integer.parseInt(CurrentPointsString));

		BarchartListner(CurrentPlayer, data);

		BarChartData.get(countPlayer).getData().add(data);

		// Method to check the end Condition
		checkEndCondition(CurrentPlayer, OldPoints, CurrentPoints, gc, x, y);

		PlayerColumns.get(countPlayer).add(CurrentPointsString);
		ls = FXCollections.observableList(PlayerColumns.get(countPlayer));

		PlayerLists.get(countPlayer).setItems(ls);

		sumPoints = sumPoints + Point;

		if(CurrentPlayer.getCurrentThrow()==0){
			GridPaneGameInfo.setStyle("-fx-background-color: "+PlayerColortoString(CurrentPlayer)+";");
		}
		ShowOverallPoints.setText(CurrentPointsString);
		ShowDartThrow.setText(Integer.toString(countPlayerThrow));
		ShowSumPoints.setText(Integer.toString(sumPoints));
		NamePlayer.setText(CurrentPlayer.getName()); 

		// Show next Throw Hint
		if (CurrentPoints <= 170) {
			ShowThrowHint.setText(FinishTable.FinishTable.get(CurrentPoints));
		}

		
		drawThrowPoint(gc, x, y);
		
		// Check for round change
		if (countPlayerThrow == 3) {
			if (playermanager.getPlayer().size() >= 2) {
				countPlayerThrow = 1;
				sumPoints = 0;
				this.Showdialog("NEXT PLAYER", "NEXT PLAYERS MOVE!");
				ShowOverallPoints.setText("");
				ShowDartThrow.setText("");
				ShowSumPoints.setText("");
				ShowThrowHint.setText("");
				NamePlayer.setText("");

				countThrow++;
				return;
			}
		}
			
		countPlayerThrow++;
		countThrow++;

	}
	
	private String PlayerColortoString(Player CurrenPlayer){
		
		int red = (int) (CurrenPlayer.getColor().getRed()*255);
		int green = (int) (CurrenPlayer.getColor().getGreen()*255);
		int blue = (int) (CurrenPlayer.getColor().getBlue()*255);
		
		String ColorString = "rgb("+red+","+green+","+blue+")";
		return ColorString;
	}

	private void BarchartListner(Player CurrentPlayer, XYChart.Data<String, Number> data) {

		data.nodeProperty().addListener(new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {

				newNode.setStyle("-fx-bar-fill: "+PlayerColortoString(CurrentPlayer)+";");

			}
		});
	}

	private int checknomalPoints(DartBoardPoints points) {
		if (points.check_bulls_eye() != 0) {
			return points.check_bulls_eye();
		} else if (points.check_outer_bull() != 0) {
			return points.check_outer_bull();
		} else if (points.check_inner_ring() != 0) {
			return points.check_inner_ring();
		} else if (points.check_triple_ring() != 0) {
			return points.check_triple_ring();
		} else if (points.check_outer_ring() != 0) {
			return points.check_outer_ring();
		} else if (points.check_double_ring() != 0) {
			return points.check_double_ring();
		} else
			return 0;
	}

	private int checkStartCondition(Player CurrentPlayer, int Point) {
		if (CurrentPlayer.getStartThrow() == 1 && CurrentPlayer.getStartEndState() == false
				&& points.check_double_ring() != 0) {
			CurrentPlayer.setStartEndState(true);
			return points.check_double_ring();

		} else if (CurrentPlayer.getStartThrow() == 2 && CurrentPlayer.getStartEndState() == false
				&& points.check_triple_ring() != 0) {
			CurrentPlayer.setStartEndState(true);
			return points.check_triple_ring();

		} else if (CurrentPlayer.getStartEndState() == false) {
			return 0;
		} else
			return Point;

	}

	private void checkEndCondition(Player CurrentPlayer, int OldPoints, int CurrentPoints, GraphicsContext gc, int x,
			int y) {

		if (CurrentPoints < 0) {
			CurrentPointsString = Integer.toString(OldPoints);
		} else if (CurrentPoints == 0) {
			if (CurrentPlayer.getEndThrow() == 3) {
				this.drawThrowPoint(gc, x, y);
				this.Showdialog("GAME END", "PLAYER " + CurrentPlayer.getName() + " is the winner!");
				DartboardCanvas.setDisable(true);
				return;
			} else if (CurrentPlayer.getEndThrow() == 1) {
				if (points.check_double_ring() != 0) {
					this.drawThrowPoint(gc, x, y);
					this.Showdialog("GAME END", "PLAYER " + CurrentPlayer.getName() + " is the winner!");
					DartboardCanvas.setDisable(true);
					return;
				} else
					CurrentPointsString = Integer.toString(OldPoints);

			} else if (CurrentPlayer.getEndThrow() == 2) {
				if (points.check_triple_ring() != 0) {
					this.drawThrowPoint(gc, x, y);
					this.Showdialog("GAME END", "PLAYER " + CurrentPlayer.getName() + " is the winner!");
					DartboardCanvas.setDisable(true);
					return;
				} else
					CurrentPointsString = Integer.toString(OldPoints);
			}

		}
	}

	private void drawThrowPoint(GraphicsContext gc, int x, int y) {
		gc.setFill(Color.BLACK);
		gc.fillOval(x - 8, y - 8, 16, 16);
		gc.setFill(playermanager.getPlayer().get(countPlayer).getColor());
		gc.fillOval(x - 5, y - 5, 10, 10);
	}

	@FXML
	private Slider BeginGameThrows;
	
	@FXML
	private GridPane GridPaneGameInfo;
	
	@FXML
	private Slider EndGameThrows;

	@FXML
	private Slider StartValueGameScore;

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
		BeginGameThrows.setDisable(true);
		EndGameThrows.setDisable(true);
		StartValueGameScore.setDisable(true);
		DownPlayer.setDisable(true);
		UpPlayer.setDisable(true);
		PlayerColor.setDisable(true);
		PlayerCollection.setDisable(true);
		StartGame.setDisable(true);
		DartboardCanvas.setVisible(true);

		HBox tabC_vBox = new HBox();

		int i = 0;
		for (Player player : playermanager.getPlayer()) {
			PlayerColumns.add(new ArrayList<String>());
			PlayerColumns.get(i).add(player.getName());
			PlayerColumns.get(i).add(Integer.toString(player.getStart()));
			PlayerLists.add(new ListView<String>());
			ls = FXCollections.observableList(PlayerColumns.get(i));
			PlayerLists.get(i).setItems(ls);
			tabC_vBox.getChildren().add(PlayerLists.get(i));
			player.setStartThrow((int) (BeginGameThrows.getValue()));
			player.setEndThrow((int) (EndGameThrows.getValue()));
			if (player.getStartThrow() == 3) {
				player.setStartEndState(true);
			}

			BarChartData.add(new XYChart.Series());
			ChartData = new XYChart.Data("", player.getStart());
			BarchartListner(player, ChartData);
			BarChartData.get(i).getData().add(ChartData);
			barChart.getData().add(BarChartData.get(i));

			i++;
		}
		GridPaneGameInfo.setStyle("-fx-background-color: "+PlayerColortoString(playermanager.getPlayer().get(0))+";");
		NamePlayer.setText(playermanager.getPlayer().get(0).getName());
		
		barChart.setLegendVisible(false);
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

		int StartThrow = (int) (BeginGameThrows.getValue());
		int EndThrow = (int) (EndGameThrows.getValue());
		int Start = (int) (StartValueGameScore.getValue());

		items.add(NamePlayer);
		PlayerCollection.setItems(items);

		playermanager.createPlayer(NamePlayer, color, StartThrow, EndThrow, Start);

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		int height = (int) DartboardCanvas.getHeight();
		dart = new DartBoard(DartboardCanvas, height);
		dart.DrawDartBoard();
		DartboardCanvas.setVisible(false);

		this.setSlider(StartValueGameScore, 501, 301, 701, 200, false);
		this.setSlider(EndGameThrows, 3, 1, 3, 1, true);
		this.setSlider(BeginGameThrows, 3, 1, 3, 1, true);

	}

	private void Showdialog(String title, String HeaderText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(HeaderText);
		alert.showAndWait();
	}

	private void setSlider(Slider slider, int StartPosition, int min, int max, int tickdistance,
			Boolean TigLableString) {
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(StartPosition);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setSnapToTicks(true);
		slider.setMajorTickUnit(tickdistance);
		slider.setMinorTickCount(0);
		slider.setBlockIncrement(0);

		if (TigLableString == true) {
			slider.setLabelFormatter(new StringConverter<Double>() {
				@Override
				public String toString(Double n) {
					if (n == 1)
						return "Double";
					if (n == 2)
						return "Tripple";

					return "Any";
				}

				@Override
				public Double fromString(String s) {
					switch (s) {
					case "Double":
						return 0d;
					case "Tripple":
						return 1d;
					case "Any":
						return 2d;

					default:
						return 2d;
					}
				}
			});
		}

		/**
		 * slider.valueProperty().addListener(new ChangeListener<Number>() {
		 * public void changed(ObservableValue<? extends Number> ov, Number
		 * old_val, Number new_val) { System.out.println(new_val.doubleValue());
		 * System.out.println(String.format("%.2f", new_val)); } });
		 * 
		 */

	}

}
