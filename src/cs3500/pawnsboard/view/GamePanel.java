package cs3500.pawnsboard.view;

/**
 * Represents a GamePanel interface with behaviors, including subscribing an observer to receive
 * updates on view actions.
 */
public interface GamePanel {

  /**
   * Subscribes an observer to receive updates on view actions.
   * @param observer the {@link ViewActions} instance that will receive updates
   */
  void subscribe(ViewActions observer);
}
