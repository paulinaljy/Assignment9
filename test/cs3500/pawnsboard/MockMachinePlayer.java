package cs3500.pawnsboard;

import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.strategy.Move;
import cs3500.pawnsboard.strategy.Strategy;
import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents a mock of the machine player used
 * to test that the machines are taking turns correctly.
 */
public class MockMachinePlayer implements GamePlayer {
  private int playerID;
  private ReadonlyPawnsBoardModel model;
  private ViewActions observer; // interface
  private Strategy strategy;
  private StringBuilder log;

  /**
   * Initializes a MockMachinePlayer with a read only pawns board model, strategy, and player ID.
   * @param model the read only pawns board model in the game
   * @param strategy the strategy the machine player should use to calculate the next move
   * @param playerID the corresponding player ID of this player
   * @param log StringBuilder output
   */
  public MockMachinePlayer(ReadonlyPawnsBoardModel model, Strategy strategy,
                           int playerID, StringBuilder log) {
    this.model = model;
    this.strategy = strategy;
    this.playerID = playerID;
    this.log = log;
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
      log.append(currentPlayer + " passed ");
      observer.pass();
    } else {
      log.append(currentPlayer + " placed " + move.getCardIdx() + " " + move.getRow() + " "
              + move.getCol() + " ");
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
}
