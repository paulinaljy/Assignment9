package cs3500.pawnsboard.player;

import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;
import cs3500.pawnsboard.strategy.Move;
import cs3500.pawnsboard.strategy.Strategy;
import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents a MachinePlayer that chooses the next move based on a strategy and notifies the
 * controller to take action.
 */
public class MachinePlayer implements GamePlayer {
  private final int playerID;
  private final ReadonlyPawnsBoardModel model;
  private ViewActions observer; // interface
  private final Strategy strategy;

  /**
   * Initializes a MachinePlayer with a read only pawns board model, strategy, and player ID.
   * @param model the read only pawns board model in the game
   * @param strategy the strategy the machine player should use to calculate the next move
   * @param playerID the corresponding player ID of this player
   */
  public MachinePlayer(ReadonlyPawnsBoardModel model, Strategy strategy, int playerID) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null");
    }
    this.model = model;
    this.strategy = strategy;
    this.playerID = playerID;
  }

  /**
   * Chooses the next move based on the strategy. If the move indicates the player should pass,
   * notifies the observer (PawnsBoardPlayerController) to pass. If the move indicates the player
   * should play, notifies the observer (PawnsBoardPlayerController) to set the card index, selected
   * cell, and place the card.
   */
  @Override
  public void chooseMove() {
    Player currentPlayer = model.getCurrentPlayer();
    Move move = strategy.chooseMove(model, currentPlayer);
    if (move.isPass()) {
      observer.pass();
    } else {
      observer.setCardIdx(move.getCardIdx());
      observer.setSelectedCell(move.getRow(), move.getCol());
      observer.placeCard();
    }
  }

  @Override
  public boolean isHumanPlayer() {
    return false;
  }

  @Override
  public int getPlayerID() {
    return playerID;
  }

  /**
   * Subscribes to the ViewActions observer that notifies the controller.
   * @param observer the ViewActions observer who should be notified
   */
  @Override
  public void subscribe(ViewActions observer) {
    this.observer = observer;
  }

  @Override
  public String toString() {
    return "Player " + playerID;
  }
}
