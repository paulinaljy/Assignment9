package cs3500.pawnsboard.view;

/**
 * Represents a PlayersHandPanel interface with behaviors, including refreshing the panel after
 * every game state and getting the selected card.
 */
public interface IntPlayersHandPanel extends GamePanel {
  /**
   * Refreshes the player's hand based on the game state.
   */
  void refreshHand();
}
