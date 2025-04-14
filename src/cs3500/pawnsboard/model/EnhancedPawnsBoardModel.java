package cs3500.pawnsboard.model;

import java.util.List;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;

public class EnhancedPawnsBoardModel extends PawnsBoardModel {

  /**
   * Initializes a PawnsBoardModel with width, height, and random object.
   *
   * @param width      the width of the board
   * @param height     the height of the board
   * @param rand       random object
   * @param deckConfig deck configuration
   */
  public EnhancedPawnsBoardModel(int width, int height, Random rand, DeckConfiguration deckConfig) {
    super(width, height, rand, deckConfig);
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    Player currentPlayer = this.getCurrentPlayer();
    if (super.isValidCardPlacement(cardIdx, row, col, currentPlayer)) {
      GameCard card = currentPlayer.removeCard(cardIdx); // removes card from player's hand
      this.applyInfluenceGrid(card, row, col, currentPlayer); // influence effect
      Cell cell = getCellAt(row, col);
      if (cell.getFutureValue() < 0) { // cell future value < 0
        Cell newCell = new Pawns(currentPlayer.getColor(), card.getCost(), 0); // pawns with count of cost of game card
        this.board.get(row).set(col, newCell);
      } else {
        card.setFutureValue(cell.getFutureValue()); // set future value
        this.board.get(row).set(col, card); // set card at given row and column
        // sets the color of the card to the current player's color
        card.setColor(currentPlayer.getColor());
      }
      pass = 0; // sets pass to 0
      this.setNextPlayer();
    }
  }

  /**
   * Calculates the positions of the cells that are influenced based on the influence grid in the
   * given game card and the given row and column of the placed card. The row position of the
   * influenced cells are calculated by adding each row delta to the given row. The column position
   * of the influenced cells are calculated by adding each col delta to the given col.
   *
   * <p>For example, if the given game card's influence grid has a position (0,1),
   * this indicates that the influenced cell position is in the same row but 1 col to the
   * left of the placed cell. Thus,the row position of the influenced cell is 2 (0 + 2) and
   * the col position of the influenced cell is 1 (-1 + 2).
   *
   * @param card          the game card placed by the player
   * @param row           the row position of the card placed by the player
   * @param col           the column position of the card placed by the player
   * @param currentPlayer the current player of the game
   */
  private void applyInfluenceGrid(GameCard card, int row, int col, Player currentPlayer) {
    super.applyInfluenceCells(card, row, col, currentPlayer);

    List<Position> devalueCells = card.getDevaluePositions();
    for (int i = 0; i < devalueCells.size(); i++) {
      int rowPosition = devalueCells.get(i).getRowDelta() + row;
      int colPosition = devalueCells.get(i).getColDelta() + col;
      if (rowPosition >= 0 && rowPosition < this.height
              && colPosition >= 0 && colPosition < this.width) {
        this.devalueCellEffect(rowPosition, colPosition);
      }
    }

    List<Position> upgradeCells = card.getUpgradePositions();
    for (int i = 0; i < upgradeCells.size(); i++) {
      int rowPosition = upgradeCells.get(i).getRowDelta() + row;
      int colPosition = upgradeCells.get(i).getColDelta() + col;
      if (rowPosition >= 0 && rowPosition < this.height
              && colPosition >= 0 && colPosition < this.width) {
        this.upgradeCellEffect(rowPosition, colPosition);
      }
    }
  }

  /**
   * Performs the upgrade influence of the card by adding to the future value of the cell by 1.
   *
   * @param row           the row position of the cell to influence
   * @param col           the column position of the cell to influence
   */
  private void upgradeCellEffect(int row, int col) {
    Cell influencedCell = this.getCellAt(row, col);
    Cell newCell = influencedCell.upgrade();
    this.board.get(row).set(col, newCell);
  }

  /**
   * Performs the devalue influence of the card by decreasing the future value of the cell by 1.
   *
   * @param row           the row position of the cell to influence
   * @param col           the column position of the cell to influence
   */
  private void devalueCellEffect(int row, int col) {
    Cell influencedCell = this.getCellAt(row, col);
    influencedCell.devalue();
  }
}
