package game.viewer.path.rect;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.viewer.path.*;

public abstract class AbstractRectPathUI implements PathUI {

	Graphics graphics;
	Rectangle rectangle;
	
	public AbstractRectPathUI(Graphics graphics, Rectangle rectangle) {
		this.graphics = graphics;
		this.rectangle = rectangle;
	}
	
	
}
