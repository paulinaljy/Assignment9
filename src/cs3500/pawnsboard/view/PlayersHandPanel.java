package cs3500.pawnsboard.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Panel representing players' hands with behaviors, including drawing and updating the player's
 * hand after every game state.
 */
public class PlayersHandPanel extends JPanel implements IntPlayersHandPanel {

  private GameCardPanel selectedCard;
  private List<GameCardPanel> playersHand;
  private final ReadonlyPawnsBoardModel pawnsBoardModel;
  private final int playerID;
  private ViewActions observer;

  /**
   * Initializes a PlayersHandPanel with a read only pawns board model and player ID. Sets the
   * layout of this players hand panel to be a 1x1 grid and creates and highlights the starting
   * player's hand.
   * @param pawnsBoardModel read only pawns board model
   * @param playerID the current player's id
   */
  public PlayersHandPanel(ReadonlyPawnsBoardModel pawnsBoardModel, int playerID) {
    super();
    this.playerID = playerID;
    if (pawnsBoardModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.pawnsBoardModel = pawnsBoardModel;
    this.selectedCard = null;

    setLayout(new GridLayout(1, 1));
    createHand();
    highlightTurn();
  }

  /**
   * Creates and draws the current player's hand.
   */
  private void createHand() {
    this.playersHand = new ArrayList<GameCardPanel>();
    ArrayList<GameCard> playersHand = new ArrayList<GameCard>(pawnsBoardModel.getHand(playerID));
    for (int i = 0; i < playersHand.size(); i++) {
      createNewCard(i);
    }
    revalidate();
  }

  /**
   * Creates a new Game Card based on the given card index in the player's hand and adds it to the
   * playersHand in this PlayersHandPanel.
   * @param cardIdx card index in the player's hand (0-index)
   */
  private void createNewCard(int cardIdx) {
    GameCardPanel cardPanel = new GameCardPanel(pawnsBoardModel, cardIdx, playerID);
    cardPanel.setOpaque(true);
    this.add(cardPanel);
    this.playersHand.add(cardPanel);
  }

  /**
   * Highlights the current player's turn by adding a yellow outline to this player's hand.
   */
  private void highlightTurn() {
    if (pawnsBoardModel.getCurrentPlayerID() == this.playerID) {
      setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
    } else {
      setBorder(BorderFactory.createLineBorder(this.getBackground(), 0));
    }
  }

  /**
   * Refreshes this player's hand every turn by removing all cards and redrawing the new player's
   * hand.
   */
  @Override
  public void refreshHand() {
    this.removeAll();
    this.playersHand.clear();
    this.createHand();
    this.highlightTurn();
    if (observer != null) {
      for (int i = 0; i < this.playersHand.size(); i++) {
        GameCardPanel card = this.playersHand.get(i);
        card.addMouseListener(new PlayersHandMouseListener(observer, card));
      }
    }
  }

  /**
   * Returns the selected card in this player's hand.
   * @return GameCardButton that is currently selected
   */
  @Override
  public GameCardPanel getSelectedCard() {
    return selectedCard;
  }

  /**
   * Adds a new mouse listener with the given observer to each game card in this player's hand
   * panel.
   * @param observer the observer
   */
  @Override
  public void subscribe(ViewActions observer) {
    this.observer = observer;
    for (int i = 0; i < this.playersHand.size(); i++) {
      GameCardPanel card = this.playersHand.get(i);
      card.addMouseListener(new PlayersHandMouseListener(observer, card));
    }
  }

  /**
   * Represents a PlayersHandMouseListener that responds to mouse clicked events in this player's
   * hand (PlayersHandPanel class).
   */
  class PlayersHandMouseListener extends MouseAdapter {
    private ViewActions observer;
    private GameCardPanel card;

    /**
     * Initializes the PlayersHandMouseListener with an observer and game card.
     * @param observer  the {@link ViewActions} instance that will receive updates
     * @param card the game card
     */
    public PlayersHandMouseListener(ViewActions observer, GameCardPanel card) {
      this.observer = observer;
      this.card = card;
    }

    /**
     * Responds to mouse clicked events in the game board. Moves the card up if the card is
     * selected, while deselecting and moving the previously selected card down. After each mouse
     * event, notifies the observer (controller) to set the card index selected in the players hand.
     * @param evt the mouse event to be processed
     */
    public void mouseClicked(MouseEvent evt) {
      if (pawnsBoardModel.isGameOver() || !observer.isViewEnabled()) {
        return;
      }
      if (selectedCard == card) { // if selected card that is already selected
        // moves card down
        selectedCard.setLocation(selectedCard.getX(), selectedCard.getY() + 10);
        selectedCard = null; // deselect card
      } else {
        if (selectedCard != null) { // if a card is already selected + select different card
          // move originally selected card down
          selectedCard.setLocation(selectedCard.getX(), selectedCard.getY() + 10);
        }

        selectedCard = card; // set selected card to current card
        // move current card up
        selectedCard.setLocation(selectedCard.getX(), selectedCard.getY() - 10);
      }

      if (selectedCard != null) {
        observer.setCardIdx(selectedCard.getIndexID());
      }
      getTopLevelAncestor().requestFocus();
    }
  }
}
