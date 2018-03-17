package game.model.deck;

import java.io.Serializable;
import java.util.Deque;
import java.util.Map;

import game.model.card.Card;
import game.model.card.actioncard.ActionCard;

/**
 * DeckBuilder builds the deck section by section.
 * 
 * @author ehiew
 *
 */
public interface DeckBuilder extends Serializable {

	public void buildPathCards();

	public void buildActionCards();

	public void buildPersonalCards();

	public Map<ActionCard, Card> getActionCardPairingEnums();

	public Deque<Card> getCardsDeck();

}
