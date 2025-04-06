package cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.player.MachinePlayer;
import cs3500.pawnsboard.strategy.FillFirst;
import cs3500.pawnsboard.strategy.Move;
import cs3500.pawnsboard.strategy.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Represents tests for the Players (Machine and Human).
 */
public class PlayerTest {
  private QueensBlood game1;
  private ArrayList<GameCard> p1Deck;
  private ArrayList<GameCard> p2Deck;

  @Before
  public void setup() {
    Position leftSecurity = new Position(0, -1); // (2,1)
    Position rightSecurity = new Position(0, 1); // (2,3)
    Position topSecurity = new Position(-1, 0); // (1,2)
    Position bottomSecurity = new Position(1, 0); // (3,2)
    ArrayList<Position> securityInfluenceGrid = new ArrayList<Position>(
            Arrays.asList(topSecurity, leftSecurity, rightSecurity, bottomSecurity));

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
    GameCard sweeper1 = new GameCard("Sweeper", GameCard.Cost.TWO, 2, sweeperInfluenceGrid);
    GameCard crab1 = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    GameCard queen1 = new GameCard("Queen", GameCard.Cost.ONE, 1, queenInfluenceGrid);
    GameCard mandragora1 = new GameCard("Mandragora", GameCard.Cost.ONE, 2,
            mandragoraInfluenceGrid);
    GameCard trooper1 = new GameCard("Trooper", GameCard.Cost.TWO,
            3, trooperInfluenceGrid);
    GameCard cavestalker1 = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid);
    GameCard lobber1 = new GameCard("Lobber", GameCard.Cost.TWO, 1, lobberInfluenceGrid);

    GameCard security2 = new GameCard("Security", GameCard.Cost.ONE, 2,
            securityInfluenceGrid);
    GameCard bee2 = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid);
    GameCard sweeper2 = new GameCard("Sweeper", GameCard.Cost.TWO,
            2, sweeperInfluenceGrid);
    GameCard crab2 = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    GameCard queen2 = new GameCard("Queen", GameCard.Cost.ONE, 1, queenInfluenceGrid);
    GameCard mandragora2 = new GameCard("Mandragora", GameCard.Cost.ONE, 2,
            mandragoraInfluenceGrid);
    GameCard trooper2 = new GameCard("Trooper", GameCard.Cost.TWO,
            3, trooperInfluenceGrid);
    GameCard cavestalker2 = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid);
    GameCard lobber2 = new GameCard("Lobber", GameCard.Cost.TWO,
            1, lobberInfluenceGrid);

    p1Deck = new ArrayList<GameCard>(Arrays.asList(security1, bee1, sweeper1, crab1,
            mandragora1, queen1, trooper1, cavestalker1, lobber1, security1, bee1, sweeper1,
            crab1, mandragora1, queen1, trooper1, cavestalker1, lobber1));

    p2Deck = new ArrayList<GameCard>(Arrays.asList(security2, bee2, sweeper2, crab2,
            mandragora2, queen2, trooper2, cavestalker2, lobber2, security2, bee2, sweeper2,
            crab2, mandragora2, queen2, trooper2, cavestalker2, lobber2));

    DeckConfiguration deckConfig = new PawnsBoardDeckConfig();
  }

  @Test
  public void testInvalidNullConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new HumanPlayer(null, 1));

    assertThrows(IllegalArgumentException.class, () -> new MachinePlayer(null, new FillFirst(), 2));

    QueensBlood model = new PawnsBoardModel(
            5, 3, new Random(6), new PawnsBoardDeckConfig());

    assertThrows(IllegalArgumentException.class, () -> new MachinePlayer(model, null, 2));
  }


  @Test
  public void testHumanPlayerIsHumanPlayer() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    GamePlayer player = new HumanPlayer(model, 1);

    assertTrue(player.isHumanPlayer());
  }

  @Test
  public void testHumanPlayerGetPlayerID() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    GamePlayer player = new HumanPlayer(model, 1);

    assertEquals(1, player.getPlayerID());
    assertEquals("Player 1", player.toString());
  }

  @Test
  public void testMachinePlayerIsHumanPlayer() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    GamePlayer player = new MachinePlayer(model, new FillFirst(), 1);

    assertFalse(player.isHumanPlayer());
  }

  @Test
  public void testMachinePlayerGetPlayerID() {
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    GamePlayer player = new MachinePlayer(model, new FillFirst(), 2);

    assertEquals(2, player.getPlayerID());
    assertEquals("Player 2", player.toString());
  }

  @Test
  public void testMachinePlayerPasses() {
    StringBuilder log = new StringBuilder();
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    model.startGame(p1Deck, p2Deck, 5, false);
    MockViewActions mockObserver = new MockViewActions();
    Strategy strategy = new MockStrategy(new ArrayList<Move>());
    GamePlayer player = new MockMachinePlayer(model, strategy, 1, log);

    player.subscribe(mockObserver);

    player.chooseMove();

    assertTrue(mockObserver.passCalled);
    assertEquals("Player 1 passed ", log.toString());
  }

  @Test
  public void testMachinePlayerPlaceCard() {
    StringBuilder log = new StringBuilder();
    QueensBlood model = new PawnsBoardModel(5, 3, new Random(6),
            new PawnsBoardDeckConfig());
    model.startGame(p1Deck, p2Deck, 5, false);
    MockViewActions mockObserver = new MockViewActions();
    Strategy strategy = new MockStrategy(new ArrayList<Move>(new ArrayList<Move>(Arrays.asList(
            new Move(0, 0, 0, false)))));
    GamePlayer player = new MockMachinePlayer(model, strategy, 1, log);

    player.subscribe(mockObserver);

    player.chooseMove();

    assertTrue(mockObserver.placeCardCalled);
    assertEquals("Player 1 placed 0 0 0 ", log.toString());
  }
}
