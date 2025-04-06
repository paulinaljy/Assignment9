package cs3500.pawnsboard.strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a FillFirst strategy that finds the first card and location that can be played on.
 */
public class FillFirst implements Strategy {

  /**
   * Chooses the first valid move for the current player. Rows are visited from top-down. Cols are
   * visited from left to right for player 1 and right to left for player 2.
   * If a move is found, returns the move with the card index, row, col, and false. If a move is not
   * found, returns a move with -1 as the card index, row, col and true, indicating the player
   * should pass.
   * @param model the ready only pawns board model of the game
   * @param player the current player
   * @return a Move with the card index, row, col, and boolean whether the player should pass
   */
  @Override
  public Move chooseMove(ReadonlyPawnsBoardModel model, Player player) {
    List<ArrayList<Cell>> board = model.getBoard();

    for (int h = 0; h < player.getHandSize(); h++) {
      for (int row = 0; row < board.size(); row++) {
        for (int col = 0; col < board.get(row).size(); col++) {
          int newCol = col;
          if (player.getColor().equals(Color.blue)) {
            newCol = model.getWidth() - 1 - col;
          }
          ReadOnlyCell cell = model.getCellAt(row, newCol);
          if (cell.isCardPlaceable() && cell.getOwnedColor().equals(player.getColor())
                  && cell.getValue() >= player.getHand().get(h).getCost()) {
            return new Move(h, row, newCol, false);
          }
        }
      }
    }

    return new Move(-1, -1, -1, true);
  }
}
