package cs3500.pawnsboard.strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;
import cs3500.pawnsboard.model.GameCard;

/**
 * Represents a MaxRowScore strategy that finds a card and location that will allow the current
 * player to win a particular row by making their row-score higher than the opponent’s row-score.
 */
public class MaxRowScore implements Strategy {

  /**
   *  Chooses a move that will allow the current player to have a higher row score than their
   *  opponent. Rows are visited from top-down. Cols are visited from left to right for player 1
   *  and right to left for player 2. If the current player has a lower or equal row-score
   *  than their opponent on that row, chooses the first card and location option that increases
   *  their row-score to be greater than or equal to the opponent’s row-score.
   *  If a move is found, returns the move with the card index, row, col, and false. If a move is
   *  not found, returns a move with -1 as the card index, row, col and true, , indicating the
   *  player should pass.
   * @param model the read only pawns board model of the game
   * @param player the current player
   * @return a Move with the card index, row, col, and boolean whether the player should pass
   */
  @Override
  public Move chooseMove(ReadonlyPawnsBoardModel model, Player player) {
    List<ArrayList<Cell>> board = model.getBoard();

    int currentPlayerID = 1; // player 1
    int otherPlayerID = 2; // player 2
    if (player.getColor().equals(Color.BLUE)) {
      currentPlayerID = 2; // player 2
      otherPlayerID = 1; // player 1
    }

    List<GameCard> hand = new ArrayList<>(model.getHand(currentPlayerID));
    // sort hand by order of card value - highest to lowest
    hand.sort(Comparator.comparingInt(GameCard::getValue).reversed());

    for (int row = 0; row < board.size(); row++) {
      int score = (currentPlayerID == 1) ? model.getP1RowScore(row) : model.getP2RowScore(row);
      int otherScore = (currentPlayerID == 1) ? model.getP2RowScore(row) : model.getP1RowScore(row);
      if (score > otherScore) { // if current player row score > other player's row score
        continue; // move to next row
      }

      for (int col = 0; col < board.get(row).size(); col++) {
        int newCol = col;
        if (currentPlayerID == 2) {
          newCol = model.getWidth() - 1 - col;
        }
        ReadOnlyCell cell = model.getCellAt(row, newCol);
        // if cell not placeable
        if (!cell.isCardPlaceable() || !(cell.getOwnedColor().equals(player.getColor()))) {
          continue; // move on to next cell
        }

        for (int h = 0; h < hand.size(); h++) {
          GameCard card = hand.get(h); // gets card in hand
          int cardValue = card.getValue(); // gets value of card
          int cardCost = card.getCost(); // gets cost of card
          if (cell.getValue() < cardCost) {
            continue; // move on to next card in hand
          }
          if ((score + cardValue) > otherScore) {
            int realHandIndex = model.getHand(currentPlayerID).indexOf(card);
            return new Move(realHandIndex, row, newCol, false);
          }
        }
      }
    }
    return new Move(-1, -1, -1, true);
  }
}
