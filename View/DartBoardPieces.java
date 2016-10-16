package View;

import java.awt.geom.Arc2D;

import javafx.scene.paint.ImagePattern;

public class DartBoardPieces {
	private int arcAngle;
	private double x, y, width, height, start, startAngle;
	private ImagePattern imagePattern;

	public DartBoardPieces(double x, double y, double width, double height, double start, double startAngle,
			int arcAngle, ImagePattern imagePattern) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.start = start;
		this.startAngle = startAngle;
		this.arcAngle = arcAngle;
		this.imagePattern = imagePattern;
				
	}

	public double get_x() {
		return x;
	}

	public double get_y() {
		return y;
	}

	public double get_width() {
		return width;
	}

	public double get_height() {
		return height;
	}

	public double get_start() {
		return start;
	}

	public double get_startAngle() {
		return startAngle;
	}

	public double get_arcAngle() {
		return arcAngle;
	}

	public ImagePattern get_imagePattern() {
		return imagePattern;
	}
}