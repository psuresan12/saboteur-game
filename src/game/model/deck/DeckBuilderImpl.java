package game.model.deck;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import game.controller.GameManager;
import game.model.card.AbstractGoalCard;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.card.TreasureCard;
import game.model.card.actioncard.ActionCard;
import game.model.enums.ActionCardEnum;
import game.model.enums.HexCardShapeEnum;
import game.model.enums.PersonalCardEnum;
import game.model.enums.RectangleCardShapeEnum;
import game.model.enums.SquareCardShapeEnum;
import game.model.enums.TriangleCardShapeEnum;
import game.model.shape.Shape;
import game.model.shape.HexShape;
import game.model.shape.RectangleShape;
import game.model.shape.SquareShape;
import game.model.shape.TriangleShape;

/**
 * DeckBuilderImpl implements DeckBuilder.
 * 
 * This class is a builder pattern used to abstract the building of the card
 * deck object from the operations of the game using the deck.
 * 
 * @author ehiew
 *
 */
public class DeckBuilderImpl implements DeckBuilder {

	/* Static Constant */
	/* Deck contents */
	private static final int NUM_PATH_CARDS = 30;
	private static final int NUM_ACTION_CARDS = 27;
	private static final int NUM_PERSONAL_CARDS = 15;

	/* Instance Variables */
	private Vector<Integer[]> cardPathEnums;
	private Deque<Card> actionCardEnums;
	private Deque<Card> personalCardEnums;
	private Map<ActionCard, Card> actionCardPairingEnums;

	private Shape shape;
	private Deque<Card> cardDeck;

	/**
	 * Constructor
	 * 
	 * @param cardShape
	 * @param cardDeck
	 */
	public DeckBuilderImpl(Shape cardShape, Deque<Card> cardDeck) {
		this.shape = cardShape;
		this.cardDeck = cardDeck;
		cardPathEnums = new Vector<Integer[]>();
		actionCardEnums = new LinkedList<Card>();
		personalCardEnums = new LinkedList<Card>();
		actionCardPairingEnums = new HashMap<ActionCard, Card>();
	}

	@Override
	public void buildPathCards() {
		PathCard pathCard;
		// go through the deck object and create new cards with pathedges
		// set the card shape of the deck
		setCardShapes();
		// set the number of cards for each combination of paths
		int totPathSegmentCards = NUM_PATH_CARDS / cardPathEnums.size();
		// create all the card path combinations (13 for Hex)
		for (Integer[] paths : cardPathEnums) {
			// number of each card (3 for Hex)
			for (int i = 0; i < totPathSegmentCards; i++) {
				pathCard = new PathCard(shape); // create card
				pathCard.setInitialPathSegments(paths); // add path edges
				cardDeck.push(pathCard); // add path card to deck
			}
		}
	}

	@Override
	public void buildActionCards() {
		// initialise action cards.
		initActionCards();
		// add all the action cards to the deck.
		cardDeck.addAll(actionCardEnums);
		initPersonalCards();
		cardDeck.addAll(personalCardEnums);
	}

	@Override
	public void buildPersonalCards() {
		// TODO Auto-generated method stub

	}

	/* TODO - Refactor for Chain of Responsibility Pattern */
	private void setCardShapes() {

		if (shape instanceof RectangleShape) {
			initRectangleCardPathSegments();
		} else if (shape instanceof SquareShape) {
			initSquareCardPathSegments();
		} else if (shape instanceof TriangleShape) {
			initTriangleCardPathSegments();
		} else if (shape instanceof HexShape) {
			initHexCardPathSegments();
		}
	}

	/**
	 * Initialize the Action Cards.
	 */
	private void initActionCards() {
		for (ActionCardEnum value : ActionCardEnum.values())
			for (int i = 0; i < value.getActionCardEnum().getValue(); i++)
				actionCardEnums.push(value.getActionCardEnum().getKey());
		// build the pairings for the action cards.
		initActionCardPairingEnums();
	}

	/**
	 * Create the action pairings. When we have a match, we pop top card from
	 * stack.
	 */
	private void initActionCardPairingEnums() {
		//creating temporary reference for patchCard.
		PathCard pathCardReference = new PathCard(new PathCard(GameManager.getCardShape()));
		//Set of paired action cards	
		actionCardPairingEnums.put(ActionCardEnum.REMOVECONCRETEWALL.getActionCardEnum().getKey(),
				ActionCardEnum.CONCRETEWALL.getActionCardEnum().getKey());
		actionCardPairingEnums.put(ActionCardEnum.CONCRETEWALL.getActionCardEnum().getKey(),
				pathCardReference);
		actionCardPairingEnums.put(ActionCardEnum.BOMB.getActionCardEnum().getKey(),
				pathCardReference);
		actionCardPairingEnums.put(ActionCardEnum.POWER.getActionCardEnum().getKey(),
				pathCardReference);
				
		// Persistent Data Structure
		actionCardPairingEnums = Collections.unmodifiableMap(actionCardPairingEnums);
	}
	
	
	
	/**
	 * Initialize the Personal Cards.
	 */
	private void initPersonalCards() {
		for (PersonalCardEnum value : PersonalCardEnum.values())
			for (int i = 0; i < value.getPersonalCardEnum().getValue(); i++)
				personalCardEnums.push(value.getPersonalCardEnum().getKey());
	}
	
	
	

	/**
	 * Initialise the rectangular card path segments.
	 */
	private void initRectangleCardPathSegments() {
		for (RectangleCardShapeEnum value : RectangleCardShapeEnum.values())
			cardPathEnums.addElement(value.getCardEnum());
	}

	/**
	 * Initialise the square card path segments.
	 */
	private void initSquareCardPathSegments() {
		for (SquareCardShapeEnum value : SquareCardShapeEnum.values())
			cardPathEnums.addElement(value.getCardEnum());
	}

	/**
	 * Initialise the triangular card path segments.
	 */
	private void initTriangleCardPathSegments() {
		for (TriangleCardShapeEnum value : TriangleCardShapeEnum.values())
			cardPathEnums.addElement(value.getCardEnum());
	}

	/**
	 * Initialise the Hexagonal card path segments.
	 */
	private void initHexCardPathSegments() {
		for (HexCardShapeEnum value : HexCardShapeEnum.values())
			cardPathEnums.addElement(value.getCardEnum());
	}

	public  Map<ActionCard, Card> getActionCardPairingEnums() {
		return actionCardPairingEnums;
	}

	@Override
	public Deque<Card> getCardsDeck() {
		return cardDeck;
	}

}
