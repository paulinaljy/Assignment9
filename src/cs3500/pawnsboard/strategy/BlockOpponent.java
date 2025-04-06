package cs3500.pawnsboard.strategy;

import java.awt.Color;
import java.util.List;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a BlockOpponent strategy that calculates the best move of the opponent from the given
 * strategies and finds a move to block that cell.
 */
public class BlockOpponent implements Strategy {
  private final List<Strategy> opponentStrategies;

  /**
   * Initializes a BlockOpponent strategy with a list of opponent strategies.
   * @param opponentStrategies list of opponent strategies
   */
  public BlockOpponent(List<Strategy> opponentStrategies) {
    this.opponentStrategies = opponentStrategies;
  }

  /**
   * Chooses a move that will allow the current player to block the best move of the opponent.
   * Calculates the move of each strategy in the opponents strategies and then visits each cell
   * and card in player's hand until a move is found to block the target cell of that move. If a
   * move is found, returns a Move with a card index, row, col, and false. If no move is found,
   * return a Move with -1 as the card index, row, col and true, indicating the player should pass.
   * @param model the read only pawns board model
   * @param player the current player
   * @return a Move with the card index, row, col, and  boolean whether the player should pass
   */
  @Override
  public Move chooseMove(ReadonlyPawnsBoardModel model, Player player) {
    Player opponent = model.getPlayerByColor(Color.red);
    if (player.getColor().equals(Color.red)) {
      opponent = model.getPlayerByColor(Color.blue);
    }

    Move maxBlockMove = null;
    for (Strategy strategy : opponentStrategies) {
      Move nextMove = strategy.chooseMove(model, opponent);
      if (!nextMove.isPass()) {
        maxBlockMove = findInfluenceableCell(model, nextMove.getRow(), nextMove.getCol(), player);
        if (maxBlockMove != null) {
          return maxBlockMove;
        }
      }
    }
    return new Move(-1, -1, -1, true);
  }

  /**
   * Visits every cell and card in player's hand until finds a location and card that can block the
   * opponent's best move. Loops through every relative influence cell position in the influence
   * grid of the card to find whether any of its influenced cells can block the given target row
   * and target col of the opponent's best move. Returns a Move with the card index, row, col, and
   * false if a move is found. Otherwise, returns null.
   * @param model the read only pawns model of the game
   * @param targetRow the row of the opponent's best move
   * @param targetCol the col of the opponent's best move
   * @param player the current player
   * @return a Move with the card index, row, col, and boolean whether the player should pass
   */
  private Move findInfluenceableCell(ReadonlyPawnsBoardModel model, int targetRow,
                                     int targetCol, Player player) {
    for (int h = 0; h < player.getHand().size(); h++) {
      GameCard card = player.getHand().get(h);
      List<Position> influencedCells = card.getPositions();
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          int newCol = col;
          if (player.getColor().equals(Color.blue)) {
            newCol = model.getWidth() - 1 - col;
          }
          ReadOnlyCell cell = model.getCellAt(row, newCol);
          if (!cell.isCardPlaceable() || !(cell.getOwnedColor().equals(player.getColor()))
                  || cell.getValue() < player.getHand().get(h).getCost()) {
            continue;
          }
          for (Position pos : influencedCells) {
            int rowPosition = pos.getRowDelta() + row;
            int colPosition = pos.getColDelta() + newCol;
            if (rowPosition == targetRow && colPosition == targetCol) {
              return new Move(h, row, newCol, false);
            }
          }
        }
      }
    }
    return null;
  }
}

