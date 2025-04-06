package cs3500.pawnsboard.view;

import java.io.IOException;

import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.model.ReadOnlyCell;

/**
 * Represents a PawnsBoardTextualView that prints out a textual representation of the game board.
 */
public class PawnsBoardTextualView implements QueensBloodTextualView {
  private final QueensBlood model;

  /**
   * Initializes a PawnsBoardTextualView with a model.
   * @param model the model of the game
   */
  public PawnsBoardTextualView(QueensBlood model) {
    this.model = model;
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
        board += cell.toString();
      }
      board += " " + p2 + "\n";
    }
    return board;
  }

  @Override
  public void render(Appendable append) throws IOException {
    append.append(this.toString());
  }
}
