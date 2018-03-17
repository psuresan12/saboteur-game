package game.viewer.path.hex;

import java.awt.Graphics;
import java.awt.Polygon;


/**
 * Draw five path (bottom down is the first path, and moves clock wise)
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class HexPath5 extends AbstractHexPathUI {

	public HexPath5(Graphics graphics, Polygon polygon) {
		super(graphics, polygon);

	}

	/**
	 * Calculate logic to draw the fifth path, and draws
	 */
	public void drawPaths() {
		// g.drawLine(150, 150, 500,500);
		this.graphics.drawLine(this.polygon.xpoints[1] + (this.polygon.xpoints[2] - this.polygon.xpoints[1]) / 2 - 3,
				this.polygon.ypoints[1] + (this.polygon.ypoints[2] - this.polygon.ypoints[1]) / 2 - 3,
				(this.polygon.xpoints[0] + (this.polygon.xpoints[1] - this.polygon.xpoints[0]) / 2) - 3,
				this.polygon.ypoints[2] - 3);

		this.graphics.drawLine(this.polygon.xpoints[1] + (this.polygon.xpoints[2] - this.polygon.xpoints[1]) / 2 + 3,
				this.polygon.ypoints[1] + (this.polygon.ypoints[2] - this.polygon.ypoints[1]) / 2 + 3,
				(this.polygon.xpoints[0] + (this.polygon.xpoints[1] - this.polygon.xpoints[0]) / 2) + 3,
				this.polygon.ypoints[2] + 3);
		
		this.graphics.fillOval(this.polygon.xpoints[0]+(this.polygon.xpoints[1]- this.polygon.xpoints[0])/2 -5,
                this.polygon.ypoints[2] -3,
                 10, 
                 10 );
	}

}
