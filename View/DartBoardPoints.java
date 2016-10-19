package View;

import java.awt.geom.Arc2D;

public class DartBoardPoints {

	private int x;
	private int y;
	private double height;
	private double double_ring_dist;
	private double outer_filling_dist;
	private double triple_ring_dist;
	private double inner_filling_dist;

	private int[] dartnumbers = { 20, 1, 18, 4, 13, 6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5 };

	public DartBoardPoints(double height, int x, int y) {
		this.height = height / 2 - 10;
		this.x = x;
		this.y = y;
		
		double_ring_dist = (height / 2 - 10) / 100 * 45;
		outer_filling_dist = (height / 2 - 10) / 100 * 55;
		triple_ring_dist = (height / 2 - 10);
		inner_filling_dist = (height / 2 - 10) + (outer_filling_dist - double_ring_dist);
	}

	public int check_bulls_eye() {

		for (int i = 0; i <= 360; i++) {
			if (new Arc2D.Double(height - height / 7 / 2 + 10, height - height / 7 / 2 + 10, height / 7, height / 7, i,
					360, Arc2D.PIE).contains(x, y)) {
				return 50;
			}
		}
		return 0;

	}

	public int check_outer_bull() {
		
		for (int i = 0; i <= 360; i++) {
			if (new Arc2D.Double(height - height / 4 / 2 + 10, height - height / 4 / 2 + 10, height / 4, height / 4, i,
					360, Arc2D.PIE).contains(x, y)) {
				if (new Arc2D.Double(height - height / 7 / 2 + 10, height - height / 7 / 2 + 10, height / 7, height / 7,
						i, 360, Arc2D.PIE).contains(x, y) == false) {
					return 25;
				}
			}
		}
		return 0;

	}

	public int check_inner_ring() {
		int count = 0;
		int distance = 18;
		for (int i = 0; i <= 20; i++) {
			if (new Arc2D.Double(inner_filling_dist / 2 + 10, inner_filling_dist / 2 + 10,
					height * 2 - inner_filling_dist, height * 2 - inner_filling_dist, 99 - distance, 18, Arc2D.PIE)
							.contains(x, y) == true) {
				if (new Arc2D.Double(height - height / 4 / 2 + 10, height - height / 4 / 2 + 10, height / 4, height / 4,
						i, 360, Arc2D.PIE).contains(x, y) == false) {
					//System.out.println(count);
					return dartnumbers[count];
				}
			}
			count++;
			distance = distance + 18;
		}
		return 0;
	}

	public int check_triple_ring() {
		int count = 0;
		int distance = 18;
		for (int i = 0; i <= 20; i++) {
			if (new Arc2D.Double(triple_ring_dist / 2 + 10, triple_ring_dist / 2 + 10, height * 2 - triple_ring_dist,
					height * 2 - triple_ring_dist, 99 - distance, 18, Arc2D.PIE).contains(x, y)) {
				if (!new Arc2D.Double(inner_filling_dist / 2 + 10, inner_filling_dist / 2 + 10,
						height * 2 - inner_filling_dist, height * 2 - inner_filling_dist, 99 - distance, 18, Arc2D.PIE)
								.contains(x, y)) {
					return dartnumbers[count] * 3;
				}
			}
			count++;
			distance = distance + 18;
		}
		return 0;
	}

	public int check_outer_ring() {
		int count = 0;
		int distance = 18;
		for (int i = 0; i <= 20; i++) {
			if (new Arc2D.Double(outer_filling_dist / 2 + 10, outer_filling_dist / 2 + 10,
					height * 2 - outer_filling_dist, height * 2 - outer_filling_dist, 99 - distance, 18, Arc2D.PIE)
							.contains(x, y)) {
				if (!new Arc2D.Double(triple_ring_dist / 2 + 10, triple_ring_dist / 2 + 10,
						height * 2 - triple_ring_dist, height * 2 - triple_ring_dist, 99 - distance, 18, Arc2D.PIE)
								.contains(x, y)) {
					return dartnumbers[count];
				}
			}
			count++;
			distance = distance + 18;
		}
		return 0;
	}

	public int check_double_ring() {
		int count = 0;
		int distance = 18;
		for (int i = 0; i <= 20; i++) {
			if (new Arc2D.Double(double_ring_dist / 2 + 10, double_ring_dist / 2 + 10, height * 2 - double_ring_dist,
					height * 2 - double_ring_dist, 99 - distance, 18, Arc2D.PIE).contains(x, y)) {
				if (!new Arc2D.Double(outer_filling_dist / 2 + 10, outer_filling_dist / 2 + 10,
						height * 2 - outer_filling_dist, height * 2 - outer_filling_dist, 99 - distance, 18, Arc2D.PIE)
								.contains(x, y)) {
					return dartnumbers[count] * 2;
				}
			}
			count++;
			distance = distance + 18;
		}
		return 0;
	}

}
