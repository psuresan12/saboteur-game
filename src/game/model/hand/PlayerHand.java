package game.model.hand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import game.controller.GameManager;
import game.model.card.Card;
import game.model.card.PathCard;

/**
 * PlayerHand represents the cards in possession for a given player.
 * 
 * @author ehiew
 *
 */
public class PlayerHand implements Serializable,Cloneable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7879628233516033885L;

	/* Static Constants*/
	private static final int MAX_HELD_CARDS = 6;
	
	/* Instance Variables */
	private List<Card> hand; //player hand
	
	/**
	 * PlayerHand constructor
	 */
	public PlayerHand() {
		hand = new ArrayList<Card>();
	}
	
	/**
	 * Add a card to the player hand.
	 * @param c
	 */
	public void addCard(Card c){
		hand.add(c);
	}
	
	/**
	 * Returns the list of cards
	 * @return
	 */
	public List<Card> getCard(){
		return hand;
	}
	
	/**
	 * Remove a card by index from the player hand.
	 * Draw(get) a card by index from the player hand.
	 * @param discard
	 * @param index
	 */
	public Card drawCard(int index){
		if(getPlayerHandSize() == 0) return null;
		return hand.get(index);
	}
	
	/**
	 * Remove a card by index from the player hand.
	 * @param discard
	 * @param index
	 */
	public Card removeCard(int index){
		if(getPlayerHandSize() == 0) return null;
		return hand.remove(index);
	}
	
	/**
	 * When a player has decided to pass, they need to choose the index of the
	 * card and discard it.
	 *
	 * @param index
	 */
	public void discardCard(int index){
		GameManager.getDiscardPile().addCard(removeCard(index));
	}
	
	
	/**
	 * Reorder cards in the player hand.
	 * 
	 * @param first
	 * @param second
	 */
	public void swapCards(Card first, Card second){
		Card temp = first;
		first = second;
		second = temp;
	}
	
	/**
	 * Return a card from the player hand at a given index.
	 * 
	 * @param index
	 * @return
	 */
	public Card getCardByIndex(int index){
		return hand.get(index);
	}
	
	/**
	 * Return the size of the player hand.
	 * 
	 * @return
	 */
	public int getPlayerHandSize(){
		return hand.size();
	}

	/**
	 * Return the maximum size of player hand.
	 * 
	 * @return
	 */
	public static int getMaxHeldCards() {
		return MAX_HELD_CARDS;
	}

	/**
	 * Remove a card from the player hand.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Card removeCard() {
		if(getPlayerHandSize() == 0) return null;
		return ((Deque<Card>)hand).pop();
	}
	
	/**
	 * Rotate card by index and distance of rotation. Each integer
	 * is equivalent to an edge.
	 * 
	 * @param index
	 * @param distance
	 */
	//TODO - update for shape rotate
	public boolean rotateCardByIndex(int index, int distance){
		Card card = hand.get(index);
		if(card instanceof PathCard){
			((PathCard)card).rotatePathCard(distance);
			return true;
		}
		return false;
	}
}
