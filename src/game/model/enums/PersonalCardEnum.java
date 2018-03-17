package game.model.enums;

import java.util.AbstractMap.SimpleEntry;

import game.model.card.personalcard.AddLuckPersonalCard;
import game.model.card.personalcard.AddPowerPersonalCard;
import game.model.card.personalcard.HealPersonalCard;
import game.model.card.personalcard.PersonalCard;
import game.model.card.personalcard.RemoveLuckPersonalCard;
import game.model.card.personalcard.RemovePowerActionCard;
import game.model.card.personalcard.StealCoinPersonalCard;
import game.model.card.personalcard.WeakPersonalCard;

/**
 * Enum for PersonalCards
 * @author pratap
 *
 */
public enum PersonalCardEnum {
	
	WEAK(new WeakPersonalCard(),3),
	HEAL(new HealPersonalCard(),3),
	POWER(new AddPowerPersonalCard(),3),
	LUCK(new AddLuckPersonalCard(),3),
	REMOVELUCK(new RemoveLuckPersonalCard(),3),
	REMOVEPOWER(new RemovePowerActionCard(),3),
	STEAL(new StealCoinPersonalCard(),3);
	
	
	private SimpleEntry<PersonalCard, Integer> entry;

	PersonalCardEnum(PersonalCard card, Integer num) {
		entry = new SimpleEntry<PersonalCard, Integer>(card, num);
	}

	public SimpleEntry<PersonalCard, Integer> getPersonalCardEnum() {
		return entry;
	}
	
	

}
