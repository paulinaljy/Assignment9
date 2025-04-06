package cs3500.pawnsboard.controller;

import java.awt.Point;
import java.io.IOException;

import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.view.PawnsBoardView;
import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents a GUI player controller for the Pawns Board game with a model, view, game player.
 * Behaviors include handling user interactions and notifying the model, view, and player at every
 * change in game state, including when the player wants to place a card, pass, and its their turn.
 */
public class PawnsBoardPlayerController implements PawnsBoardController, ViewActions, ModelActions {
  private QueensBlood model;
  private PawnsBoardView view;
  private GamePlayer player;
  private Appendable transcript;
  private int cardIdxSelected;
  private Point cellSelected;
  private boolean viewEnabled;

  /**
   * Constructs a GUI controller for the game.
   *
   * @param model model
   * @param view player's GUI view
   * @param player player
   * @throws IllegalArgumentException if model or view is null.
   */
  public PawnsBoardPlayerController(QueensBlood model, GamePlayer player, PawnsBoardView view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (player == null) {
      throw new IllegalArgumentException("GamePlayer cannot be null");
    }
    this.model = model;
    this.view = view;
    this.transcript = System.out;
    this.viewEnabled = false;
    this.player = player;
    this.cellSelected = null;
    this.cardIdxSelected = -1;
  }

  @Override
  public void playGame() {
    this.view.subscribe(this); // view subscribed to controller
    this.model.subscribe(this, player.getPlayerID()); // model subscribed to controller
    this.player.subscribe(this); // player (machine) subscribed to controller
    this.view.makeVisible();
  }

  /**
   * Appends the given message to the transcript.
   * @param message string message
   */
  private void addTranscript(String message) {
    try {
      transcript.append(message + "\n");
    } catch (IOException e) {
      // ignore exception
    }
  }

  @Override
  public void placeCard() {
    String currentPlayer = this.player.toString();
    int originalCardSelected = this.cardIdxSelected;
    Point originalCellSelected = this.cellSelected;
    if (cardIdxSelected == -1) {
      view.displayMessage(currentPlayer + ": " + "Please " +
              "select a card from hand first.", "Message");
      return;
    }
    if (cellSelected == null) {
      view.displayMessage(currentPlayer + ": " + "Please " +
              "select a cell on the board first.", "Message");
      return;
    }
    try {
      addTranscript(currentPlayer + " placed card " + cardIdxSelected);
      cardIdxSelected = -1;
      cellSelected = null;
      viewEnabled = false;
      model.placeCardInPosition(originalCardSelected, (int)originalCellSelected.getX(),
              (int)originalCellSelected.getY());
      view.refresh();
      view.reset();
    } catch (IllegalArgumentException | IllegalStateException e) {
      cardIdxSelected = originalCardSelected;
      cellSelected = originalCellSelected;
      viewEnabled = true;
      view.displayMessage(currentPlayer + ": " + e.getMessage()
              + "Please play again.", "Invalid Move");
    }
  }

  @Override
  public void pass() {
    String currentPlayer = this.player.toString();
    addTranscript(currentPlayer + " passed");
    try {
      model.pass();
      cardIdxSelected = -1;
      cellSelected = null;
      view.reset();
      viewEnabled = false;
    } catch (IllegalStateException e) {
      view.displayMessage(e.getMessage(), "Game Not Started");
    }
    model.drawNextCard();
    view.refresh();
  }

  @Override
  public void setCardIdx(int cardIdx) {
    cardIdxSelected = cardIdx;
    addTranscript("Player " + model.getCurrentPlayerID() + " selected card " + cardIdx);
  }

  @Override
  public void setSelectedCell(int row, int col) {
    cellSelected = new Point(row, col);
    addTranscript("Cell (" + row + "," + col + ") selected");
  }

  @Override
  public boolean isViewEnabled() {
    return viewEnabled;
  }

  @Override
  public void processGameOver() {
    view.displayGameOver();
  }

  @Override
  public void itsYourTurn() {
    if (model.isGameOver()) {
      processGameOver();
      return;
    }
    if (player.isHumanPlayer()) {
      viewEnabled = true;
      view.refresh();
      String currentPlayer = "Player 1";
      if (player.getPlayerID() == 2) {
        currentPlayer = "Player 2";
      }
      view.displayMessage(currentPlayer + ": It's your turn!", "It's Your Turn!");
    }
    player.chooseMove();
  }

  @Override
  public void refreshView() {
    view.refresh();
  }
}

