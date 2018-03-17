package game.viewer.hexagon;

import java.awt.Polygon;

import game.viewer.board.UISettings;

public class HexMathLogicUI {
	static UISettings uiSettings;
	final static int hexagonEdges = 6;
	int hexagonHeight = 50;
	int hexagonOuterSide = (int) (hexagonHeight / 1.73205);
	int hexagonInnerSide = (int) (hexagonHeight / (2 * 1.73205));

	int hexagonXVertices[] = new int[6];
	int hexagonYVertices[] = new int[6];
	int boardBorder = 100;
	int playerHandBorder = 500;
	Polygon polygon;

	/**
	 * get the basic polygon with the start vertex start vertex is the top left
	 * coordinate
	 * 
	 * @param hexagonXVertex
	 *            represents the first X vertex of hexagon
	 * @param hexagonYVertex
	 *            represents the first Y vertex of hexagon
	 * @return the complete hexagon
	 */
	public static Polygon getHexagon(int hexagonXVertex, int hexagonYVertex) {

		return (new Polygon(
				// calculate coordinates of X axis of Polygon
				new int[] { (hexagonXVertex + uiSettings.getHexagonInnerSide()),
						(hexagonXVertex + uiSettings.getHexagonInnerSide() + uiSettings.getHexagonOuterSide()),
						(hexagonXVertex + 2 * uiSettings.getHexagonInnerSide() + uiSettings.getHexagonOuterSide()),
						(hexagonXVertex + uiSettings.getHexagonInnerSide() + uiSettings.getHexagonOuterSide()),
						(hexagonXVertex + uiSettings.getHexagonInnerSide()), hexagonXVertex },

				// calculate coordinates of Y axis of Polygon
				new int[] { hexagonYVertex, hexagonYVertex, (hexagonYVertex + (uiSettings.getHexagonHeight() / 2)),
						hexagonYVertex + uiSettings.getHexagonHeight(), hexagonYVertex + uiSettings.getHexagonHeight(),
						hexagonYVertex + (uiSettings.getHexagonHeight() / 2) },

				// Number of Edges
				hexagonEdges));
	}

	/**
	 * returns the polygon based on the row and column
	 * 
	 * @param gridRow
	 * @param gridColumn
	 * @return
	 */
	public static Polygon getPolygon(int boardRow, int boardColumn) {

		return getHexagon(
				// calculate XPoisition of first coordinate of Polygon
				uiSettings.getLeftBorder()
						+ (boardRow * (uiSettings.getHexagonInnerSide() + (uiSettings.getHexagonOuterSide()))),
				// calculate YPosition of first coordinate of Polygon
				uiSettings.getTopBorder() + (boardColumn * uiSettings.getHexagonHeight())
						+ (boardRow % 2) * (uiSettings.getHexagonHeight() / 2));
	}

	/**
	 * returns the coordinate inside polygon to draw text
	 * 
	 * @param playerhand
	 *            row
	 * @param playerhand
	 *            column
	 * @return
	 */
	public static int[] getPositionForText(int boardRow, int boardColumn) {
		int[] labelIndexes = new int[2];
		labelIndexes[0] = getPolygon(boardRow, boardColumn).xpoints[0];
		labelIndexes[1] = getPolygon(boardRow, boardColumn).ypoints[5];
		return labelIndexes;

	}

	/**
	 * returns the indexes(coordinate) of the polygon
	 * 
	 * @param clickedX
	 *            - X location of mouse
	 * @param clickedY
	 *            - Y location of mouse
	 * @return
	 */
	public static int[] getPosition(int clickedX, int clickedY) {
		int[] playerBoardIndices = new int[2];
		playerBoardIndices[0] = -1;
		playerBoardIndices[1] = -1;

		for (int i = 0; i < uiSettings.getRow(); i++) {
			for (int j = 0; j < uiSettings.getColumn(); j++) {

				if (getPolygon(j, i).contains(clickedX, clickedY)) {
					playerBoardIndices[0] = j;
					playerBoardIndices[1] = i;
					// System.out.println("Inside getPlayerBoardIndices 0 "+i);
					return playerBoardIndices;

				}
			}
		}
		return playerBoardIndices;

	}

}
