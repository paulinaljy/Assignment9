package cs3500.pawnsboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.controller.PawnsBoardPlayerController;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.strategy.BlockOpponent;
import cs3500.pawnsboard.strategy.ControlBoard;
import cs3500.pawnsboard.strategy.FillFirst;
import cs3500.pawnsboard.strategy.MaxRowScore;
import cs3500.pawnsboard.strategy.Move;
import cs3500.pawnsboard.strategy.Strategy;
import cs3500.pawnsboard.view.PawnsBoardView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Represents examples and tests of the strategies.
 **/
public class StrategyTest {
  private GameCard security;
  private GameCard bee;
  private GameCard sweeper;
  private GameCard crab;
  private GameCard queen;
  private GameCard mandragora;
  private GameCard trooper;
  private GameCard cavestalker;
  private GameCard lobber;
  private ArrayList<GameCard> p1Deck;
  private ArrayList<GameCard> p2Deck;
  private QueensBlood model;
  private FillFirst fillFirstStrategy;
  private MaxRowScore maxRowScoreStrategy;
  private ControlBoard controlBoardStrategy;
  private BlockOpponent blockOpponentStrategy;
  private BlockOpponent blockOpponentStrategy2;
  private ModelActions observer1;
  private ModelActions observer2;

  @Before
  public void setup() {
    Position leftSecurity = new Position(0, -1); // (2,1)
    Position rightSecurity = new Position(0, 1); // (2,3)
    Position topSecurity = new Position(-1, 0); // (1,2)
    Position bottomSecurity = new Position(1, 0); // (3,2)
    ArrayList<Position> securityInfluenceGrid = new ArrayList<Position>(Arrays.asList(topSecurity,
            leftSecurity, rightSecurity, bottomSecurity));

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

    Position leftLobber = new Position(0, -2);
    ArrayList<Position> lobberInfluenceGrid = new ArrayList<Position>(Arrays.asList(leftLobber));

    security = new GameCard("Security", GameCard.Cost.ONE, 2,
            securityInfluenceGrid);
    bee = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid);
    sweeper = new GameCard("Sweeper", GameCard.Cost.TWO, 2, sweeperInfluenceGrid);
    crab = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    queen = new GameCard("Queen", GameCard.Cost.ONE, 1, queenInfluenceGrid);
    mandragora = new GameCard("Mandragora", GameCard.Cost.ONE, 2,
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

    DeckConfiguration deckConfig = new PawnsBoardDeckConfig();
    model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    fillFirstStrategy = new FillFirst();
    maxRowScoreStrategy = new MaxRowScore();
    controlBoardStrategy = new ControlBoard();
    ArrayList<Strategy> opponentStrategies3 =
            new ArrayList<Strategy>(Arrays.asList(controlBoardStrategy,
                    maxRowScoreStrategy, fillFirstStrategy));
    ArrayList<Strategy> opponentStrategies2 =
            new ArrayList<Strategy>(Arrays.asList(fillFirstStrategy, maxRowScoreStrategy));
    blockOpponentStrategy = new BlockOpponent(opponentStrategies3);
    blockOpponentStrategy2 = new BlockOpponent(opponentStrategies2);

    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    GamePlayer humanPlayer1 = new HumanPlayer(model, 1);
    observer1 = new PawnsBoardPlayerController(model, humanPlayer1, view1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);
    GamePlayer humanPlayer2 = new HumanPlayer(model, 2);
    observer2 = new PawnsBoardPlayerController(model, humanPlayer2, view2);
  }

  // test for player 1 find first card that is placeable in first available cell (0,0)
  @Test
  public void testFillFirstCardPlayer1() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);

    assertEquals(Arrays.asList(cavestalker, bee, sweeper, mandragora, queen, security),
            model.getHand(model.getCurrentPlayerID())); // player 1's current hand

    assertTrue(model.getCellAt(0, 0).isCardPlaceable()); // first placeable cell
    assertEquals(1, model.getCellAt(0, 0).getValue()); // cell value = 1

    assertEquals(3, cavestalker.getCost());
    assertEquals(1, bee.getCost()); // first placeable card in (0,0)

    Assert.assertEquals(new Move(1, 0, 0, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // test for player 2 find first card that is placeable in first available cell (0,4)
  @Test
  public void testFillFirstCardPlayer2() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.pass();
    model.drawNextCard();

    assertTrue(model.getCellAt(0, 4).isCardPlaceable()); // first placeable cell
    assertEquals(1, model.getCellAt(0, 4).getValue()); // cell value = 1

    assertEquals(2, lobber.getCost());
    assertEquals(1, bee.getCost()); // first placeable card in (0,4)

    assertEquals(new Move(1, 0, 4, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // test for player 1 find first cell that is placeable with first playable card
  @Test
  public void testFillFirstCellPlayer1() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.placeCardInPosition(1, 0, 0);
    model.pass();

    assertFalse(model.getCellAt(0, 0).isCardPlaceable());
    assertTrue(model.getCellAt(2, 0).isCardPlaceable()); // first placeable cell
    assertEquals(2, model.getCellAt(2, 0).getValue()); // cell value = 2
    assertEquals(2, sweeper.getCost()); // first placeable card in (2,0)

    assertEquals(new Move(1, 2, 0, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // test for player 2 find first cell that is placeable with first playable card
  @Test
  public void testFillFirstCellPlayer2() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.pass();
    model.placeCardInPosition(1, 0, 4);
    model.pass();
    model.drawNextCard();

    assertFalse(model.getCellAt(0, 4).isCardPlaceable());
    assertTrue(model.getCellAt(2, 0).isCardPlaceable()); // first placeable cell
    assertEquals(2, model.getCellAt(2, 4).getValue()); // cell value = 2
    assertEquals(2, lobber.getCost()); // first placeable card in (2,4)

    assertEquals(new Move(0, 2, 4, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // unit test for reading rows correctly (from top to bottom)
  @Test
  public void testFillFirstReadCorrectlyPlayer1() {
    StringBuilder log = new StringBuilder();
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel(log, 5, 3, new Random(6));
    mockModel.startGame(p1Deck, p2Deck, 5, false);
    mockModel.subscribe(observer1, 1);
    mockModel.subscribe(observer2, 2);

    Move move = fillFirstStrategy.chooseMove(mockModel, mockModel.getCurrentPlayer());
    assertEquals("(0,0) ", log.toString());
  }

  @Test
  public void testFillFirstReadCorrectlyPlayer2() {
    StringBuilder log = new StringBuilder();
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel(log, 5, 3, new Random(6));
    mockModel.startGame(p1Deck, p2Deck, 5, true);
    mockModel.subscribe(observer1, 1);
    mockModel.subscribe(observer2, 2);
    mockModel.pass();

    Move move = fillFirstStrategy.chooseMove(mockModel, mockModel.getCurrentPlayer());
    assertEquals("(0,4) (0,3) (0,2) (0,1) (0,0) (1,4) (1,3) (1,2) (1,1) (1,0) (2,4) "
            + "(2,3) (2,2) (2,1) (2,0) (0,4) ", log.toString());
  }

  @Test
  public void testFillFirstPass() {
    model.startGame(new ArrayList<GameCard>(Arrays.asList(sweeper, trooper, cavestalker, lobber,
            sweeper, trooper, cavestalker, lobber, sweeper, trooper, cavestalker, lobber)),
            p2Deck, 5, true); // all cards cost = 2
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);

    assertEquals(new Move(-1, -1, -1, true),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // test for player 1 find highest value card placeable in first row
  @Test
  public void testMaxRowScorePlayer1() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);

    assertEquals(Arrays.asList(cavestalker, bee, sweeper, mandragora, queen, security),
            model.getHand(model.getCurrentPlayerID())); // player 1's current hand

    assertTrue(model.getCellAt(0, 0).isCardPlaceable()); // first placeable cell
    assertEquals(1, model.getCellAt(0, 0).getValue()); // cell value = 1

    assertEquals(0, model.getP1RowScore(0)); // current player 1 score at row 0
    assertEquals(0, model.getP2RowScore(0)); // current player 2 score at row 0

    assertEquals(4, cavestalker.getValue());
    assertEquals(3, cavestalker.getCost()); // highest value card not placeable

    assertEquals(2, sweeper.getValue());
    assertEquals(2, sweeper.getCost()); // next highest value card not placeable

    assertEquals(2, mandragora.getValue());
    assertEquals(1, mandragora.getCost()); // next highest card placeable in (0,0)

    assertEquals(new Move(3, 0, 0, false),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // test for player 2 find highest value card placeable in first row
  @Test
  public void testMaxRowScorePlayer2() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.placeCardInPosition(4, 0, 0);

    assertEquals(Arrays.asList(lobber, bee, mandragora, crab, sweeper, security),
            model.getHand(model.getCurrentPlayerID())); // player 2's current hand

    assertTrue(model.getCellAt(0, 4).isCardPlaceable()); // first placeable cell
    assertEquals(1, model.getCellAt(0, 4).getValue()); // cell value = 1

    assertEquals(1, model.getP1RowScore(0)); // current player 1 score at row 0
    assertEquals(0, model.getP2RowScore(0)); // current player 2 score at row 0

    assertEquals(1, lobber.getValue());
    assertEquals(2, mandragora.getValue());
    assertEquals(1, mandragora.getCost()); // highest value card placeable in (0,4)

    assertEquals(new Move(2, 0, 4, false),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  // unit test for reading rows correctly (from top to bottom)
  @Test
  public void testMaxRowScoreReadCorrectlyPlayer1() {
    StringBuilder log = new StringBuilder();
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel(log, 5,
            3, new Random(6));
    mockModel.startGame(p1Deck, p2Deck, 5, true);
    mockModel.subscribe(observer1, 1);
    mockModel.subscribe(observer2, 2);
    mockModel.pass();
    mockModel.placeCardInPosition(2, 0, 4); // player 2 placed mandragora, value 2

    // row 0 => no cards for p1 higher score
    // row 1 => mandragora => max row score
    Move move = maxRowScoreStrategy.chooseMove(mockModel, mockModel.getCurrentPlayer());
    assertEquals("(0,0) (0,1) (0,2) (0,3) (0,4) (1,0) (1,1) (1,2) (1,3) (1,4) (2,0) (2,1) " +
                    "(2,2) (2,3) (2,4) ",
            log.toString());
  }

  @Test
  public void testMaxRowScoreReadCorrectlyPlayer2() {
    StringBuilder log = new StringBuilder();
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel(log, 5,
            3, new Random(6));
    mockModel.startGame(p1Deck, new ArrayList<GameCard>(Arrays.asList(security, sweeper, trooper,
            cavestalker, lobber)), 5, false);
    mockModel.subscribe(observer1, 1);
    mockModel.subscribe(observer2, 2);
    mockModel.pass();
    mockModel.placeCardInPosition(0, 0, 4);
    mockModel.pass();

    // row 0 => move on
    // row 1 => (1,4) h0 => cavestalker => move on
    // (1,4) h1 => trooper => max row score
    Move move = maxRowScoreStrategy.chooseMove(mockModel, mockModel.getCurrentPlayer());
    assertEquals("(1,4) (1,3) (1,2) (1,1) (1,0) (2,4) (2,3) (2,2) (2,1) (2,0) ", log.toString());
  }

  // test returning pass when no cards placed will give increase player 1 score
  @Test
  public void testMaxRowScoreCellPass() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.pass();
    model.placeCardInPosition(1, 0, 4); // player 2 placed bee in (0,4)
    model.pass();

    model.drawNextCard();
    model.placeCardInPosition(4, 1, 4); // player 2 placed security in (1,4)
    model.pass();

    model.drawNextCard();
    model.placeCardInPosition(3, 2, 4); // player 2 placed security in (1,4)
    model.pass();

    model.drawNextCard();
    model.placeCardInPosition(1, 1, 3); // player 2 placed mandragora in (1,3)
    model.pass();

    model.drawNextCard();
    model.placeCardInPosition(3, 0, 3); // player 2 placed carb in (0,3)

    assertEquals(2, model.getP2RowScore(0));
    assertEquals(0, model.getP1RowScore(0));
    assertEquals(4, model.getP2RowScore(1));
    assertEquals(0, model.getP1RowScore(1));
    assertEquals(2, model.getP2RowScore(2));
    assertEquals(0, model.getP1RowScore(2));

    assertEquals(new Move(-1, -1, -1, true),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testControlBoardPlayer1() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);

    assertEquals(Arrays.asList(cavestalker, bee, sweeper, mandragora, queen, security),
            model.getHand(model.getCurrentPlayerID())); // player 1's current hand

    // (0,0) cavestalker, bee, sweeper, mandragora, queen, security
    // => max net influence 2 mandragora
    // (0,1) (0,2) (0,3) (0,4)
    // (1,0) cavestalker, bee, sweeper, mandragora, queen, security
    // => max net influence 2 mandragora
    // (1,1) (1,2) (1,3) (1,4)
    // (2,0) cavestalker, bee, sweeper, mandragora, queen, security
    // => max net influence 2 mandragora

    assertEquals(new Move(3, 0, 0, false),
            controlBoardStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testControlBoardPlayer2() {
    model.startGame(p1Deck, new ArrayList<GameCard>(Arrays.asList(bee, sweeper, crab, mandragora,
            trooper, queen, lobber, bee, sweeper, crab, mandragora, trooper, queen, lobber,
            bee, sweeper, crab, mandragora, trooper, queen, lobber)), 5, false);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.pass();
    model.placeCardInPosition(3, 2, 4); // placed mandragora
    model.pass();
    model.drawNextCard();
    assertEquals(Arrays.asList(bee, sweeper, crab, trooper, queen, lobber, bee),
            model.getHand(model.getCurrentPlayerID())); // player 2's current hand

    // (0,4) bee, sweeper, crab, trooper, queen => max net influence 1 crab
    // (0,3) (0,2) (0,1) (0,0)
    // (1,4) bee, sweeper, crab, trooper, queen => max net influence 2 sweeper
    // (1,3) (1,2) (1,1) (1,0)
    // (2,4) => cannot place card

    assertEquals(new Move(1, 1, 4, false),
            controlBoardStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testControlBoardReadCorrectlyPlayer1() {
    StringBuilder log = new StringBuilder();
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel(log, 5,
            3, new Random(6));
    mockModel.startGame(p1Deck, p2Deck, 5, true);
    mockModel.subscribe(observer1, 1);
    mockModel.subscribe(observer2, 2);

    Move move = controlBoardStrategy.chooseMove(mockModel, mockModel.getCurrentPlayer());
    assertEquals("(0,0) (0,1) (0,2) (0,3) (0,4) (1,0) (1,1) (1,2) (1,3) (1,4) (2,0) (2,1) "
            + "(2,2) (2,3) (2,4) ", log.toString());
  }

  @Test
  public void testControlBoardReadCorrectlyPlayer2() {
    StringBuilder log = new StringBuilder();
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel(log, 5,
            3, new Random(6));
    mockModel.startGame(p1Deck, new ArrayList<GameCard>(Arrays.asList(bee, sweeper, crab,
            mandragora, trooper, queen, lobber, bee, sweeper, crab, mandragora, trooper, queen,
            lobber, bee, sweeper, crab, mandragora, trooper, queen, lobber)),
            5, true);
    mockModel.subscribe(observer1, 1);
    mockModel.subscribe(observer2, 2);

    mockModel.pass();
    mockModel.placeCardInPosition(3, 2, 4); // placed mandragora
    mockModel.pass();
    mockModel.drawNextCard();

    Move move = controlBoardStrategy.chooseMove(mockModel, mockModel.getCurrentPlayer());
    assertEquals("(0,4) (0,3) (0,2) (0,1) (0,0) (1,4) (1,3) (1,2) (1,1) (1,0) (2,4) (2,3) "
            + "(2,2) (2,1) (2,0) ", log.toString());
  }

  @Test
  public void testControlBoardPass() {
    model.startGame(new ArrayList<GameCard>(Arrays.asList(bee, sweeper, queen, trooper,
            cavestalker, lobber, bee, sweeper, queen, trooper, cavestalker, bee, sweeper, queen,
            trooper, cavestalker)), p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    // cards have no net influence / cost > 1 cannot be placed

    assertEquals(new Move(-1, -1, -1, true),
            controlBoardStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testBlockOpponentPlayer1() {
    model.startGame(p1Deck, p2Deck, 5, false);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.placeCardInPosition(0, 1, 0); // p1 placed security
    model.drawNextCard();
    model.placeCardInPosition(0, 1, 4); // p2 placed security
    model.drawNextCard();
    model.placeCardInPosition(2, 1, 1); // p1 placed crab
    model.drawNextCard();
    assertEquals(Arrays.asList(bee, sweeper, crab, mandragora, queen, trooper, cavestalker, lobber),
            model.getHand(model.getCurrentPlayerID())); // opponent (player 2's) current hand

    // bee => target cell: (0,4)
    assertEquals(new Move(0, 0, 4, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));

    // trooper => target cell: (0,4)
    assertEquals(new Move(5, 0, 4, false),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));

    // crab => target cell: (1,3)
    assertEquals(new Move(2, 1, 3, false),
            controlBoardStrategy.chooseMove(model, model.getCurrentPlayer()));

    model.pass();
    model.drawNextCard();

    // player 1 => block best move (1,3) with mandragora in (1,2)
    assertEquals(new Move(2, 1, 2, false),
            blockOpponentStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testBlockOpponentPlayer2() {
    model.startGame(p1Deck, p2Deck, 5, false);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.placeCardInPosition(0, 1, 0); // p1 placed security
    model.drawNextCard();
    model.placeCardInPosition(3, 2, 4); // p2 placed crab
    model.drawNextCard();
    model.placeCardInPosition(2, 1, 1); // p1 placed crab
    model.drawNextCard();
    model.placeCardInPosition(2, 1, 4); // p2 placed sweeper
    model.drawNextCard();
    model.pass();
    model.drawNextCard();
    model.placeCardInPosition(4, 2, 3); // p2 placed trooper

    // bee => target cell: (0,0)
    assertEquals(new Move(0, 0, 0, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));

    // trooper => target cell: (0,0)
    assertEquals(new Move(4, 0, 0, false),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));

    // crab => target cell: (0,1)
    assertEquals(new Move(7, 1, 2, false),
            controlBoardStrategy.chooseMove(model, model.getCurrentPlayer()));

    model.pass();
    model.drawNextCard();

    assertEquals(new Move(8, 0, 3, false),
            blockOpponentStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testBlockOpponentPass() {
    model.startGame(p1Deck, p2Deck, 5, true);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    assertEquals(Arrays.asList(cavestalker, bee, sweeper, mandragora, queen, security),
            model.getHand(model.getCurrentPlayerID())); // player 1's current hand
    model.pass();
    model.drawNextCard();
    assertEquals(Arrays.asList(lobber, bee, mandragora, crab, sweeper, security, sweeper),
            model.getHand(model.getCurrentPlayerID())); // opponent (player 2's) current hand

    // bee => target cell: (0,4)
    assertEquals(new Move(1, 0, 4, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));

    // mandragora => target cell: (0,4)
    assertEquals(new Move(2, 0, 4, false),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));

    // crab => target cell: (0,4)
    assertEquals(new Move(3, 0, 4, false),
            controlBoardStrategy.chooseMove(model, model.getCurrentPlayer()));

    model.pass();
    // player 1 => no cards can block (0,4) (0,3)
    assertEquals(new Move(-1, -1, -1, true),
            blockOpponentStrategy.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testBlockOpponentDifferentStrategies() {
    model.startGame(p1Deck, p2Deck, 5, false);
    model.subscribe(observer1, 1);
    model.subscribe(observer2, 2);
    model.placeCardInPosition(0, 1, 0); // p1 placed security
    model.drawNextCard();
    model.placeCardInPosition(0, 1, 4); // p2 placed security
    model.drawNextCard();
    model.placeCardInPosition(2, 1, 1); // p1 placed crab
    model.drawNextCard();

    // bee => target cell: (0,4)
    assertEquals(new Move(0, 0, 4, false),
            fillFirstStrategy.chooseMove(model, model.getCurrentPlayer()));

    // trooper => target cell: (0,4)
    assertEquals(new Move(5, 0, 4, false),
            maxRowScoreStrategy.chooseMove(model, model.getCurrentPlayer()));

    model.pass();
    model.drawNextCard();

    // cannot find move with given strategies (max row score, fill first)
    assertEquals(new Move(-1, -1, -1, true),
            blockOpponentStrategy2.chooseMove(model, model.getCurrentPlayer()));
  }
}
