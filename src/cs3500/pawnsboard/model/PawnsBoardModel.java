package cs3500.pawnsboard.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;

/**
 * Represents the model of a PawnsBoard game with behaviors, including starting the game, placing a
 * card, passing, performing the influence effect of placing a card, getting a cell at a specific
 * board position, setting the next player, getting the current player, drawing the next card,
 * getting the player's hand, score of each player, the winner and winning score of the game, and
 * determining if the game is over.
 * Throws an IllegalArgumentException when initializing the game if the height of the board is
 * negative, width is negative or even, or if the random object is null.
 * Throws an IllegalArgumentException at any point of the game if the cardIdx, row or column is
 * invalid.
 * Throws an IllegalStateException at any point of the game if the game has not started and
 * user tries to activate a game functionality like placing a card.
 * Throws an IllegalStateException if a player tries to place a card that cannot be placed on the
 * board, either due to not owning enough pawns to cover the cost of the card, not owning the same
 * color of pawns, or placing a card in a cell where there is already a game card on the board.
 *
 * <p>CLASS INVARIANT:
 * “The player turn value is always either 0 or 1.”
 * This is a class invariant because it is a logical statement and meets the instantaneous state of
 * object requirement because at any given moment, we know what the turn value is. In addition, this
 * statement is also ensured by the constructor because the constructor initializes turn to be 0.
 * This statement is also preserved by the method setNextPlayer() because it uses the modulo
 * operation (% 2) to ensure that turn always updates to 0 or 1.
 */
public class PawnsBoardModel implements QueensBlood {
  private final int width;
  private final int height;
  private final Player[] players;
  private final List<ArrayList<Cell>> board;
  private int turn;
  private int pass;
  private boolean gameStarted;
  private final Random rand;
  private int p1TotalScore;
  private int p2TotalScore;
  private ModelActions observer1;
  private ModelActions observer2;

  /**
   * Initializes a PawnsBoardModel with width, height, and random object.
   *
   * @param width      the width of the board
   * @param height     the height of the board
   * @param rand       random object
   * @param deckConfig deck configuration
   */
  public PawnsBoardModel(int width, int height, Random rand, DeckConfiguration deckConfig) {
    if (height < 0) {
      throw new IllegalArgumentException("invalid height");
    }
    if (width < 0 || width % 2 != 1) {
      throw new IllegalArgumentException("invalid width");
    }
    if (rand == null) {
      throw new IllegalArgumentException("Random object cannot be null");
    }
    this.width = width;
    this.height = height;
    this.rand = rand;
    this.board = this.makeBoard();
    this.gameStarted = false;
    this.turn = 0;
    this.players = new Player[2];
    this.p1TotalScore = 0;
    this.p2TotalScore = 0;
  }

  /**
   * Sets the next player. After updating turn, draws the next card for the next player and then
   * notifies the observer (view) to refresh both players' view. If turn = 0 and observer is not
   * null, notifies observer1 (player 1) that it's their turn. If turn = 1 and observer is not null,
   * notifies observer2 (player 2) that it's their turn.
   */
  @Override
  public void setNextPlayer() {
    this.isGameStarted();
    if (this.isGameOver()) {
      observer1.processGameOver();
    }
    this.turn = (turn + 1) % 2;
    this.drawNextCard();
    observer1.refreshView();
    observer2.refreshView();
    if (this.turn == 0) {
      if (observer1 != null) {
        observer1.itsYourTurn();
      }
    } else {
      if (observer2 != null) {
        observer2.itsYourTurn();
      }
    }
  }

  /**
   * If turn is 0, return Player 1. If turn is 1, return Player 2.
   *
   * @return the player based on the turn index
   */
  @Override
  public Player getCurrentPlayer() {
    this.isGameStarted();
    return this.players[this.turn];
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

  /**
   * Checks if the game has started. If the game has not started, throws an IllegalStateException
   * with the message "Game has not started yet".
   */
  private void isGameStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
  }

  /**
   * The game is over when both players pass their turn, one after the other.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    this.isGameStarted();
    return this.pass == 2;
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    this.isGameStarted();
    Player currentPlayer = this.getCurrentPlayer();

    if (cardIdx < 0 || cardIdx >= currentPlayer.getHandSize()) {
      throw new IllegalArgumentException("Selected card index is out of bounds. ");
    }
    if (row < 0 || row >= this.height) {
      throw new IllegalArgumentException("Selected row is out of bounds. ");
    }
    if (col < 0 || col >= this.width) {
      throw new IllegalArgumentException("Selected column is out of bounds. ");
    }
    Cell centerCell = board.get(row).get(col);
    // if there are no pawns (empty cell or game card)
    if (!centerCell.isCardPlaceable()) {
      throw new IllegalStateException("You have no pawns on this cell. Cannot add card. ");
    }
    // if the given card and position does not contain the same color pawn as the player
    if (!(centerCell.getOwnedColor().equals(currentPlayer.getColor()))) {
      throw new IllegalStateException("You do not own these pawns. Cannot add card. ");
    }
    // if the player does not have enough pawns to cover the cost of the card
    if (centerCell.getValue() < currentPlayer.getCard(cardIdx).getCost()) {
      throw new IllegalStateException("You do not have enough pawns to cover the cost of this " +
              "card. ");
    }

    GameCard card = currentPlayer.removeCard(cardIdx); // removes card from player's hand
    this.board.get(row).set(col, card); // set card at given row and column
    // sets the color of the card to the current player's color
    card.setColor(currentPlayer.getColor());
    this.applyInfluenceCells(card, row, col, currentPlayer); // influence effect
    pass = 0; // sets pass to 0
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
    Cell influencedCell = this.getCellAt(row, col);
    Cell newCell = influencedCell.influence(currentPlayer);
    this.board.get(row).set(col, newCell);
  }

  @Override
  public void pass() {
    this.isGameStarted();
    pass = pass + 1;
    this.setNextPlayer();
  }

  @Override
  public void drawNextCard() {
    this.isGameStarted();
    Player currentPlayer = this.getCurrentPlayer();
    if (currentPlayer.getDeck().size() > 0) {
      currentPlayer.drawNextCard(0);
    }
  }

  /**
   * Returns the board size based on the width and height of the game board.
   * @return the board size
   */
  private int getBoardSize() {
    return this.width * this.height;
  }

  @Override
  public void startGame(List<GameCard> p1Deck, List<GameCard> p2Deck,
                        int handSize, boolean shuffle) {
    if (gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    if (p1Deck == null || p1Deck.contains(null) || p2Deck == null || p2Deck.contains(null)) {
      throw new IllegalArgumentException("Game cards cannot be null");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size must be positive");
    }
    if (p1Deck.size() + handSize < this.getBoardSize()
            || p2Deck.size() + handSize < this.getBoardSize()) {
      throw new IllegalArgumentException("Size of deck must be able to fill the board");
    }
    if ((handSize > ((p1Deck.size() + handSize) / 3)
            || (handSize > ((p2Deck.size() + handSize) / 3)))) {
      throw new IllegalArgumentException("Size of player's hand must be less than a third of the "
              + "deck size");
    }

    // creates new player 1
    this.players[0] = new Player(Color.RED, p1Deck, handSize, rand, shuffle);
    // creates new player 2
    this.players[1] = new Player(Color.BLUE, p2Deck, handSize, rand, shuffle);
    this.gameStarted = true;
    this.pass = 0;
    this.drawNextCard();
  }

  /**
   * If the given playerID is 1, subscribes observer1 (player 1) to the given observer. If the given
   * playerID is 2, subscribes observer2 (player 2) to the given observer.
   * @param observer model actions observer
   * @param playerID corresponding player ID
   */
  @Override
  public void subscribe(ModelActions observer, int playerID) {
    if (playerID == 1) {
      this.observer1 = observer;
    } else {
      this.observer2 = observer;
    }
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public Cell getCellAt(int row, int col) {
    this.isGameStarted();
    if (row < 0 || row >= this.height) {
      throw new IllegalArgumentException("Row is out of bounds");
    }
    if (col < 0 || col >= this.width) {
      throw new IllegalArgumentException("Column is out of bounds");
    }
    return this.board.get(row).get(col); // returns an empty cell, pawns, or cell
  }

  /**
   * Calculates the total score of each player.
   */
  private void getTotalPlayerScore() {
    this.isGameStarted();
    int p1Score = 0;
    int p2Score = 0;
    for (int row = 0; row < this.board.size(); row++) {
      int p1RowScore = getP1RowScore(row);
      int p2RowScore = getP2RowScore(row);
      if (p1RowScore == p2RowScore) {
        continue;
      } else if (p1RowScore > p2RowScore) {
        p1Score += p1RowScore; // p1 + score, p2 + 0
      } else {
        p2Score += p2RowScore; // p2 + score, p1 + 0
      }
    }
    this.p1TotalScore = p1Score;
    this.p2TotalScore = p2Score;
  }

  @Override
  public Player getWinner() {
    this.getTotalPlayerScore();
    if (this.p1TotalScore == this.p2TotalScore) {
      return null;
    }
    if (this.p1TotalScore > this.p2TotalScore) {
      return players[0];
    } else {
      return players[1];
    }
  }

  @Override
  public int getWinningScore() {
    this.isGameStarted();
    this.getTotalPlayerScore();
    if (this.p1TotalScore == this.p2TotalScore) {
      return 0;
    } else if (this.p1TotalScore > this.p2TotalScore) {
      return this.p1TotalScore;
    }
    return this.p2TotalScore;
  }

  /**
   * Returns the score based on the given player color and row.
   * @param playerColor the color of the player
   * @param row the row to calculate th score of (0-index based)
   * @return the score of the row of the given player
   */
  private int getPlayerScore(Color playerColor, int row) {
    this.isGameStarted();
    int score = 0;
    for (int col = 0; col < this.board.get(row).size(); col++) {
      Cell cell = getCellAt(row, col);
      if (cell.isGameCard() && cell.getOwnedColor() == playerColor) {
        score += cell.getValue(); // gets the value of the card if Player owns color and is GameCard
      }
    }
    return score;
  }

  @Override
  public int getP1RowScore(int row) {
    return this.getPlayerScore(Color.red, row);
  }

  @Override
  public int getP2RowScore(int row) {
    return this.getPlayerScore(Color.blue, row);
  }

  @Override
  public List<GameCard> getHand(int playerID) {
    return players[playerID - 1].getHand();
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
    Cell cell = getCellAt(row, col);
    if (cell.getOwnedColor().equals(Color.red)) {
      return players[0];
    } else if (cell.getOwnedColor().equals(Color.blue)) {
      return players[1];
    } else {
      return null;
    }
  }

  @Override
  public Color getPlayerColor(int playerID) {
    return players[playerID - 1].getColor();
  }

  @Override
  public int getPlayerTotalScore(int playerID) {
    getTotalPlayerScore();
    if ((playerID - 1) == 0) {
      return p1TotalScore;
    } else {
      return p2TotalScore;
    }
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
