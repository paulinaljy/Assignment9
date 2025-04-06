package cs3500.pawnsboard;

import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents a mock of the view actions used for testing.
 */
public class MockViewActions implements ViewActions {
  public boolean passCalled = false;
  public boolean placeCardCalled = false;
  public int cardIdx = -1;
  public int row = -1;
  public int col = -1;

  @Override
  public void placeCard() {
    placeCardCalled = true;
  }

  @Override
  public void pass() {
    passCalled = true;
  }

  @Override
  public void setCardIdx(int cardIdx) {
    this.cardIdx = cardIdx;
  }

  @Override
  public void setSelectedCell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public boolean isViewEnabled() {
    return true;
  }

}
