package cs3500.pawnsboard.view;


import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.model.ReadOnlyCell;

/**
 * Represents an EnhancedPawnsBoardTextualView that prints out a textual representation of the
 * game board with future values of each cell.
 */
public class EnhancedTextualView extends PawnsBoardTextualView {

  public EnhancedTextualView(QueensBlood model) {
    super(model);
  }

  @Override
  public String toString() {
    String board = "";
    int p1;
    int p2;
    for (int row = 0; row < model.getHeight(); row++) {
      p1 = model.getP1RowScore(row);
      p2 = model.getP2RowScore(row);

      board += p1 + " ";

      for (int col = 0; col < model.getWidth(); col++) {
        ReadOnlyCell cell = model.getCellAt(row, col);
        String strCell = cell.toString();

        int futureValue = cell.getFutureValue();
        if (futureValue > 0) {
          strCell += "(+" + futureValue + ")";
        } else if (futureValue < 0) {
          strCell += "(" + futureValue + ")";
        } else {
          strCell += "(+0)";
        }

        board += strCell + " ";
      }

      board += p2 + "\n";
    }

    return board;
  }
}
