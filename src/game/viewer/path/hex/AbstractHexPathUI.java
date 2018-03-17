package game.viewer.path.hex;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import game.viewer.path.PathUI;

/**
 * Abstract class to draw paths of pathcard
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractHexPathUI extends JPanel implements PathUI {
	
	Graphics graphics;
	Polygon polygon;
	
	
	public AbstractHexPathUI(Graphics graphics, Polygon polygon){
		this.graphics = graphics;
		this.polygon = polygon;
	}

	
}
