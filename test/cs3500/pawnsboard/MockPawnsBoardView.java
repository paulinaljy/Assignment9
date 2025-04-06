package cs3500.pawnsboard;

import java.io.IOException;

import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;
import cs3500.pawnsboard.view.PawnsBoardView;
import cs3500.pawnsboard.view.QueensBloodTextualView;
import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents a mock of the pawns board view that prints out the textual view of the board.
 */
public class MockPawnsBoardView implements PawnsBoardView, QueensBloodTextualView {
  private final ReadonlyPawnsBoardModel model;
  private boolean displayMessage;
  private String message;
  private boolean displayGameOver;

  /**
   * Initializes a MockPawnsBoardView with a model and player id.
   * @param model the model in the game
   * @param playerID corresponding player id of the current player
   */
  public MockPawnsBoardView(ReadonlyPawnsBoardModel model, int playerID) {
    this.model = model;
    this.displayMessage = false;
    this.message = "";
    this.displayGameOver = false;
  }

  @Override
  public void refresh() {
    //empty
  }

  @Override
  public void makeVisible() {
    //empty
  }

  @Override
  public void subscribe(ViewActions observer) {
    //empty
  }

  @Override
  public void reset() {
    //empty
  }

  @Override
  public void displayMessage(String message, String title) {
    this.message = message;
    this.displayMessage = true;
  }

  @Override
  public void displayGameOver() {
    this.displayGameOver = true;
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
    if (this.displayMessage) {
      board += message;
    }
    if (this.displayGameOver) {
      board += "\n" + "Game Over." + "\n" + "Winner: " + model.getWinner() + "\n"
              + "Winning Score: " + model.getWinningScore();
    }
    return board;
  }

  @Override
  public void render(Appendable append) throws IOException {
    append.append(this.toString());
  }
}
