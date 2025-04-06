package cs3500.pawnsboard.player;

import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;
import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents a HumanPlayer in the PawnsBoard game.
 */
public class HumanPlayer implements GamePlayer {
  private final int playerID;

  /**
   * Initializes a Human Player with a read only pawns board model and player ID.
   * @param model the model of the game
   * @param playerID the corresponding player ID of the player
   */
  public HumanPlayer(ReadonlyPawnsBoardModel model, int playerID) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.playerID = playerID;
  }

  @Override
  public void chooseMove() {
    return;
  }

  @Override
  public boolean isHumanPlayer() {
    return true;
  }

  @Override
  public int getPlayerID() {
    return playerID;
  }

  @Override
  public void subscribe(ViewActions observer) {
    // player doesn't subscribe to an observer
  }

  @Override
  public String toString() {
    return "Player " + playerID;
  }
}
