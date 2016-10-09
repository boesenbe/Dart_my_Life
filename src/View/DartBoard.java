package View;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import Controller.ViewController;
import Model.Data;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class DartBoard {

	private List<DartBoardPieces> Arcs;

	private double height;
	private double distance;
	private double double_ring_dist;
	private double outer_filling_dist;
	private double triple_ring_dist;
	private double inner_filling_dist;

	private Canvas DartboardCanvas;

	public DartBoard(Canvas DartboardCanvas, double height) {
		this.DartboardCanvas = DartboardCanvas;
		this.height = height / 2 - 10;
		distance = 0;
		double_ring_dist = (height / 2 - 10) / 100 * 45;
		outer_filling_dist = (height / 2 - 10) / 100 * 55;
		triple_ring_dist = (height / 2 - 10);
		inner_filling_dist = (height / 2 - 10) + (outer_filling_dist - double_ring_dist);
	}
	
	

	public void DrawDartBoard() {
		GraphicsContext gc = DartboardCanvas.getGraphicsContext2D();
		drawShapes(gc);

	}

	public Canvas getCAnvas() {
		Canvas canvas = new Canvas(750, 750);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawShapes(gc);
		return canvas;
	}

	private void drawShapes(GraphicsContext gc) {
		// draw outer circle

		List<DartBoardPieces> Arcs = this.setDartboard();

		int i = 0;
		// gc.setFill(Arcs.get(i).get_color());
		gc.setFill(Arcs.get(i).get_imagePattern());
		gc.fillArc(Arcs.get(i).get_x(), Arcs.get(i).get_y(), Arcs.get(i).get_width(), Arcs.get(i).get_height(),
				Arcs.get(i).get_start(), Arcs.get(i).get_startAngle(), ArcType.ROUND);

		for (i = 3; i < Arcs.size(); i++) {
			gc.setFill(Arcs.get(i).get_imagePattern());
			gc.fillArc(Arcs.get(i).get_x(), Arcs.get(i).get_y(), Arcs.get(i).get_width(), Arcs.get(i).get_height(),
					Arcs.get(i).get_start(), Arcs.get(i).get_startAngle(), ArcType.ROUND);
		}

		i = 1;
		gc.setFill(Arcs.get(i).get_imagePattern());
		gc.fillArc(Arcs.get(i).get_x(), Arcs.get(i).get_y(), Arcs.get(i).get_width(), Arcs.get(i).get_height(),
				Arcs.get(i).get_start(), Arcs.get(i).get_startAngle(), ArcType.ROUND);

		i = 2;
		gc.setFill(Arcs.get(i).get_imagePattern());
		gc.fillArc(Arcs.get(i).get_x(), Arcs.get(i).get_y(), Arcs.get(i).get_width(), Arcs.get(i).get_height(),
				Arcs.get(i).get_start(), Arcs.get(i).get_startAngle(), ArcType.ROUND);

		/*
		 * String[] dartnumbers =
		 * {"3","19","7","16","8","11","14","9","12","5","20","1","18","4","13",
		 * "6","10","15","2","17"}; int count_numbers = 0; for (int i1 = 0; i1 <
		 * 20; i1++) {
		 * 
		 * //40 is exactly the middle between double ring and outer ring has to
		 * be dynamic
		 * 
		 * int radius = (int)(height-height/100*15+10);
		 * 
		 * int r = radius; double radians = Math.toRadians(-count_numbers); int
		 * x = (int)(r*Math.sin(radians)); int y = (int)(r*Math.cos(radians));
		 * 
		 * //gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, //
		 * RenderingHints.VALUE_ANTIALIAS_ON);
		 * 
		 * gc.setFont(getDesiredFont());
		 * 
		 * String str = dartnumbers[i1];
		 * 
		 * Rectangle bounds = getStringBounds(gc, str, x, y); int stringwidth =
		 * (int)(bounds.width); int stringheight = (int)(bounds.height);
		 * 
		 * 
		 * gc.setColor(Color.WHITE); gc.drawString(str,
		 * x+height-stringwidth/2+10, y+height+stringheight/2+10); count_numbers
		 * = count_numbers+18;
		 * 
		 * // gc.setFill(Arcs.get(0).get_2DArc());
		 * 
		 * // g.fill(new Arc2D.Double( x, y, width, height, start, startAngle,
		 * // arcAngle));
		 */

	}

	public List<DartBoardPieces> getDartboard() {
		return Arcs;
	}

	public List<DartBoardPieces> setDartboard() {

		Arcs = new ArrayList<>();

		Map<String, ImagePattern> Imagepattern = getcolorpattern();

		// create outer circle
		Arcs.add(new DartBoardPieces(10, 10, height * 2, height * 2, 0, 360, Arc2D.PIE, Imagepattern.get("black")));
		// create Bull
		Arcs.add(new DartBoardPieces(height - height / 4 / 2 + 10, height - height / 4 / 2 + 10, height / 4, height / 4,
				0.0, 360.0, Arc2D.PIE, Imagepattern.get("green")));
		// create Bull's eye
		Arcs.add(new DartBoardPieces(height - height / 7 / 2 + 10, height - height / 7 / 2 + 10, height / 7, height / 7,
				0, 360, Arc2D.PIE, Imagepattern.get("red")));

		for (int i = 0; i < 11; i++) {

			// create double ring Red
			Arcs.add(new DartBoardPieces(double_ring_dist / 2 + 10, double_ring_dist / 2 + 10,
					height * 2 - double_ring_dist, height * 2 - double_ring_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("red")));
			// create outer black filling
			Arcs.add(new DartBoardPieces(outer_filling_dist / 2 + 10, outer_filling_dist / 2 + 10,
					height * 2 - outer_filling_dist, height * 2 - outer_filling_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("black")));
			// create triple ring Red
			Arcs.add(new DartBoardPieces(triple_ring_dist / 2 + 10, triple_ring_dist / 2 + 10,
					height * 2 - triple_ring_dist, height * 2 - triple_ring_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("red")));
			// create inner black filling
			Arcs.add(new DartBoardPieces(inner_filling_dist / 2 + 10, inner_filling_dist / 2 + 10,
					height * 2 - inner_filling_dist, height * 2 - inner_filling_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("black")));

			distance = distance + 18;

			new View.DartBoardPieces(double_ring_dist / 2 + 10, double_ring_dist / 2 + 10,
					height * 2 - double_ring_dist, height * 2 - double_ring_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("green"));

			// create double ring Green
			Arcs.add(new DartBoardPieces(double_ring_dist / 2 + 10, double_ring_dist / 2 + 10,
					height * 2 - double_ring_dist, height * 2 - double_ring_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("green")));
			// create outer Light Gray filling
			Arcs.add(new DartBoardPieces(outer_filling_dist / 2 + 10, outer_filling_dist / 2 + 10,
					height * 2 - outer_filling_dist, height * 2 - outer_filling_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("gray")));
			// create triple ring Green
			Arcs.add(new DartBoardPieces(triple_ring_dist / 2 + 10, triple_ring_dist / 2 + 10,
					height * 2 - triple_ring_dist, height * 2 - triple_ring_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("green")));
			// create inner Light Gray filling
			Arcs.add(new DartBoardPieces(inner_filling_dist / 2 + 10, inner_filling_dist / 2 + 10,
					height * 2 - inner_filling_dist, height * 2 - inner_filling_dist, 99 - distance, 18, Arc2D.PIE,
					Imagepattern.get("gray")));

			distance = distance + 18;

		}
		return Arcs;
	}

	private int get_screen_height() {
		GraphicsDevice Mi = VIEW.Monitorsize;
		int height = Mi.getDisplayMode().getHeight();
		return height;
	}

	private Map<String, ImagePattern> getcolorpattern() {
		Map<String, ImagePattern> Imagepattern = new HashMap<String, ImagePattern>();

		Imagepattern.put("black", new ImagePattern(new Image("/CSS/schwarz.jpg")));
		Imagepattern.put("green", new ImagePattern(new Image("/CSS/gruen.jpg")));
		Imagepattern.put("red", new ImagePattern(new Image("/CSS/rot.jpg")));
		Imagepattern.put("gray", new ImagePattern(new Image("/CSS/weiss.jpg")));

		return Imagepattern;
	}

	/*
	 * private Font getDesiredFont() { return new Font(Font.SANS_SERIF,
	 * Font.BOLD, height/100*15); }
	 * 
	 * private java.awt.Rectangle getStringBounds(GraphicsContext g2, String
	 * str, float x, float y) { FontRenderContext frc =
	 * g2.getFontRenderContext(); GlyphVector gv =
	 * g2.getFont().createGlyphVector(frc, str); return gv.getPixelBounds(null,
	 * x, y); }
	 */

	// ===============================================================
	// FOR TESTING
	// ===============================================================
	/*
	 * 
	 * public static void main(String[] args) { launch(args); }
	 * 
	 * @Override public void start(Stage primaryStage) {
	 * primaryStage.setTitle("Drawing Operations Test"); Group root = new
	 * Group(); Canvas canvas = new Canvas(1000, 1000); GraphicsContext gc =
	 * canvas.getGraphicsContext2D(); drawShapes(gc);
	 * root.getChildren().add(canvas); primaryStage.setScene(new Scene(root));
	 * primaryStage.show();
	 * 
	 * }
	 * 
	 */
}
