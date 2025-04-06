package cs3500.pawnsboard.strategy;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a ControlBoard strategy that finds a card and location that will give the current
 * player ownership of the most cells.
 */
public class ControlBoard implements Strategy {
  /**
   * Chooses a move that will give the current player ownership of the most cells.In a tie between
   * positions, choose the uppermost-leftmost for player 1 and uppermost-rightmost for player 2.
   * In a tie between cards, choose the leftmost (or closest to first) card.
   * If a move is found, returns the move with the card index, row, col, and false. If a move is not
   * found, returns a move with -1 as the card index, row, col and true, , indicating the player
   * should pass.
   * @param model the read only pawns board model of the game
   * @param player the current player
   * @return a Move with the card index, row, col, and boolean whether the player should pass
   */
  @Override
  public Move chooseMove(ReadonlyPawnsBoardModel model, Player player) {
    int maxCount = 0;
    Point maxCellPosition = null;
    int maxCardIdx = -1;

    List<GameCard> hand = player.getHand();
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        int newCol = col;
        if (player.getColor().equals(Color.blue)) {
          newCol = model.getWidth() - 1 - col;
        }
        ReadOnlyCell cell = model.getCellAt(row, newCol);
        if (!cell.isCardPlaceable() || !(cell.getOwnedColor().equals(player.getColor()))) {
          continue; // if card is not pawns, move to next cell
        }

        for (int h = 0; h < hand.size(); h++) {
          GameCard card = hand.get(h);
          int cardCost = card.getCost(); // gets cost of card
          if (cell.getValue() < cardCost) {
            continue; // move on to next card in hand
          }

          List<Position> influencedCells = card.getPositions();
          int netCount = getNetCount(model, influencedCells, row, newCol, player);
          if (netCount > maxCount) {
            maxCount = netCount; // replaces max net count with current net count
            maxCardIdx = h;
            maxCellPosition = new Point(row, newCol);
          }
        }
      }
    }
    if (maxCount > 0) {
      return new Move(maxCardIdx, (int)maxCellPosition.getX(),
              (int)maxCellPosition.getY(), false);
    } else {
      return new Move(-1, -1, -1, true);
    }
  }

  /**
   * Calculates the net count of owned cells based on the given influenced cells of the game card,
   * row and col of the cell where the card will be placed, and the current player.
   * @param model the read only pawns board model
   * @param influencedCells the list of relative positions in the influence grid of the card
   * @param row the row of the cell (0-index)
   * @param col the col of the cell (0-index)
   * @param currentPlayer the current player
   * @return value representing the net count of owned cells if the card was placed
   */
  private int getNetCount(ReadonlyPawnsBoardModel model, List<Position> influencedCells,
                          int row, int col, Player currentPlayer) {
    int netCount = 0;
    for (int i = 0; i < influencedCells.size(); i++) {
      int rowPosition = influencedCells.get(i).getRowDelta() + row;
      int colPosition = influencedCells.get(i).getColDelta() + col;
      if (rowPosition >= 0 && rowPosition < model.getHeight()
              && colPosition >= 0 && colPosition < model.getWidth()) {
        ReadOnlyCell cell = model.getBoard().get(rowPosition).get(colPosition);
        if (cell.isCardPlaceable()) { // is pawns
          if (!(cell.getOwnedColor().equals(currentPlayer.getColor()))) {
            netCount += 1; // take ownership of other player's pawns
          }
        } else if (!cell.isGameCard()) { // is empty
          netCount += 1; // add to net count
        }
      }
    }
    return netCount;
  }
}
