package game.model.deck;

import java.util.Map;
import java.util.Random;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import game.controller.GameManager;
import game.model.card.Card;
import game.model.card.actioncard.ActionCard;
import game.model.hand.PlayerHand;
import game.model.player.Player;
import game.model.player.SimplePlayer;
import game.model.prototype.ClonableUtils;
import game.model.prototype.GameItem;
import game.model.shape.Shape;

/**
 * 
 * Deck Class intitialises a deck of cards for the Saboteur Game.
 * 
 * TODO -
 * 1) Path Segments for PathCards should be another class.
 * 2) Action Card pairings should be another class.
 * 
 * @author ehiew
 *
 */
public class Deck implements GameItem,Serializable {

	/* Deck contents */
	private static final int NUM_PATH_CARDS = 44;
	private static final int NUM_ACTION_CARDS = 27;
	private static final int MAX_NUM_PLAY_CARDS = NUM_PATH_CARDS + NUM_ACTION_CARDS;

	/* Treasure Cards */
	private static final int NUM_STONE_CARDS = 2;
	private static final int NUM_GOAL_CARDS = 1;
	
	/* Action Cards */
	private static final int NUM_TOXIC_SPILL_ACTION_CARDS = 1;
	private static final int NUM_CLEANUP_ACTION_CARDS = 1;	
	private static final int NUM_BOMB_ACTION_CARDS = 1;
	
	/* Static Variables */
	private Deque<Card> cardsDeck = new LinkedList<Card>();
	private boolean isDeckEmpty;
	private static Map<ActionCard, Card> actionCardPairingEnums = new HashMap<ActionCard, Card>();

	
	/* DeckBuilder variable - To access enums */
	private DeckBuilder deckBuilder;
	
	
	public Deck(){
		isDeckEmpty = true;
	}
	
	/**
	 * Constructor - Director of Build Pattern
	 * 
	 * @param shape
	 */
	public Deck(Shape shape) {
		//create a new builder
		deckBuilder = new DeckBuilderImpl(shape, cardsDeck);
		
		//TODO - Build Deck
		deckBuilder.buildPathCards();
		deckBuilder.buildActionCards();
		//deckBuilder.buildPersonalCards();
		
		//Return built components for run-time
		cardsDeck = deckBuilder.getCardsDeck();
		//shuffle the deck
		this.shuffle();
		//set deck state
		isDeckEmpty = false;
		//get the action card pairing enums
		actionCardPairingEnums = deckBuilder.getActionCardPairingEnums();	
	}

	
	/**
	 * Deal a card from the top of the deck to the player hand.
	 * Maximum 6 cards per player hand.
	 */
	public void deal(PlayerHand hand) {
		for(int i=0;i<PlayerHand.getMaxHeldCards(); i++) 
			hand.addCard(removeNextCard());
	}

	/**
	 * Shuffle the card deck randomly.
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Deque<Card> shuffle() {
		Collections.shuffle((List<Card>)cardsDeck, new Random());
		return cardsDeck;
	}

	/**
	 * Add a card to the deck.
	 * 
	 * @param c
	 */
	public void addCard(Card c) {
		cardsDeck.add(c);
	}

	/**
	 * Remove the next card from the deck.
	 * 
	 * @return
	 */
	public Card removeNextCard(){
		if(cardsDeck.size() == 0){
			isDeckEmpty = true;
			return null;
		}
		return ((Deque<Card>)cardsDeck).pop();
	}
	
	
	public static Card getActionCardPair(ActionCard action) {
		return actionCardPairingEnums.get(action);
	}

	public static int getMaxNumPlayCards() {
		return MAX_NUM_PLAY_CARDS;
	}

	public static int getNumToxicSpillActionCards() {
		return NUM_TOXIC_SPILL_ACTION_CARDS;
	}

	public static int getNumCleanUpActionCards() {
		return NUM_CLEANUP_ACTION_CARDS;
	}

	public static int getNumStoneCards() {
		return NUM_STONE_CARDS;
	}

	public static int getNumBombActionCards() {
		return NUM_BOMB_ACTION_CARDS;
	}

	public static int getNumGoalCards() {
		return NUM_GOAL_CARDS;
	}
	
	/**
	 * Getter of DeckBuilder
	 * @return DeckBuilder
	 */
	public DeckBuilder getDeckBuilder() {
		return deckBuilder;
	}
	
	@Override
	public Deck makeCopy() throws CloneNotSupportedException{
		
		Deck tempDeck = new Deck();		
		tempDeck = ClonableUtils.cloneThis(this);

		return tempDeck;
	}

}
