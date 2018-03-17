package game.viewer.hexagon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.List;

import game.model.card.Card;
import game.model.card.PathCard;
import game.model.visitor.ColorVisitor;
import game.model.visitor.Visitor;
import game.viewer.path.hex.HexPath2;
import game.viewer.path.hex.HexPath1;
import game.viewer.path.hex.HexPath4;
import game.viewer.path.hex.HexPath3;
import game.viewer.path.hex.HexPath6;
import game.viewer.path.hex.HexPath5;

public class HexogonImplUI {

	public static Color color;

	/**
	 * method to draw the basic polygon
	 * 
	 * @param polygon
	 * @param graphics
	 */
	public static void drawHexagon(Polygon polygon) {
		// Get the graphics of polygon
		Graphics graphics = AppHexagonalPanel.getInstance().getGraphics();
		graphics.setColor(Color.blue);
		// draw the basic polygon.
		graphics.drawPolygon(polygon);

	}

	/**
	 * Draw polygon with the pathCards
	 * 
	 * @param polygon
	 * @param pathCard
	 * @param graphics
	 */
	public static void drawHexagon(PathCard pathCard) {
		Visitor visitor = new ColorVisitor();
		// Get the graphics
		Graphics graphics = AppHexagonalPanel.getInstance().getGraphics();
		// draw the basic polygon
		// drawHexagon(polygon,graphics);
		if (pathCard != null) // check if the path attribute is valid
		{
			// Changing the color of path only for the selected Player Hand
			// indexes.
			if (AppHexagonalPanel.getInstance().isSelectedPlayerIndex == true) {
				pathCard.accept(visitor);
				graphics.setColor(color);
				AppHexagonalPanel.getInstance().isSelectedPlayerIndex = false; // setting
																				// the
																				// selected
																				// index
																				// once
																				// the
																				// colour
																				// has
																				// been
																				// changed
			} else
				graphics.setColor(Color.blue);
			drawPaths(pathCard, graphics); // draw all the paths of the polygon
											// based on the value

		}

	}

	/**
	 * Draw cards except the pathcards.(i.e) action and personal, start,and goal
	 * cards.
	 * 
	 * @param card
	 */
	public static void drawHexagon(Card card) {
		Visitor visitor = new ColorVisitor();
		// Get the current polygon from panel
		Polygon polygon = AppHexagonalPanel.getInstance().getPolygon();
		int[] label = new int[2];
		label[0] = polygon.xpoints[0];
		label[1] = polygon.ypoints[5];
		// Get the graphics
		Graphics graphics = AppHexagonalPanel.getInstance().getGraphics();
		if (AppHexagonalPanel.getInstance().isSelectedPlayerIndex) {
			card.accept(visitor);
			graphics.setColor(color); // changing the color to show case the
										// selected card
		} else
			graphics.setColor(Color.blue);
		graphics.drawString(card.getCardName(), label[0] - 7, label[1] + 4);
		AppHexagonalPanel.getInstance().isSelectedPlayerIndex = false; // setting
																		// false
																		// once
																		// color
																		// is
																		// changed.
	}

	/**
	 * draws all the path of the given polygon based on the path values
	 * 
	 * @param polygon
	 * @param pathCard
	 * @param graphics
	 */
	public static void drawPaths(PathCard pathCard, Graphics graphics) {

		// Get the current Polygon from panel
		Polygon polygon = AppHexagonalPanel.getInstance().getPolygon();
		List<Integer> paths = pathCard.getPathSegments();

		// Navigating to find all the six paths of the polygon
		for (int i = 0; i < paths.size(); i++) {

			switch (i) {
			// navigate to the first path
			case 0:
				if (paths.get(i).intValue() == 1)
					new HexPath1(graphics, polygon).drawPaths();
				break;
			// navigate to the second path
			case 1:
				if (paths.get(i).intValue() == 1)
					new HexPath2(graphics, polygon).drawPaths();
				break;
			// navigate to the third path
			case 2:
				if (paths.get(i).intValue() == 1)
					new HexPath3(graphics, polygon).drawPaths();
				break;
			// navigate to the fourth path
			case 3:
				if (paths.get(i).intValue() == 1)
					new HexPath4(graphics, polygon).drawPaths();
				break;
			// navigate to the fifth path
			case 4:
				if (paths.get(i).intValue() == 1)
					new HexPath5(graphics, polygon).drawPaths();
				break;
			// navigate to the sixth path
			case 5:
				if (paths.get(i).intValue() == 1)
					new HexPath6(graphics, polygon).drawPaths();
				break;
			default:
				System.out.println("Invalid path");

			}
		}

	}

}
