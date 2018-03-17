package game.model.enums;

import java.util.AbstractMap.SimpleEntry;

import game.model.card.actioncard.ActionCard;
import game.model.card.actioncard.BombActionCard;
import game.model.card.actioncard.ConcreteWallActionCard;
import game.model.card.actioncard.HideAndSwapCard;
import game.model.card.actioncard.PowerPathActionCard;
import game.model.card.actioncard.RemoveConcreteWallActionCard;
import game.model.card.actioncard.RevealGoalCard;

public enum ActionCardEnum {

	BOMB(new BombActionCard(), 3),
	SHOW(new RevealGoalCard(),5),
	HIDE(new HideAndSwapCard(),5),
	POWER(new PowerPathActionCard(),3),
	CONCRETEWALL(new ConcreteWallActionCard(),3),
	REMOVECONCRETEWALL(new RemoveConcreteWallActionCard(),3);
	

	private SimpleEntry<ActionCard, Integer> entry;

	ActionCardEnum(ActionCard card, Integer num) {
		entry = new SimpleEntry<ActionCard, Integer>(card, num);
	}

	public SimpleEntry<ActionCard, Integer> getActionCardEnum() {
		return entry;
	}

}
