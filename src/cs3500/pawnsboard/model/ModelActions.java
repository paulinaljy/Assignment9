package cs3500.pawnsboard.model;

/**
 * Represents a ModelActions that notifies the player controller at every change in game state of
 * the model, including when the player turn changes and to refresh the view.
 */
public interface ModelActions {

  /**
   * Notifies the player controller it's their turn and to choose a move whenever the model updates
   * the player turn.
   */
  void itsYourTurn();

  /**
   * Notifies the player controller to refresh their view whenever the model updates the game state.
   */
  void refreshView();

  /**
   * Notifies the player controller that the game is over.
   */
  void processGameOver();
}