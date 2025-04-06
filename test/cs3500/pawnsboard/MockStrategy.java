package cs3500.pawnsboard;

import java.util.Iterator;
import java.util.List;

import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;
import cs3500.pawnsboard.strategy.Move;
import cs3500.pawnsboard.strategy.Strategy;

/**
 * Represents a mock of a strategy that takes in a list of move sequences used for testing the
 * machine player.
 */
public class MockStrategy implements Strategy {
  private final Iterator<Move> moveSequence;

  /**
   * Initializes a MockStrategy with a list of moves for the machine player.
   * @param moves list of moves
   */
  public MockStrategy(List<Move> moves) {
    this.moveSequence = moves.iterator();
  }

  @Override
  public Move chooseMove(ReadonlyPawnsBoardModel model, Player player) {
    if (moveSequence.hasNext()) {
      return moveSequence.next();
    } else {
      return new Move(-1, -1, -1, true);
    }
  }
}
