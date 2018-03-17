package game.model.card;

import java.awt.Graphics;
import java.awt.Polygon;
import java.io.Serializable;

import game.model.visitor.Visitor;

public abstract class AbstractGoalCard extends Card  {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -2399308218207101528L;

	public AbstractGoalCard(){	
		super.setVisibility(false);
	}
	
	 
	public void accept(Visitor artist,Polygon polygon,Graphics graphics){
		artist.visit(this);
		
	}
}
