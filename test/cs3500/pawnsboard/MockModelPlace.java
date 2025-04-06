package cs3500.pawnsboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.EmptyCell;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.model.Pawns;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.model.ReadOnlyCell;

/**
 * Represents a mock of the pawns board model used for testing the strategies. Outputs a transcript
 * of the cell coordinates visited.
 */
public class MockModelPlace implements QueensBlood {
  private StringBuilder log;
  private final int width;
  private final int height;
  private final Player[] players;
  private final List<ArrayList<Cell>> board;
  private Random rand;
  private int turn;

  /**
   * Initializes a MockModelPlace with a string builder, width, height, and random object.
   * @param log the StringBuilder output
   * @param width the width of the board
   * @param height the height of the board
   * @param rand Random object
   */
  public MockModelPlace(StringBuilder log, int width, int height, Random rand) {
    this.width = width;
    this.height = height;
    this.board = this.makeBoard();
    this.players = new Player[2];
    this.rand = rand;
    this.turn = 0;
    this.log = log;
  }

  /**
   * Initializes the game board with a list empty cells, except the first and last column
   * start with single pawn in each cell.
   *
   * @return a list of array list of cells
   */
  private List<ArrayList<Cell>> makeBoard() {
    List<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    for (int r = 0; r < this.height; r++) {
      ArrayList<Cell> row = new ArrayList<>();
      board.add(row);
      for (int c = 0; c < this.width; c++) {
        row.add(new EmptyCell());
      }
      row.set(0, new Pawns(Color.red));
      row.set(this.width - 1, new Pawns(Color.blue));
    }
    return board;
  }

  @Override
  public Player getCurrentPlayer() {
    return this.players[this.turn];
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public Cell getCellAt(int row, int col) {
    return this.board.get(row).get(col); // returns an empty cell, pawns, or cell
  }

  @Override
  public void setNextPlayer() {
    this.turn = (turn + 1) % 2;
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    Player currentPlayer = this.getCurrentPlayer();
    GameCard card = currentPlayer.removeCard(cardIdx); // removes card from player's hand
    log.append(cardIdx + " " + row + " " + col);
    this.board.get(row).set(col, card); // set card at given row and column
    card.setColor(currentPlayer.getColor());
    this.applyInfluenceCells(card, row, col, currentPlayer); // influence effect
    this.setNextPlayer();
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
  private void applyInfluenceCells(GameCard card, int row, int col, Player currentPlayer) {
    List<Position> influencedCells = card.getPositions();
    for (int i = 0; i < influencedCells.size(); i++) {
      // get affected positions
      int rowPosition = influencedCells.get(i).getRowDelta() + row;
      int colPosition = influencedCells.get(i).getColDelta() + col;
      if (rowPosition >= 0 && rowPosition < this.height
              && colPosition >= 0 && colPosition < this.width) {
        this.influenceCellEffect(rowPosition, colPosition, currentPlayer);
      }
    }
  }

  /**
   * Performs the influence of the card. If the cell to influence has nothing on it, then the cell
   * gains a single pawn owned by the current turn player. If the cell to influence has pawns on it
   * AND is owned by the player, then the number of pawns on the cell increases by 1 with a max of
   * 3 pawns. If the cell to influence has pawns on it AND is not owned by the player, then the
   * current player takes ownership of those pawns.
   *
   * @param row           the row position of the cell to influence
   * @param col           the column position of the cell to influence
   * @param currentPlayer the current turn player of the game
   */
  private void influenceCellEffect(int row, int col, Player currentPlayer) {
    Cell influencedCell = this.getCellAtUninfluenced(row, col);
    Cell newCell = influencedCell.influence(currentPlayer);
    this.board.get(row).set(col, newCell);
  }

  @Override
  public void pass() {
    this.setNextPlayer();
  }

  @Override
  public void drawNextCard() {
    Player currentPlayer = this.getCurrentPlayer();
    if (currentPlayer.getDeck().size() > 0) {
      currentPlayer.drawNextCard(0);
    }
  }

  @Override
  public void startGame(List<GameCard> p1Deck, List<GameCard> p2Deck,
                        int handSize, boolean shuffle) {
    this.players[0] = new Player(Color.RED, p1Deck, handSize, rand, shuffle);
    // creates new player 2
    this.players[1] = new Player(Color.BLUE, p2Deck, handSize, rand, shuffle);
    //this.pass = 0;
    this.drawNextCard();
  }

  @Override
  public void subscribe(ModelActions observer, int playerID) {
    //empty body
  }

  @Override
  public List<GameCard> getHand(int playerID) {
    return players[playerID - 1].getHand();
  }

  @Override
  public Player getWinner() {
    return new Player(Color.red, new ArrayList<GameCard>(), 5, new Random(), true);
  }

  @Override
  public int getWinningScore() {
    return 0;
  }

  @Override
  public int getP2RowScore(int row) {
    return this.getPlayerScore(Color.blue, row);
  }

  @Override
  public int getP1RowScore(int row) {
    return this.getPlayerScore(Color.red, row);
  }

  /**
   * Returns the score based on the given player color and row.
   * @param playerColor the color of the player
   * @param row the row to calculate th score of (0-index based)
   * @return the score of the row of the given player
   */
  private int getPlayerScore(Color playerColor, int row) {
    int score = 0;
    for (int col = 0; col < this.board.get(row).size(); col++) {
      ReadOnlyCell cell = getCellAtUninfluenced(row, col);
      if (cell.isGameCard() && cell.getOwnedColor() == playerColor) {
        score += cell.getValue(); // gets the value of the card if Player owns color and is GameCard
      }
    }
    return score;
  }

  private Cell getCellAtUninfluenced(int row, int col) {
    return this.board.get(row).get(col);
  }

  @Override
  public List<ArrayList<Cell>> getBoard() {
    List<ArrayList<Cell>> boardCopy = new ArrayList<>();
    for (ArrayList<Cell> row : board) {
      ArrayList<Cell> rowCopy = new ArrayList<>();
      for (Cell cell : row) {
        rowCopy.add(cell.getCell());
      }
      boardCopy.add(rowCopy);
    }
    return boardCopy;
  }

  @Override
  public Player getOwnerOfCell(int row, int col) {
    return new Player(Color.red, new ArrayList<GameCard>(), 5, new Random(), true);
  }

  @Override
  public Color getPlayerColor(int playerID) {
    return Color.red;
  }

  @Override
  public int getPlayerTotalScore(int playerID) {
    return 0;
  }

  @Override
  public int getCurrentPlayerID() {
    return turn + 1;
  }

  @Override
  public Player getPlayerByColor(Color color) {
    if (color.equals(Color.red)) {
      return players[0];
    } else {
      return players[1];
    }
  }
}

