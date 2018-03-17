package game.model.player;

import java.io.Serializable;
import java.util.Map;

import game.controller.GameManager;
import game.exceptions.ActionCardFunctionFailed;
import game.exceptions.CannotAddPathCardException;
import game.exceptions.ImpotentPlayerException;
import game.exceptions.IncorrectActionCardPairingException;
import game.exceptions.IncorrectPersonalCardPairingException;
import game.exceptions.MissingStartCardGoalCardException;
import game.exceptions.ServiceDownException;
import game.exceptions.UnconnectedPathException;
import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.AbstractGoalCard;
import game.model.card.Card;
import game.model.card.StartCard;
import game.model.card.actioncard.ActionCard;
import game.model.card.personalcard.PersonalCard;
import game.model.deck.Deck;
import game.model.hand.PlayerHand;
import game.viewer.hexagon.AppHexagonalPanel;

/**
 * Abstract class for player.
 * @author pratap
 *
 */
public abstract class AbstractPlayer implements Player, Serializable {

	private static final long serialVersionUID = 6798403779961589849L;

	/* Static Constants */
	protected static final int MAX_NUM_PLAYERS = 6;
	protected static final int MIN_NUM_PLAYERS = 3;
	private static final int WINNER_GOLD = 50;

	/* Static Variables */
	protected static int playerNumber = 0;

	/* Instance Variables */
	protected PlayerHand hand;
	protected Role role;
	protected String name;

	/* Player status Variables */
	protected boolean isWeak = false;
	protected int sick = 0;
	protected int goldCoins = 0;
	protected boolean isUndoUsed = false;
	protected int luckyCoin = 0;
	protected int powerLevel = 0;

	/**
	 * Turn-based gameplay handling each player turn. If there is an action card
	 * played from the playerhand, we can use it against a path card, as long as
	 * it is not a StartCard or TreasureCard. Once the action is successful, the
	 * card from the board coordinate is removed and added to the discard pile.
	 * Otherwise, the current card is a Path Card.
	 * 
	 * @param board
	 * @param handIndex
	 * @param xy
	 * @return
	 * @throws CannotAddPathCardException
	 * @throws IncorrectActionCardPairingException
	 * @throws UnconnectedPathException
	 * @throws MissingStartCardGoalCardException
	 * @throws ActionCardFunctionFailed
	 * @throws ImpotentPlayerException
	 * @throws IncorrectPersonalCardPairingException
	 * @throws ServiceDownException
	 */
	@Override
	public void takeTurn(Grid board, int handIndex, Coordinate xy, Deck cardDeck, Deck discardPile, boolean isDiscarded,
			int targetPlayerIndex) throws CannotAddPathCardException, IncorrectActionCardPairingException,
			UnconnectedPathException, MissingStartCardGoalCardException, ActionCardFunctionFailed,
			ImpotentPlayerException, IncorrectPersonalCardPairingException, ServiceDownException {

		PlayerHand hand = getHand(); // Select the player hand
		Card gridCard = board.getCardFromGrid(xy); // Get card from grid
		Card handCard = hand.drawCard(handIndex); // get card from hand
		Deck pile = discardPile; // discard pile

		if (isDiscarded) {
			// card discarded, turn completed
			pile.addCard(hand.drawCard(handIndex));
		} else {
			// make sure previousCard is not starting card or treasure card.
			if ((gridCard instanceof StartCard || gridCard instanceof AbstractGoalCard)
					&& !(handCard instanceof ActionCard)) {
				throw new ActionCardFunctionFailed();
			}

			/*
			 * Action Card functionality.
			 */
			if (handCard instanceof ActionCard) {
				if (!isWeak) // Enter as long as the player is not weak
				{
					// TypeCast from normal card to ActionCard
					ActionCard actionCard = (ActionCard) handCard;
					// Access the enum ActionCard pair
					Map<ActionCard, Card> pairingActionCard = GameManager.getDeck().getDeckBuilder()
							.getActionCardPairingEnums();

					// Check if the card is a key
					if (pairingActionCard.containsKey(actionCard)) {
						// check if the grid coordinate is not null(i.e.cannot
						// be placed on empty grid)
						if (board.getCardFromGrid(xy) != null) {
							// Check if the pair card is same as the card in the
							// grid
							if (pairingActionCard.get(actionCard).getClass()
									.equals(board.getCardFromGrid(xy).getClass())) {
								if (!actionCard.action(board, xy, pile))
									throw new ActionCardFunctionFailed();

							} else
								throw new IncorrectActionCardPairingException();
						} else
							throw new IncorrectActionCardPairingException();
					}
					/*
					 * if the card is not a key(Hide and show cards, delegates
					 * the responsibility of pairing validation inside the
					 * overridden method
					 */
					else if (!actionCard.action(board, xy, pile))
						throw new IncorrectActionCardPairingException();

				} else
					throw new ImpotentPlayerException();
			}

			/*
			 * Personal Card Functionality
			 * 
			 */
			else if (handCard instanceof PersonalCard) {
				// Typecast from Card to PersonalCard
				PersonalCard tempPersonalCard = (PersonalCard) handCard;
				// Get the target Player reference
				Player targetPlayer = GameManager.getPlayerByIndex(targetPlayerIndex);

				if (targetPlayer != null) { // check if the target player is
											// valid
					if (!tempPersonalCard.action(this, targetPlayer, targetPlayerIndex)) {
						throw new ServiceDownException();
					}
				} else
					throw new IncorrectPersonalCardPairingException();
			}

			else {
				/*
				 * Path Card Functionality
				 * 
				 */
				// ...is a path card
				// check that there is a path through to the coordinate hand

				// check if the coordinate has connecting paths with the start

				if (!board.isAdjacentLeaf(xy, handCard)) {
					// grid selection is not connecting path.
					throw new UnconnectedPathException("Illegal Move. Tunnels must connect!\n");
				} else {
					AppHexagonalPanel.getInstance().messageBox.setText("Successful Card connection!\n");
				}

				// check if the game is completed
				if (!board.hasPath()) {
					// check the coordinate is empty for path card
					if (board.checkCoordinateIsEmpty(xy)) {
						// add the current card to the grid coordinate
						board.addCardToGrid(xy, handCard);
					} else {
						// grid selection is full, cannot add pathcard
						throw new CannotAddPathCardException("PathCard already on the board. Try another board coordinate.");
					}
				} else {
					// goal reached - WORKERS WON - handle end of game
					// add the current card to the grid coordinate
					board.addCardToGrid(xy, handCard);
					// update viewer
					AppHexagonalPanel.getInstance().messageBox.setText("Gameover. Path from Start to End complete!\n");
					// this player is the winner, winner gets gold prize
					addGoldCoins(WINNER_GOLD);
					// handle end of game for worker win.
					GameManager.workersWin();
				}
			}
		}
		// Successful move...
		moveWasSuccessful(handIndex, cardDeck);
	}

	private void moveWasSuccessful(int handIndex, Deck cardDeck) {
		// if more cards in the player hand
		if (hand.getPlayerHandSize() != 0) {
			// remove card from hand
			hand.removeCard(handIndex);
			// pop card from deck to the playerhand prior to turn;
			Card newCard = cardDeck.removeNextCard();
			hand.addCard(newCard);
		}

		// increment player
		// finalOperation();

	}

	@Override
	public void finalOperation() {
		GameManager.incrementPlayerIndex();
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	/* Static Methods */

	public static int getMaxPlayers() {
		return MAX_NUM_PLAYERS;
	}

	public static int getMinPlayers() {
		return MIN_NUM_PLAYERS;
	}

	@Override
	public PlayerHand getHand() {
		return hand;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isWeak() {
		return isWeak;
	}

	@Override
	public void setWeak(boolean isWeak) {
		this.isWeak = isWeak;
	}

	@Override
	public int getSick() {
		return sick;
	}

	@Override
	public void setSick(int sick) {
		this.sick = sick;
	}

	@Override
	public int getGoldCoins() {
		return goldCoins;
	}

	@Override
	public void addGoldCoins(int goldCoins) {
		this.goldCoins += goldCoins;
	}

	@Override
	public void addLuckyCoin() {
		luckyCoin++;
	}

	@Override
	public void removeLuckyCoin() {
		if (luckyCoin != 0)
			luckyCoin--;
	}

	@Override
	public void addPowerLevel() {
		powerLevel++;
	}

	@Override
	public void removePowerLevel() {
		if (powerLevel != 0)
			powerLevel--;
	}

	@Override
	public int getPowerLevel() {
		return powerLevel;
	}

	@Override
	public int getLuckyCoin() {
		return luckyCoin;
	}

}
