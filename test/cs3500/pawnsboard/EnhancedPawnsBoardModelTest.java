package cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.controller.PawnsBoardPlayerController;
import cs3500.pawnsboard.model.DevaluePosition;
import cs3500.pawnsboard.model.EnhancedPawnsBoardModel;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.InfluencePosition;
import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.model.UpgradePosition;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.view.EnhancedTextualView;
import cs3500.pawnsboard.view.PawnsBoardView;
import cs3500.pawnsboard.view.QueensBloodTextualView;

import static org.junit.Assert.assertEquals;

public class EnhancedPawnsBoardModelTest {
  private Position leftSecurity;
  private Position rightSecurity;
  private Position topSecurity;
  private Position bottom2Security;
  private Position bottom1Security;
  private Position bottom3Security;
  private ArrayList<Position> securityInfluenceGrid;
  private ArrayList<Position> securityUpgradeGrid;
  private GameCard security;
  private GameCard bee;
  private GameCard sweeper;
  private GameCard crab;
  private GameCard queen;
  private GameCard mandragora1;
  private GameCard mandragora2;
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
    leftSecurity = new InfluencePosition(0, -1); // (2,1)
    rightSecurity = new InfluencePosition(0, 1); // (2,3)
    topSecurity = new InfluencePosition(-1, 0); // (1,2)
    bottom2Security = new InfluencePosition(1, 0); // (3,2)
    bottom1Security = new UpgradePosition(1, -1); // (3,1)
    bottom3Security = new UpgradePosition(1, 1); // (3,3)
    securityInfluenceGrid = new ArrayList<Position>(Arrays.asList(topSecurity, leftSecurity,
            rightSecurity, bottom2Security));
    securityUpgradeGrid = new ArrayList<Position>(Arrays.asList(bottom1Security, bottom3Security));

    Position topMandragora = new InfluencePosition(-1, 0); // (1,2)
    Position right1Mandragora = new InfluencePosition(0, 1); // (2,3)
    Position right2Mandragora = new InfluencePosition(0, 2); // (2,4)
    Position left1Mandragora = new UpgradePosition(0, -2);
    Position left2Mandragora = new UpgradePosition(0, -1);
    Position bottomMandragora = new InfluencePosition(2, 0);
    ArrayList<Position> mandragoraInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topMandragora, right1Mandragora, right2Mandragora));
    ArrayList<Position> mandragoraDevalueGrid = new ArrayList<Position>(
            Arrays.asList(bottomMandragora));
    ArrayList<Position> mandragoraUpgradeGrid = new ArrayList<Position>(
            Arrays.asList(left1Mandragora, left2Mandragora));

    Position topBee = new InfluencePosition(-2, 0);
    Position bottomBee = new InfluencePosition(2, 0);
    Position rightBee = new DevaluePosition(0, 1);
    Position rightTopBee = new DevaluePosition(-1, 1);
    Position rightBottomBee = new DevaluePosition(1, 1);
    ArrayList<Position> beeInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topBee, bottomBee));
    ArrayList<Position> beeDevalueGrid = new ArrayList<Position>(Arrays.asList(rightTopBee,
            rightBee, rightBottomBee));

    Position top1Sweeper = new InfluencePosition(-1, -1);
    Position top2Sweeper = new InfluencePosition(-1, 0);
    Position bottom1Sweeper = new InfluencePosition(1, -1);
    Position bottom2Sweeper = new InfluencePosition(1, 0);
    Position left1Sweeper = new UpgradePosition(0, 2);
    Position left2Sweeper = new UpgradePosition(0, 1);
    ArrayList<Position> sweeperInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            top1Sweeper, top2Sweeper, bottom1Sweeper, bottom2Sweeper));
    ArrayList<Position> sweeperUpgradeGrid = new ArrayList<Position>(Arrays.asList(left1Sweeper,
            left2Sweeper));

    Position leftCrab = new InfluencePosition(0, -1); // (2,1)
    Position rightCrab = new InfluencePosition(0, 1); // (2,3)
    Position topCrab = new InfluencePosition(-1, 0); // (1,2)
    Position left0Crab = new DevaluePosition(0, -2);
    Position right0Crab = new DevaluePosition(0, 2);
    Position bottomCrab = new UpgradePosition(1, 0);
    ArrayList<Position> crabInfluenceGrid = new ArrayList<Position>(
            Arrays.asList(topCrab, leftCrab, rightCrab));
    ArrayList<Position> crabDevalueGrid = new ArrayList<Position>(Arrays.asList(left0Crab,
            right0Crab));
    ArrayList<Position> crabUpgradeGrid = new ArrayList<Position>(Arrays.asList(bottomCrab));

    Position topQueen = new InfluencePosition(-1, 0);
    Position leftQueen = new UpgradePosition(-2, -1);
    Position rightQueen = new DevaluePosition(-2, 1);
    Position right0Queen = new UpgradePosition(0, 1);
    ArrayList<Position> queenInfluenceGrid = new ArrayList<Position>(Arrays.asList(topQueen));
    ArrayList<Position> queenUpgradeGrid = new ArrayList<Position>(Arrays.asList(leftQueen,
            right0Queen));
    ArrayList<Position> queenDevalueGrid = new ArrayList<Position>(Arrays.asList(rightQueen));

    Position top1Trooper = new InfluencePosition(-2, 0);
    Position top2Trooper = new InfluencePosition(-1, 1);
    Position rightTrooper = new InfluencePosition(0, 1);
    Position bottom1Trooper = new InfluencePosition(1, 0);
    Position bottom2Trooper = new InfluencePosition(2, 0);
    ArrayList<Position> trooperInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            top1Trooper, top2Trooper, rightTrooper, bottom1Trooper, bottom2Trooper));

    Position topCaveStalker = new InfluencePosition(-2, 0);
    Position right1CaveStalker = new InfluencePosition(-1, 1);
    Position right2CaveStalker = new InfluencePosition(0, 1);
    Position right3CaveStalker = new InfluencePosition(1, 1);
    Position bottomCaveStalker = new InfluencePosition(2, 0);
    Position leftCaveStalker = new UpgradePosition(0, -1);
    Position top0CaveStalker = new UpgradePosition(-1, 0);
    Position bottom0Cavestalker = new UpgradePosition(1, 0);
    ArrayList<Position> cavestalkerInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topCaveStalker, right1CaveStalker, right2CaveStalker,
            right3CaveStalker, bottomCaveStalker));
    ArrayList<Position> cavestalkerUpgradeGrid = new ArrayList<Position>(Arrays.asList(
            top0CaveStalker, leftCaveStalker, bottom0Cavestalker));

    Position rightLobber = new InfluencePosition(0, 2);
    ArrayList<Position> lobberInfluenceGrid = new ArrayList<Position>(Arrays.asList(rightLobber));

    security = new GameCard("Security", GameCard.Cost.ONE, 1,
            securityInfluenceGrid, securityUpgradeGrid, new ArrayList<Position>());
    bee = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid,
            new ArrayList<Position>(), beeDevalueGrid);
    sweeper = new GameCard("Sweeper", GameCard.Cost.TWO, 2, sweeperInfluenceGrid,
            sweeperUpgradeGrid, new ArrayList<Position>());
    crab = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid,
            crabUpgradeGrid, crabDevalueGrid);
    queen = new GameCard("Queen", GameCard.Cost.ONE, 1, queenInfluenceGrid,
            queenUpgradeGrid, queenDevalueGrid);
    mandragora1 = new GameCard("Mandragora", GameCard.Cost.ONE, 1,
            mandragoraInfluenceGrid, mandragoraUpgradeGrid, mandragoraDevalueGrid);
    mandragora2 = new GameCard("Mandragora", GameCard.Cost.ONE, 1,
            mandragoraInfluenceGrid, mandragoraUpgradeGrid, mandragoraDevalueGrid);
    trooper = new GameCard("Trooper", GameCard.Cost.TWO, 3, trooperInfluenceGrid);
    cavestalker = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid, cavestalkerUpgradeGrid, new ArrayList<Position>());
    lobber = new GameCard("Lobber", GameCard.Cost.TWO, 1, lobberInfluenceGrid);

    p1Deck = new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper, crab, mandragora1, queen,
            trooper, cavestalker, lobber, security, bee, sweeper, crab, mandragora1, queen, trooper,
            cavestalker, lobber));

    p2Deck = new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper, crab, mandragora2, queen,
            trooper, cavestalker, lobber, security, bee, sweeper, crab, mandragora2, queen,
            trooper, cavestalker, lobber));

    deckConfig = new PawnsBoardDeckConfig();

    game1 = new EnhancedPawnsBoardModel(5, 3, new Random(6), deckConfig);
    player1 = new Player(Color.RED, p1Deck, 5, new Random(6), true);
    player2 = new Player(Color.BLUE, p2Deck, 5, new Random(6), true);

    PawnsBoardView view1 = new MockPawnsBoardView(game1, 1);
    GamePlayer humanPlayer1 = new HumanPlayer(game1, 1);
    observer1 = new PawnsBoardPlayerController(game1, humanPlayer1, view1);
    PawnsBoardView view2 = new MockPawnsBoardView(game1, 2);
    GamePlayer humanPlayer2 = new HumanPlayer(game1, 2);
    observer2 = new PawnsBoardPlayerController(game1, humanPlayer2, view2);
  }

  // test effect of enhancing and devaluing empty and pawns cells
  @Test
  public void testTextualViewUpgradeDevalueEmptyPawns() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    QueensBloodTextualView view = new EnhancedTextualView(game1);
    String expected =
            "0 1(+0) _(+0) _(+0) _(+0) 1(+0) 0\n"
                    + "0 1(+0) _(+0) _(+0) _(+0) 1(+0) 0\n"
                    + "0 1(+0) _(+0) _(+0) _(+0) 1(+0) 0\n";

    assertEquals(expected, view.toString()); // default (+0) on all cells

    game1.placeCardInPosition(3, 1, 0); // player 1 placed crab
    String newP1Expected =
            "0 2(+0) _(+0) _(+0) _(+0) 1(+0) 0\n"
            + "1 R(+0) 1(+0) _(-1) _(+0) 1(+0) 0\n" // devalued empty cell
            + "0 1(+1) _(+0) _(+0) _(+0) 1(+0) 0\n"; // upgraded pawns
    assertEquals(newP1Expected, view.toString());

    game1.pass();
    game1.placeCardInPosition(0, 1, 1); // player 1 placed security
    String newP1Expected1 =
            "0 2(+0) 1(+0) _(+0) _(+0) 1(+0) 0\n"
            + "2 R(+0) R(+0) 1(-1) _(+0) 1(+0) 0\n" // added pawns to devalued cell
            + "0 1(+2) 1(+0) _(+1) _(+0) 1(+0) 0\n"; // upgraded pawns + empty cell
    assertEquals(newP1Expected1, view.toString());
  }

  // test effect of placing a game card on a cell with a future value and score
  // (1) => card value with -1 => removed and replaced with pawn with cost of card + influence still in effect
  // (2) => future value is added to score once card is placed
  @Test
  public void testTextualViewPlaceCardScoreEffect() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    QueensBloodTextualView view = new EnhancedTextualView(game1);

    game1.placeCardInPosition(3, 1, 0); // player 1 placed crab
    game1.pass();
    game1.placeCardInPosition(0, 1, 1); // player 1 placed security
    game1.pass();
    game1.placeCardInPosition(0, 2, 1); // player 1 placed bee
    String expected =
            "0 2(+0) 2(+0) _(+0) _(+0) 1(+0) 0\n"
                    + "2 R(+0) R(+0) 1(-2) _(+0) 1(+0) 0\n" // negative future value
                    + "1 1(+2) R(+0) _(+0) _(+0) 1(+0) 0\n";
    assertEquals(expected, view.toString());

    game1.pass();
    game1.placeCardInPosition(2, 1, 2); // player 1 placed queen
    String newExpected =
            "0 2(+0) 2(+0) 1(+0) _(+0) 1(+0) 0\n" // influenced cell still in effect
                    // card removed replaced with pawns with cost of card (1)
                    + "2 R(+0) R(+0) 1(+0) _(+1) 1(+0) 0\n" // influenced cell still in effect
                    + "1 1(+2) R(+0) _(+0) _(+0) 1(+0) 0\n";
    assertEquals(newExpected, view.toString());

    game1.pass();
    game1.placeCardInPosition(1, 2, 0); // player 1 placed mandragora
    String newExpected1 =
            "0 2(+0) 2(+0) 1(+0) _(+0) 1(+0) 0\n"
                    + "2 R(+0) R(+0) 1(+0) _(+1) 1(+0) 0\n"
                    // row 1 score = 1 (card value) + 2 (future value) = 3 + 1 = 4
                    + "4 R(+2) R(+0) 1(+0) _(+0) 1(+0) 0\n";
    assertEquals(newExpected1, view.toString());
  }

  // (1) test upgrade/devalue of empty cell has no effect on owner of cell
  // (2) => once claimed (influence cell) => enhances/devalues player who claimed
  @Test
  public void testTextualViewUpgradeDevalueOpponentCells() {
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    QueensBloodTextualView view = new EnhancedTextualView(game1);
    game1.placeCardInPosition(3, 1, 0); // player 1 placed crab
    String expected =
            "0 2(+0) _(+0) _(+0) _(+0) 1(+0) 0\n"
                    + "1 R(+0) 1(+0) _(-1) _(+0) 1(+0) 0\n" // devalued cell (1,2): (-1)
                    + "0 1(+1) _(+0) _(+0) _(+0) 1(+0) 0\n";
    assertEquals(expected, view.toString());

    game1.placeCardInPosition(4, 1, 4); // player 2 placed mandragora
    String newExpected =
            "0 2(+0) _(+0) _(+0) _(+0) 2(+0) 0\n"
                    + "1 R(+0) 1(+0) _(+0) _(+1) B(+0) 1\n" // upgraded cell (1,2): (-1 + 1 = 0)
                    + "0 1(+1) _(+0) _(+0) _(+0) 1(+0) 0\n";
    assertEquals(newExpected, view.toString());

    game1.placeCardInPosition(3, 1, 1); // player 1 placed mandragora
    String newExpected1 =
            "0 2(+0) 1(+0) _(+0) _(+0) 2(+0) 0\n"
                    + "2 R(+0) R(+0) 1(+0) 1(+1) B(+0) 1\n" // cell (1,2) pawns owned by red
                    + "0 1(+1) _(+0) _(+0) _(+0) 1(+0) 0\n";
    assertEquals(newExpected1, view.toString());

    game1.pass();
    game1.placeCardInPosition(0, 1, 3); // player 1 placed security
    String newExpected2 =
            "0 2(+0) 1(+0) _(+0) 1(+0) 2(+0) 0\n"
                    + "4 R(+0) R(+0) 2(+0) R(+1) B(+0) 1\n" // cell (1,2) card owned by red with enhanced value
                    + "0 1(+1) _(+0) _(+1) 1(+0) 1(+1) 0\n";
    assertEquals(newExpected2, view.toString());
  }
}
