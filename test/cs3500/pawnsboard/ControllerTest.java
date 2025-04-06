package cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardController;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.controller.PawnsBoardPlayerController;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.player.MachinePlayer;
import cs3500.pawnsboard.strategy.ControlBoard;
import cs3500.pawnsboard.strategy.FillFirst;
import cs3500.pawnsboard.strategy.Move;
import cs3500.pawnsboard.view.PawnsBoardFrame;
import cs3500.pawnsboard.view.PawnsBoardView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Examples and tests for the controller.
 */
public class ControllerTest {
  private QueensBlood game1;
  private ArrayList<GameCard> p1Deck;
  private ArrayList<GameCard> p2Deck;
  private DeckConfiguration deckConfig;

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

    Position rightLobber = new Position(0, 2);
    ArrayList<Position> lobberInfluenceGrid = new ArrayList<Position>(Arrays.asList(rightLobber));

    GameCard security1 = new GameCard("Security", GameCard.Cost.ONE, 2,
            securityInfluenceGrid);
    GameCard bee1 = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid);
    GameCard sweeper1 = new GameCard("Sweeper", GameCard.Cost.TWO,
            2, sweeperInfluenceGrid);
    GameCard crab1 = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    GameCard queen1 = new GameCard("Queen", GameCard.Cost.ONE,
            1, queenInfluenceGrid);
    GameCard mandragora1 = new GameCard("Mandragora", GameCard.Cost.ONE, 2,
            mandragoraInfluenceGrid);
    GameCard trooper1 = new GameCard("Trooper", GameCard.Cost.TWO,
            3, trooperInfluenceGrid);
    GameCard cavestalker1 = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid);
    GameCard lobber1 = new GameCard("Lobber", GameCard.Cost.TWO,
            1, lobberInfluenceGrid);

    GameCard security2 = new GameCard("Security", GameCard.Cost.ONE, 2,
            securityInfluenceGrid);
    GameCard bee2 = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid);
    GameCard sweeper2 = new GameCard("Sweeper", GameCard.Cost.TWO,
            2, sweeperInfluenceGrid);
    GameCard crab2 = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    GameCard queen2 = new GameCard("Queen", GameCard.Cost.ONE,
            1, queenInfluenceGrid);
    GameCard mandragora2 = new GameCard("Mandragora", GameCard.Cost.ONE, 2,
            mandragoraInfluenceGrid);
    GameCard trooper2 = new GameCard("Trooper", GameCard.Cost.TWO,
            3, trooperInfluenceGrid);
    GameCard cavestalker2 = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid);
    GameCard lobber2 = new GameCard("Lobber", GameCard.Cost.TWO,
            1, lobberInfluenceGrid);

    p1Deck = new ArrayList<GameCard>(Arrays.asList(security1, bee1, sweeper1, crab1, mandragora1,
            queen1, trooper1, cavestalker1, lobber1, security1, bee1, sweeper1, crab1,
            mandragora1, queen1, trooper1, cavestalker1, lobber1));

    p2Deck = new ArrayList<GameCard>(Arrays.asList(security2, bee2, sweeper2, crab2, mandragora2,
            queen2, trooper2, cavestalker2, lobber2, security2, bee2, sweeper2, crab2, mandragora2,
            queen2, trooper2, cavestalker2, lobber2));

    deckConfig = new PawnsBoardDeckConfig();
  }


  @Test
  public void testInvalidNullConstructor() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    GamePlayer player1 = new HumanPlayer(model, 1);

    assertThrows(IllegalArgumentException.class, () -> new PawnsBoardPlayerController(model,
            player1, null));

    assertThrows(IllegalArgumentException.class, () -> new PawnsBoardPlayerController(null,
            player1, new MockPawnsBoardView(model, 1)));

    assertThrows(IllegalArgumentException.class, () -> new PawnsBoardPlayerController(model,
            null, new MockPawnsBoardView(model, 1)));
  }


  @Test
  public void testValidControllerConstructor() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardController controller1 = new PawnsBoardPlayerController(model, player1, view1);

    GamePlayer player2 = new HumanPlayer(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 1);
    PawnsBoardController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    assertDoesNotThrow(() -> controller1.playGame());
    assertDoesNotThrow(() -> controller2.playGame()); // works without exception
  }

  // integration test: testing single valid move for place
  // mock view: notified => prints board
  @Test
  public void testSingleValidMovePlaceHumanPlayer1() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(0);
    controller1.setSelectedCell(0, 0);
    controller1.placeCard();

    String newExpected =
            "2 R1__1 0\n"
                    + "0 2___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testSingleValidMovePlaceHumanPlayer2() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view2.toString());

    controller1.pass();
    controller2.setCardIdx(1);
    controller2.setSelectedCell(0, 4);
    controller2.placeCard();

    String newExpected =
            "0 1___B 1\n"
                    + "0 1___1 0\n"
                    + "0 1___2 0\n"
                    + "Player 2: It's your turn!";
    assertEquals(newExpected, view2.toString());
  }

  @Test
  public void testSingleValidMovePlaceMachinePlayer() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new MachinePlayer(model, new FillFirst(), 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view2.toString());

    player1.chooseMove();

    String newExpected =
            "2 R1__1 0\n"
                    + "0 2___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(newExpected, view1.toString());
  }

  // integration test: testing pass
  // mock view: notified => prints board
  @Test
  public void testSingleValidMovePassHumanPlayer1() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.pass();

    assertEquals(expected, view1.toString()); // no change
    assertEquals(player2.getPlayerID(), model.getCurrentPlayerID());
  }

  @Test
  public void testSingleValidMovePassHumanPlayer2() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view2.toString());

    controller1.pass();
    controller2.pass();

    assertEquals(player1.getPlayerID(), model.getCurrentPlayerID());
  }

  @Test
  public void testSingleValidMovePassMachinePlayer() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new MachinePlayer(model, new MockStrategy(new ArrayList<Move>()), 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    player1.chooseMove();

    assertEquals(expected, view1.toString()); // no change
  }

  // integration test: when model indicates move is invalid for placeCard
  // mock view: notified => print e.message with board
  @Test
  public void testModelInvalidCardIdxPlace() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(-2);
    controller1.setSelectedCell(0, 0);
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: Selected card index is out of bounds. Please play again.";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testModelInvalidRowPlaceInput() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(0);
    controller1.setSelectedCell(6, 0); // Invalid row
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: Selected row is out of bounds. Please play again.";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testModelInvalidColumnPlaceInput() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(0);
    controller1.setSelectedCell(0, 10); // Invalid column
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: Selected column is out of bounds. Please play again.";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testModelPlaceCannotAddCardNoPawnsInput() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(0);
    controller1.setSelectedCell(1, 1); // Cell with no pawns
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: You have no pawns on this cell. "
                    + "Cannot add card. Please play again.";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testModelPlaceCannotAddCardNoPlayerPawnsOwnershipInput() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);
    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(0);
    controller1.setSelectedCell(0, 4); // Blue pawns
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: You do not own these pawns. Cannot add card. Please play again.";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testModelPlaceCannotAddCardCannotCoverCostInput() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(2);
    controller1.setSelectedCell(0, 0);
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: You do not have enough pawns to cover the cost of this card. " +
                    "Please play again.";
    assertEquals(newExpected, view1.toString());
  }

  // integration test: game played to completion with valid and invalid moves (pass, place)
  // mock view: notified => prints board
  @Test
  public void testGamePlayHumanPlayers() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString()); // initial game state

    controller1.setCardIdx(0);
    controller1.setSelectedCell(0, 0);
    controller1.placeCard();
    String newExpected1 =
            "2 R1__1 0\n"
                    + "0 2___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 2: It's your turn!";
    assertEquals(newExpected1, view2.toString()); // player 2 updated

    controller2.setCardIdx(0);
    controller2.setSelectedCell(0, 4);
    controller2.placeCard();

    controller1.setCardIdx(0);
    controller1.setSelectedCell(-1, 0);
    controller1.placeCard();
    String newExpected3 =
            "2 R1_1B 2\n"
                    + "0 2___2 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: Selected row is out of bounds. Please play again.";
    assertEquals(newExpected3, view1.toString()); // player 1 invalid move

    controller1.setCardIdx(0);
    controller1.setSelectedCell(1, 0);
    controller1.placeCard();

    controller2.pass();
    String newExpectedPlayer1 =
            "2 R1_1B 2\n"
                    + "1 R___2 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: It's your turn!";
    assertEquals(newExpectedPlayer1, view1.toString()); // player 1 updated

    controller1.pass();
    String finalExpected =
            "2 R1_1B 2\n"
                    + "1 R___2 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: It's your turn!\n"
                    + "Game Over.\n"
                    + "Winner: Player 1\n"
                    + "Winning Score: 1";
    assertEquals(finalExpected, view1.toString()); // final
  }

  @Test
  public void testSuccessfulGamePlayHumanAndMachinePlayer() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);

    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new MachinePlayer(model, new MockStrategy(new ArrayList<Move>(
            Arrays.asList(new Move(1, 2, 4, false),
                    new Move(0, 0, 4, false)))), 2);

    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();
    String expectedHuman =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expectedHuman, view1.toString());
    String expectedMachine =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expectedMachine, view2.toString());

    controller1.setCardIdx(0);
    controller1.setSelectedCell(0, 0);
    controller1.placeCard();


    String newExpectedHuman =
            "2 R1__2 0\n"
                    + "0 2___1 0\n"
                    + "0 1___B 1\n"
                    + "Player 1: It's your turn!";
    assertEquals(newExpectedHuman, view1.toString()); // machine automatically played
    String newExpectedMachine =
            "2 R1__2 0\n"
                    + "0 2___1 0\n"
                    + "0 1___B 1\n";
    assertEquals(newExpectedMachine, view2.toString());

    controller1.pass(); // human player passed
    // machine played
    controller1.pass(); // human player passed
    // machine no more moves => passed

    String finalExpected =
            "2 R1_1B 2\n"
                    + "0 2___2 0\n"
                    + "0 1___B 1\n"
                    + "Player 1: It's your turn!\n"
                    + "Game Over.\n"
                    + "Winner: Player 2\n"
                    + "Winning Score: 1";
    assertEquals(finalExpected, view1.toString());
  }

  @Test
  public void testSuccessfulGamePlayMachinePlayers() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new MachinePlayer(model, new ControlBoard(), 1);
    GamePlayer player2 = new MachinePlayer(model, new FillFirst(), 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    player1.chooseMove();
    String expected =
            "8 RRRRB 2\n"
                    + "2 12R1B 1\n"
                    + "0 1111B 1\n\n"
                    + "Game Over.\n"
                    + "Winner: Player 1\n"
                    + "Winning Score: 10";
    assertEquals(expected, view1.toString());
  }

  // integration test: controller taking in too little inputs
  // mock view => notified, prints e.message with board
  @Test
  public void testIntegrationPlaceNoCardIdx() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setSelectedCell(0, 0);
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: Please select a card from hand first.";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testIntegrationNoCellSelected() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    // ---------- Initial board
    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.setCardIdx(0);
    controller1.placeCard();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: Please select a cell on the board first.";
    assertEquals(newExpected, view1.toString());
  }

  // integration test: it's your turn notification
  // mock view => notified, prints e.message with board
  @Test
  public void testPlayer1ItsYourTurn() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n";
    assertEquals(expected, view1.toString());

    controller1.itsYourTurn();

    String newExpected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 1: It's your turn!";
    assertEquals(newExpected, view1.toString());
  }

  @Test
  public void testPlayer2ItsYourTurn() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    GamePlayer player2 = new HumanPlayer(model, 2);
    PawnsBoardView view1 = new MockPawnsBoardView(model, 1);
    PawnsBoardView view2 = new MockPawnsBoardView(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);

    controller1.playGame();
    controller2.playGame();

    controller1.pass();

    controller2.itsYourTurn();

    String expected =
            "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "0 1___1 0\n"
                    + "Player 2: It's your turn!";
    assertEquals(expected, view2.toString());
  }

  // unit test: controller set card index, row, col for placeCard correctly
  @Test
  public void testControllerSetCardIdxCellCorrectly() {
    StringBuilder log1 = new StringBuilder();
    QueensBlood model = new MockModelPlace(log1, 5, 3, new Random(6));
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new HumanPlayer(model, 1);
    PawnsBoardView view = new PawnsBoardFrame(model, 1);

    PawnsBoardPlayerController controller = new PawnsBoardPlayerController(model, player1, view);
    controller.playGame();
    controller.setCardIdx(0);
    controller.setSelectedCell(1, 0);
    controller.placeCard();
    assertEquals("0 1 0", log1.toString());
  }

  // unit test: machine v. machine player taking turns correctly
  @Test
  public void testMachinePlayersTakeTurnsCorrectly() {
    StringBuilder log1 = new StringBuilder();
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    model.startGame(p1Deck, p2Deck, 5, false);
    GamePlayer player1 = new MockMachinePlayer(model, new MockStrategy(new ArrayList<Move>(
            Arrays.asList(new Move(0, 0, 0, false),
                    new Move(0, 1, 0, false)))), 1, log1);
    GamePlayer player2 = new MockMachinePlayer(model, new MockStrategy(new ArrayList<Move>(
            Arrays.asList(new Move(0, 0, 4, false),
                    new Move(0, 1, 4, false)))), 2, log1);
    PawnsBoardView view1 = new PawnsBoardFrame(model, 1);
    PawnsBoardView view2 = new PawnsBoardFrame(model, 2);

    PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
    PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);
    controller1.playGame();
    controller2.playGame();

    player1.chooseMove();

    assertEquals("Player 1 placed 0 0 0 Player 2 placed 0 0 4 Player 1 placed 0 1 0 "
            + "Player 2 placed 0 1 4 Player 1 passed Player 2 passed ", log1.toString());
  }
}
