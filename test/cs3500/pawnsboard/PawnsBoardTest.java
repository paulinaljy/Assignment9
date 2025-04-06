package cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardPlayerController;
import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.EmptyCell;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.model.Pawns;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.model.PawnsBoardBuilder;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.view.PawnsBoardTextualView;
import cs3500.pawnsboard.view.PawnsBoardView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Represents examples and tests for the model of PawnsBoard.
 */
public class PawnsBoardTest {
  private Position leftSecurity;
  private Position rightSecurity;
  private Position topSecurity;
  private Position bottomSecurity;
  private ArrayList<Position> securityInfluenceGrid;
  private EmptyCell emptyCell;
  private Pawns redPawns;
  private Pawns bluePawns;
  private GameCard security;
  private GameCard bee;
  private GameCard sweeper;
  private GameCard crab;
  private GameCard queen;
  private GameCard mandragora;
  private GameCard trooper;
  private GameCard cavestalker;
  private GameCard lobber;
  private QueensBlood game1;
  private ArrayList<GameCard> p1Deck;
  private ArrayList<GameCard> p2Deck;
  private Player player1;
  private Player player2;
  private DeckConfiguration deckConfig;
  private ModelActions observer1;
  private ModelActions observer2;

  @Before
  public void setup() {
    leftSecurity = new Position(0, -1); // (2,1)
    rightSecurity = new Position(0, 1); // (2,3)
    topSecurity = new Position(-1, 0); // (1,2)
    bottomSecurity = new Position(1, 0); // (3,2)
    securityInfluenceGrid = new ArrayList<Position>(Arrays.asList(topSecurity, leftSecurity,
            rightSecurity, bottomSecurity));

    Position topMandragora = new Position(-1, 0); // (1,2)
    Position right1Mandragora = new Position(0, 1); // (2,3)
    Position right2Mandragora = new Position(0, 2); // (2,4)
    ArrayList<Position> mandragoraInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topMandragora, right1Mandragora, right2Mandragora));

    Position topBee = new Position(-2, 0);
    Position bottomBee = new Position(2, 0);
    ArrayList<Position> beeInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topBee, bottomBee));

    Position top1Sweeper = new Position(-1, -1);
    Position top2Sweeper = new Position(-1, 0);
    Position bottom1Sweeper = new Position(1, -1);
    Position bottom2Sweeper = new Position(1, 0);
    ArrayList<Position> sweeperInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            top1Sweeper, top2Sweeper, bottom1Sweeper, bottom2Sweeper));

    Position leftCrab = new Position(0, -1); // (2,1)
    Position rightCrab = new Position(0, 1); // (2,3)
    Position topCrab = new Position(-1, 0); // (1,2)
    ArrayList<Position> crabInfluenceGrid = new ArrayList<Position>(
            Arrays.asList(topCrab, leftCrab, rightCrab));

    Position topQueen = new Position(-2, 0); // (0,2)
    ArrayList<Position> queenInfluenceGrid = new ArrayList<Position>(Arrays.asList(topQueen));

    Position top1Trooper = new Position(-2, 0);
    Position top2Trooper = new Position(-1, 1);
    Position rightTrooper = new Position(0, 1);
    Position bottom1Trooper = new Position(1, 0);
    Position bottom2Trooper = new Position(2, 0);
    ArrayList<Position> trooperInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            top1Trooper, top2Trooper, rightTrooper, bottom1Trooper, bottom2Trooper));

    Position topCaveStalker = new Position(-2, 0);
    Position right1CaveStalker = new Position(-1, 1);
    Position right2CaveStalker = new Position(0, 1);
    Position right3CaveStalker = new Position(1, 1);
    Position bottomCaveStalker = new Position(2, 0);
    ArrayList<Position> cavestalkerInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topCaveStalker, right1CaveStalker, right2CaveStalker,
            right3CaveStalker, bottomCaveStalker));

    Position rightLobber = new Position(0, 2);
    ArrayList<Position> lobberInfluenceGrid = new ArrayList<Position>(Arrays.asList(rightLobber));

    emptyCell = new EmptyCell();
    redPawns = new Pawns(Color.red);
    bluePawns = new Pawns(Color.blue);

    security = new GameCard("Security", GameCard.Cost.ONE, 1,
            securityInfluenceGrid);
    bee = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid);
    sweeper = new GameCard("Sweeper", GameCard.Cost.TWO, 2, sweeperInfluenceGrid);
    crab = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    queen = new GameCard("Queen", GameCard.Cost.ONE, 1, queenInfluenceGrid);
    mandragora = new GameCard("Mandragora", GameCard.Cost.ONE, 1,
            mandragoraInfluenceGrid);
    trooper = new GameCard("Trooper", GameCard.Cost.TWO, 3, trooperInfluenceGrid);
    cavestalker = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid);
    lobber = new GameCard("Lobber", GameCard.Cost.TWO, 1, lobberInfluenceGrid);

    p1Deck = new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper, crab, mandragora, queen,
            trooper, cavestalker, lobber, security, bee, sweeper, crab, mandragora, queen, trooper,
            cavestalker, lobber));

    p2Deck = new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper, crab, mandragora, queen,
            trooper, cavestalker, lobber, security, bee, sweeper, crab, mandragora, queen,
            trooper, cavestalker, lobber));

    deckConfig = new PawnsBoardDeckConfig();

    game1 = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    player1 = new Player(Color.RED, p1Deck, 5, new Random(6), true);
    player2 = new Player(Color.BLUE, p2Deck, 5, new Random(6), true);

    PawnsBoardView view1 = new MockPawnsBoardView(game1, 1);
    GamePlayer humanPlayer1 = new HumanPlayer(game1, 1);
    observer1 = new PawnsBoardPlayerController(game1, humanPlayer1, view1);
    PawnsBoardView view2 = new MockPawnsBoardView(game1, 2);
    GamePlayer humanPlayer2 = new HumanPlayer(game1, 2);
    observer2 = new PawnsBoardPlayerController(game1, humanPlayer2, view2);
  }

  @Test
  public void testModelInvalidConstructorHeight() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PawnsBoardModel(5, -2, new Random(6), deckConfig);
    });
  }

  @Test
  public void testModelInvalidConstructorWidthNegative() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PawnsBoardModel(-5, 3, new Random(6), deckConfig);
    });
  }

  @Test
  public void testModelInvalidConstructorWidthEven() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PawnsBoardModel(4, 4, new Random(6), deckConfig);
    });
  }

  @Test
  public void testModelInvalidConstructorNullRandom() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PawnsBoardModel(7, 4, null, deckConfig);
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceInvalidCardIdx() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.placeCardInPosition(-1, 1, 1);
    game1.placeCardInPosition(10, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceInvalidRow() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.placeCardInPosition(1, -2, 1);
    game1.placeCardInPosition(2, 9, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceInvalidCol() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.placeCardInPosition(1, 1, -5);
    game1.placeCardInPosition(3, 2, 10);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCannotAddCard() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.placeCardInPosition(1, 0, 2); // cell is empty

    game1.placeCardInPosition(4, 2, 0); // place card in (2,0)
    game1.placeCardInPosition(4, 2, 0); // cell already has game card
  }

  @Test(expected = IllegalStateException.class)
  public void testPlacePawnNotSameColor() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.placeCardInPosition(4, 1, 4); // pawns are blue (rather than red)
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceNotEnoughPawns() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    // only 1 pawn, rather than cost of card (2)
    game1.placeCardInPosition(2, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceGameNotStarted() {
    game1.placeCardInPosition(0, 0, 0);
  }

  @Test
  public void testValidPlaceCardInPosition() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    // check player hand
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, security)), game1.getHand(game1.getCurrentPlayerID()));

    // check cell has pawns
    assertTrue(game1.getCellAt(2, 0).isCardPlaceable());

    // check center cell has enough pawns
    assertEquals(1, game1.getCellAt(2, 0).getValue());

    // check influenced cells
    assertFalse(game1.getCellAt(2, 1).isCardPlaceable()); // (2,1) empty cell
    assertFalse(game1.getCellAt(2, 2).isCardPlaceable()); // (2,2) empty cell
    assertEquals(1, game1.getCellAt(1, 0).getValue()); // (1,0) 1 pawn
    assertEquals(1, game1.getCellAt(1, 0).getValue()); // 1 pawn

    game1.placeCardInPosition(3, 2, 0);

    // check updated player hand
    game1.pass();
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, queen, security,
                    sweeper)),
            game1.getHand(game1.getCurrentPlayerID()));

    // check center cell changed - is not pawns but game card
    assertFalse(game1.getCellAt(2, 0).isCardPlaceable());
    assertTrue(game1.getCellAt(2, 0).isGameCard());

    // check game card cell color changed
    assertEquals(Color.red, game1.getCellAt(2, 0).getOwnedColor());

    // check influenced cells changed
    assertTrue(game1.getCellAt(2, 1).isCardPlaceable()); // (2,1)
    assertEquals(1, game1.getCellAt(2, 1).getValue()); // added pawn
    assertTrue(game1.getCellAt(2, 2).isCardPlaceable()); // (2,2)
    assertEquals(1, game1.getCellAt(2, 2).getValue()); // added pawn
    assertEquals(2, game1.getCellAt(1, 0).getValue()); // 1 pawn => 2 pawn
  }

  @Test
  public void testPlaceCardOffBoard() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(1, game1.getCellAt(0, 0).getValue());

    // player 1 placed bee in (1,0) with all influenced cells off board
    game1.placeCardInPosition(1, 1, 0);

    // no cells affected
    assertEquals(1, game1.getCellAt(0, 0).getValue());
  }

  @Test
  public void testInfluenceCellAddPawn() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertFalse(game1.getCellAt(1, 1).isCardPlaceable()); // (1,1) empty cell
    assertEquals(0, game1.getCellAt(1, 1).getValue());

    assertFalse(game1.getCellAt(1, 2).isCardPlaceable()); // (1,2) empty cell
    assertEquals(0, game1.getCellAt(1, 2).getValue());

    game1.placeCardInPosition(3, 1, 0); // player 1 placed mandragona in (1,0)

    // influenced cells that changed from empty => added pawn
    assertTrue(game1.getCellAt(1, 1).isCardPlaceable()); // (1,1) pawns
    assertEquals(1, game1.getCellAt(1, 1).getValue()); // added pawn

    assertTrue(game1.getCellAt(1, 2).isCardPlaceable()); // (1,2) pawns
    assertEquals(1, game1.getCellAt(1, 2).getValue()); // added pawn
  }

  @Test
  public void testInfluenceCellIncreasePawn() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    game1.pass();

    assertEquals(1, game1.getCellAt(0, 4).getValue()); // 1 pawn

    game1.placeCardInPosition(3, 1, 4); // player 2 placed crab in (1,4)

    // influenced cells that increased in pawns
    assertEquals(2, game1.getCellAt(0, 4).getValue()); // 1 pawn => 2 pawn
  }

  @Test
  public void testInfluenceCellChangePawnOwnership() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.placeCardInPosition(3, 1, 0); // player 1 placed mandragona in (1,0)
    assertTrue(game1.getCellAt(1, 2).isCardPlaceable()); // (1,2) red pawns
    assertEquals(1, game1.getCellAt(1, 2).getValue()); // owned by player 1

    game1.placeCardInPosition(3, 1, 4); // player 2 placed crab in (1,4)
    assertTrue(game1.getCellAt(1, 3).isCardPlaceable()); // (1,3) blue pawns
    assertEquals(Color.blue, game1.getCellAt(1, 3).getOwnedColor()); // owned by player 2

    game1.drawNextCard();
    game1.placeCardInPosition(4, 1, 2); // player 1 placed security in (1,2)
    // influenced cell that changed ownership
    // player 2 (blue) => player 1 (red)
    assertEquals(Color.red, game1.getCellAt(1, 3).getOwnedColor());
    assertEquals(1, game1.getCellAt(1, 3).getValue());
  }

  @Test(expected = IllegalStateException.class)
  public void testPassGameNotStarted() {
    game1.pass();
  }

  @Test
  public void testPass() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertEquals(player1.toString(), game1.getCurrentPlayer().toString()); // pass = 1 => player 1
    game1.pass();
    assertEquals(player2.toString(), game1.getCurrentPlayer().toString()); // pass = 2 => player 2
  }

  @Test(expected = IllegalStateException.class)
  public void testDrawNextCardGameNotStarted() {
    game1.drawNextCard();
  }

  @Test
  public void testDrawNextCard() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, security)), game1.getHand(game1.getCurrentPlayerID()));

    game1.drawNextCard();

    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, security, sweeper)), game1.getHand(game1.getCurrentPlayerID()));
  }

  @Test
  public void testDrawNextCardNoCardsLeft() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();
    game1.drawNextCard();

    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, security, sweeper, crab, trooper, lobber, security, bee, crab, mandragora,
            queen, trooper, cavestalker, lobber)), game1.getHand(game1.getCurrentPlayerID()));
    game1.drawNextCard();
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, security, sweeper, crab, trooper, lobber, security, bee, crab, mandragora,
            queen, trooper, cavestalker, lobber)), game1.getHand(game1.getCurrentPlayerID()));
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameAlreadyStarted() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.startGame(p1Deck, p2Deck, 6, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDecksNull() {
    game1.startGame(null, p2Deck, 5, true);
    GameCard nullObject = null;
    game1.startGame(new ArrayList<GameCard>(Arrays.asList(nullObject)), p2Deck, 5, true);

    game1.startGame(p1Deck, null, 5, true);
    game1.startGame(p2Deck, new ArrayList<GameCard>(Arrays.asList(nullObject)),
            5, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckSize() {
    game1.startGame(new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper)), p2Deck,
            5, true);
    game1.startGame(p1Deck, new ArrayList<GameCard>(Arrays.asList(security)),
            5, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidNegativeHandSize() {
    game1.startGame(p1Deck, p2Deck,
            -5, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidHandSize() {
    game1.startGame(p1Deck, p2Deck,
            30, true);
  }

  @Test
  public void testStartGame() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertEquals(player1.toString(), game1.getCurrentPlayer().toString()); // player 1 created
    game1.setNextPlayer();
    assertEquals(player2.toString(), game1.getCurrentPlayer().toString()); // player 2 created

    game1.placeCardInPosition(3, 2, 4); // place card works without exception
    // => game has started

    assertFalse(game1.isGameOver()); // pass = 0, game is not over
  }

  @Test
  public void testGetWidth() {
    assertEquals(5, game1.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(3, game1.getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCellAtGameNotStarted() {
    game1.getCellAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellAtInvalidRow() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.getCellAt(-1, 1);
    game1.getCellAt(10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellAtInvalidCol() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.getCellAt(2, -2);
    game1.getCellAt(1, 8);
  }

  @Test
  public void testGetCellAtPawns() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    assertEquals(redPawns, game1.getCellAt(0, 0));
  }

  @Test
  public void testGetCellAtEmptyCell() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    assertEquals(emptyCell, game1.getCellAt(0, 2));
  }

  @Test
  public void testGetCellAtGameCard() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    game1.placeCardInPosition(3, 1, 0);
    assertEquals(mandragora, game1.getCellAt(1, 0));
  }

  @Test
  public void testGetHand() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, security)), game1.getHand(game1.getCurrentPlayerID()));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameNotStarted() {
    game1.getWinner();
  }

  @Test
  public void testGetWinner() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.pass();
    game1.placeCardInPosition(0, 0, 4);
    assertEquals(player2, game1.getWinner());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinningScoreGameNotStarted() {
    game1.getWinningScore();
  }

  @Test
  public void testGetWinningScore() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.drawNextCard();
    game1.pass();

    game1.drawNextCard();
    game1.placeCardInPosition(0, 1, 4);

    game1.drawNextCard();
    game1.placeCardInPosition(3, 1, 0);

    game1.drawNextCard();
    game1.placeCardInPosition(3, 1, 3);
    assertEquals(2, game1.getWinningScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetP1ScoreGameNotStarted() {
    game1.getP1RowScore(0);
  }

  @Test
  public void testGetP1Score() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.drawNextCard();
    game1.placeCardInPosition(0, 0, 0);
    game1.pass();
    game1.drawNextCard();
    game1.placeCardInPosition(3, 0, 1);
    assertEquals(2, game1.getP1RowScore(0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetP2ScoreGameNotStarted() {
    game1.getP2RowScore(0);
  }

  @Test
  public void testGetP2Score() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.pass();
    game1.placeCardInPosition(0, 0, 4);
    assertEquals(1, game1.getP2RowScore(0));
  }

  @Test(expected = IllegalStateException.class)
  public void testSetNextPlayerGameNotStarted() {
    game1.setNextPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCurrentPlayerGameNotStarted() {
    game1.getCurrentPlayer();
  }

  @Test
  public void testGetCurrentNextPlayer() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(player1.toString(), game1.getCurrentPlayer().toString()); // player 1 turn

    game1.setNextPlayer(); // update turn
    assertEquals(player2.toString(), game1.getCurrentPlayer().toString()); // player 2 turn
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverGameNotStarted() {
    game1.isGameOver();
  }

  @Test
  public void testIsGameOver() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertFalse(game1.isGameOver());

    game1.pass();
    game1.pass();
    assertTrue(game1.isGameOver());
  }

  @Test
  public void testGetBoard() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    ArrayList<Cell> row = new ArrayList<Cell>(Arrays.asList(redPawns, emptyCell, emptyCell,
            emptyCell, bluePawns));
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    board.add(row);
    board.add(row);
    board.add(row);

    assertEquals(board, game1.getBoard());
  }

  @Test
  public void testGetOwnerOfCell() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    assertEquals(player1, game1.getOwnerOfCell(0,0));
  }

  @Test
  public void testGetPlayerColor() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    assertEquals(Color.red, game1.getPlayerColor(1));
    assertEquals(Color.blue, game1.getPlayerColor(2));
  }

  @Test
  public void testGetPlayerTotalScore() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.placeCardInPosition(0, 0, 0);
    game1.placeCardInPosition(1, 1, 4);
    assertEquals(1, game1.getPlayerTotalScore(1));
    assertEquals(1, game1.getPlayerTotalScore(2));
  }

  @Test
  public void testGetCurrentPlayerID() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(1, game1.getCurrentPlayerID());
    game1.pass();
    assertEquals(2, game1.getCurrentPlayerID());
  }

  @Test
  public void testGetPlayerByColor() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(player1, game1.getPlayerByColor(Color.red));
    assertEquals(player2, game1.getPlayerByColor(Color.blue));
  }

  // TESTS FOR DECK CONFIGUTATION
  @Test
  public void testLoadDeckConfig() throws FileNotFoundException {
    String path = "docs" + File.separator + "deck.config";
    File config = new File(path);
    FileReader fileReader = new FileReader(config);
    assertEquals(p1Deck.toString(), deckConfig.loadDeckConfig(fileReader).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testLoadDeckConfigInvalidDeck() throws FileNotFoundException {
    String path = "docs" + File.separator + "InvalidDeck.config";
    File config = new File(path);
    FileReader fileReader = new FileReader(config);
    assertEquals(p1Deck.toString(), deckConfig.loadDeckConfig(fileReader).toString());
  }

  // TESTS FOR TEXTUAL VIEW
  @Test
  public void testTextualViewToString() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    PawnsBoardTextualView view = new PawnsBoardTextualView(game1);
    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";

    assertEquals(expected, view.toString());

    game1.drawNextCard();
    game1.placeCardInPosition(4, 1, 0);
    String newP1Expected =
            "0 2___1 0\n"
                    + "1 R11_1 0\n"
                    + "0 1___1 0\n";

    assertEquals(newP1Expected, view.toString());

    game1.drawNextCard();
    game1.placeCardInPosition(0, 1, 4);
    String newP2Expected =
            "0 2___2 0\n"
                    + "1 R111B 1\n"
                    + "0 1___2 0\n";
    assertEquals(newP2Expected, view.toString());
  }

  @Test
  public void testTextualViewRender() throws IOException {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    PawnsBoardTextualView view = new PawnsBoardTextualView(game1);
    StringBuilder output = new StringBuilder();

    view.render(output);
    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";

    assertEquals(expected, output.toString());

    game1.drawNextCard();
    game1.placeCardInPosition(4, 1, 0);
    view.render(output);
    String newP1Expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 2___1 0\n"
                    + "1 R11_1 0\n"
                    + "0 1___1 0\n";

    assertEquals(newP1Expected, output.toString());

    game1.drawNextCard();
    game1.placeCardInPosition(0, 1, 4);
    view.render(output);
    String newP2Expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 2___1 0\n"
                    + "1 R11_1 0\n"
                    + "0 1___1 0\n"
                    + "0 2___2 0\n"
                    + "1 R111B 1\n"
                    + "0 1___2 0\n";
    assertEquals(newP2Expected, output.toString());
  }

  // TESTS FOR PLAYER
  @Test
  public void testPlayerGetCard() {
    assertEquals(cavestalker, player1.getCard(0));
  }

  @Test
  public void testPlayerGetColor() {
    assertEquals(Color.red, player1.getColor());
    assertEquals(Color.blue, player2.getColor());
  }

  @Test
  public void testPlayerGetHand() {
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen)), player1.getHand());
  }

  @Test
  public void testPlayerRemoveCard() {
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen)), player1.getHand());
    assertEquals(sweeper, player1.removeCard(2));
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, mandragora, queen)),
            player1.getHand());
  }

  @Test
  public void testPlayerDrawNextCard() {
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen)), player1.getHand());
    player1.drawNextCard(2);
    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee, sweeper, mandragora,
            queen, crab)), player1.getHand());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayerDrawNextCardCannotDraw() {
    // game cards = 0
    Player pEmptyDeck = new Player(Color.RED, new ArrayList<>(), 5,
            new Random(), false);
    pEmptyDeck.drawNextCard(0);
  }

  @Test
  public void testPlayerGetDeck() {
    assertEquals(new ArrayList<GameCard>(Arrays.asList(security, sweeper, crab, trooper,
                    lobber, security, bee, crab, mandragora, queen, trooper, cavestalker, lobber)),
            player1.getDeck());

    player1.drawNextCard(0);

    assertEquals(new ArrayList<GameCard>(Arrays.asList(sweeper, crab, trooper,
                    lobber, security, bee, crab, mandragora, queen, trooper, cavestalker, lobber)),
            player1.getDeck());
  }

  @Test
  public void testPlayerGetHandSize() {
    assertEquals(5, player1.getHandSize());
  }

  // TESTS FOR EMPTY CELL
  @Test
  public void testEmptyCellGetCost() {
    assertEquals(4, emptyCell.getCost());
  }

  @Test
  public void testEmptyCellGetValue() {
    assertEquals(0, emptyCell.getValue());
  }

  @Test
  public void testEmptyCellGetColor() {
    assertEquals(Color.gray, emptyCell.getOwnedColor());
  }

  @Test
  public void testEmptyCellIsGameCard() {
    assertFalse(emptyCell.isGameCard());
  }

  @Test
  public void testEmptyCellToString() {
    assertEquals("_", emptyCell.toString());
  }

  @Test
  public void testEmptyCellGetOwnedColor() {
    assertEquals(Color.gray, emptyCell.getOwnedColor());
  }

  @Test
  public void testEmptyCellGetCellColor() {
    assertEquals(Color.gray, emptyCell.getCellColor());
  }

  // TESTS FOR GAME CARD
  @Test
  public void testGameCardInvalidConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GameCard("Security", GameCard.Cost.ONE, -1, securityInfluenceGrid);
    });
  }

  @Test
  public void testGameCardGetCost() {
    assertEquals(1, security.getCost());
    assertEquals(2, sweeper.getCost());
    assertEquals(3, cavestalker.getCost());
  }

  @Test
  public void testGameCardGetColor() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(Color.white, game1.getHand(game1.getCurrentPlayerID()).get(1).getOwnedColor());

    game1.placeCardInPosition(1, 0, 0);

    assertEquals(Color.red, game1.getHand(game1.getCurrentPlayerID()).get(1).getOwnedColor());
  }

  @Test
  public void testGameCardGetValue() {
    assertEquals(1, security.getValue());
  }

  @Test
  public void testGameCardIsGameCard() {
    assertTrue(security.isGameCard());
  }

  @Test
  public void testGameCardGetPositions() {
    assertEquals(securityInfluenceGrid, security.getPositions());
  }

  @Test
  public void testValueToCost() {
    assertEquals(GameCard.Cost.ONE, security.valueToCost(1));
    assertEquals(GameCard.Cost.TWO, security.valueToCost(2));
    assertEquals(GameCard.Cost.THREE, security.valueToCost(3));
  }

  @Test
  public void testGameCardToString() {
    game1.startGame(p1Deck, p2Deck, 5, true);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(new ArrayList<GameCard>(Arrays.asList(cavestalker, bee,
                    sweeper, mandragora, queen, security)),
            game1.getHand(game1.getCurrentPlayerID()));

    game1.placeCardInPosition(1, 2, 0); // player 1 placed card
    game1.placeCardInPosition(2, 0, 4); // player 2 placed card

    assertEquals("R", game1.getCellAt(2, 0).toString());
    assertEquals("B", game1.getCellAt(0, 4).toString());
  }

  @Test
  public void testGameCardGetOwnedColor() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    game1.placeCardInPosition(0, 0, 0); // player 1 placed card

    assertEquals(Color.red, security.getOwnedColor());
  }

  @Test
  public void testGameCardGetCellColor() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    game1.placeCardInPosition(0, 0, 0); // player 1 placed card

    assertEquals(Color.red, security.getCellColor());
  }

  // TESTS FOR PAWNS
  @Test
  public void testPawnsGetCost() {
    assertEquals(1, redPawns.getCost());
  }

  @Test
  public void testPawnsGetValue() {
    assertEquals(1, redPawns.getValue());
  }

  @Test
  public void testPawnsGetColor() {
    assertEquals(Color.red, redPawns.getOwnedColor());
  }

  @Test
  public void testPawnsToString() {
    assertEquals("1", redPawns.toString());
  }

  @Test
  public void testPawnsGetOwnedColor() {
    assertEquals(Color.red, redPawns.getOwnedColor());
  }

  @Test
  public void testPawnsGetCellColor() {
    assertEquals(Color.gray, redPawns.getCellColor());
  }

  // TESTS FOR POSITION
  @Test
  public void testGetRowDelta() {
    assertEquals(0, leftSecurity.getRowDelta());
    assertEquals(1, bottomSecurity.getRowDelta());
  }

  @Test
  public void testGetColDelta() {
    assertEquals(1, rightSecurity.getColDelta());
    assertEquals(0, topSecurity.getColDelta());
  }

  // TESTS FOR PAWNS BOARD BUILDER
  @Test
  public void testPawnsBoardBuilder() {
    QueensBlood pawnsBoard = new PawnsBoardBuilder()
            .setHeight(3)
            .setWidth(5)
            .build();
    pawnsBoard.startGame(p1Deck, p2Deck, 5, false);
    pawnsBoard.subscribe(observer1, 1);
    pawnsBoard.subscribe(observer2, 2);
    assertEquals(player1, pawnsBoard.getCurrentPlayer());
  }
}
