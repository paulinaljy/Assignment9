package cs3500.pawnsboard.view;

import java.io.IOException;

import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.model.ReadOnlyCell;

public class NewTextualView implements QueensBloodTextualView {
  private final QueensBlood model;

  public NewTextualView(QueensBlood model) {
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
        String strCell = cell.toString();

        int delta = cell.getFutureValue() - cell.getValue();
        if (delta > 0) {
          strCell += "(+" + delta + ")";
        } else if (delta < 0) {
          strCell += "(" + delta + ")";
        }

        board += strCell + " ";
      }

      board += p2 + "\n";
    }

    return board;
  }


  @Override
  public void render(Appendable append) throws IOException {
    append.append(this.toString());
  }
}
