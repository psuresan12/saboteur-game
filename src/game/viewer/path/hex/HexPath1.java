package game.viewer.path.hex;

import java.awt.Graphics;
import java.awt.Polygon;

/**
 * Draw first path (bottom down is the first path, and moves clock wise)
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class HexPath1 extends AbstractHexPathUI {

	public HexPath1(Graphics graphics,Polygon polygon){
		super(graphics, polygon);
		
	}
	
	
	/**
	 * Calculate logic to draw the first path, and draws
	 */
	public void drawPaths(){
		
			this.graphics.drawLine(this.polygon.xpoints[4]+(this.polygon.xpoints[3]- this.polygon.xpoints[4])/2 -3 ,
				this.polygon.ypoints[4] - 3, 
				(this.polygon.xpoints[0]+(this.polygon.xpoints[1]- this.polygon.xpoints[0])/2) - 3,
				this.polygon.ypoints[2] - 3);
			this.graphics.drawLine(this.polygon.xpoints[4]+(this.polygon.xpoints[3]- this.polygon.xpoints[4])/2 +3,
					this.polygon.ypoints[4]+3, 
					(this.polygon.xpoints[0]+(this.polygon.xpoints[1]- this.polygon.xpoints[0])/2) +3,
					this.polygon.ypoints[2]+3);
			
			this.graphics.fillOval(this.polygon.xpoints[0]+(this.polygon.xpoints[1]- this.polygon.xpoints[0])/2 -5,
                    this.polygon.ypoints[2] -3,
                     10, 
                     10 );
	}


}
