OVERVIEW:
This codebase provides a basic implementation of a variation of the Queen’s Blood game, designed to
simulate a 2-player card game. The design assumes that the users are familiar with the basic rules
of the card game, as the implementation doesn’t include any explanation or tutorial. The game is
also limited to two players, each taking turns based on their available moves.

QUICK START:
To use this codebase:
1) Create a new PawnsBoardDeckConfig to load the deck configuration.
   EX: DeckConfiguration deckConfig = new PawnsBoardDeckConfig();

2) Create a PawnsBoardModel with a board width, height, random object, and the PawnsBoardDeckConfig object.
   EX: model = new PawnsBoardModel(5, 3, new Random(6), deckConfig);

3) Create a new deck of cards for each player by calling loadDeckConfig with a FileReader.
   String path = "docs" + File.separator + "gameDeck.config";
   File config = new File(path);
   List<GameCard> p1Deck = deckConfig.loadDeckConfig(new FileReader(config));
   List<GameCard> p2Deck = deckConfig.loadDeckConfig(new FileReader(config));

4) Call startGame with a player 1 deck, player 2 deck, starting hand size, and boolean indicating whether shuffle to be on.
   EX: model.startGame(p1Deck, p2Deck, 5, true);

5) Create two views using the PawnsBoardFrame object and set their location and visibility to be true:
   PawnsBoardFrame view1 = new PawnsBoardFrame(model, 1);
   PawnsBoardFrame view2 = new PawnsBoardFrame(model, 2);
   view2.setLocation(view1.getX() + view1.getWidth(), view1.getY());
   view1.setVisible(true);
   view2.setVisible(true);

6) Create two players using the GamePlayer object, either a HumanPlayer or MachinePlayer with a strategy:
   GamePlayer player1 = HumanPlayer(model, 1);
   GamePlayer player2 = MachinePlayer(model, new FillFirst(), 2);

7) Instantiate a new PawnsBoardPlayerController for each GamePlayer and call playGame on each controller:
   PawnsBoardPlayerController controller1 = new PawnsBoardPlayerController(model, player1, view1);
   PawnsBoardPlayerController controller2 = new PawnsBoardPlayerController(model, player2, view2);
   controller1.playGame();
   controller2.playGame();

8) Human Players: Once it's your turn, select the desired move, either place a card on the board or pass:
    - to place: select a cell and card on GUI and press "enter" key
    - to pass: press "space" bar

RUNNING THE GAME:
To run the main() method in PawnsBoardGame, 6 string arguments must be entered:
(1) Path to the file for Player 1 Red’s deck
(2) Path to the file for Player 2 Blue’s deck
(3) Player 1 type, either "human", "strategy1", "strategy 2", or "strategy 3" to represent a human player or machine player with a strategy
(4) Player 2 type, either "human", "strategy1", "strategy 2", or "strategy 3"
(5) Width of the game board
(6) Height of the game board

EX: for a human vs. machine strategy 1 game, run:
java -jar Assignment7.jar docs/gameDeck.config docs/gameDeck.config human strategy1 5 7

KEY COMPONENTS:
- PawnsBoardModel: Manages the board and keeps track of the state of the game
    - Has game play behaviors, including starting the game, placing a card, passing, performing the
      influence effect of placing a card, getting a cell at a specific board position, setting the
      next player, determining the current player, drawing the next card, getting the player's hand,
      determining if the game is over
    - Has scoring behaviors, including calculating the score of each player, determining the winner
      and winning score of the game
- PawnsBoardView: Displays and updates the GUI view of the game 
- PawnsBoardPlayerController: Responds to user interaction, telling the model what to do and GUI view to update
- GamePlayer: Actions available to all the players in the game (Human and Machine)
- Strategy: Represents different strategies a MachinePlayer can use, including fill first, max row score, controlling the board, or blocking an opponent

The driving components of this codebase include the PawnsBoardModel, which enforces the rules and
updates the game state; the PawnsBoardView, which updates the GUI at every game state; and the 
PawnsBoardPlayerController, which respond to user actions and initiate changes in the game's state.

The driven components of this codebase include the board, which updates in response to changes in
the game state, and the view, which visually reflects these updates without influencing gameplay.


KEY SUB-COMPONENTS:
- Cell: Represents a cell on the board; can be empty, filled with 1~3 pawns, or have a card
    - EmptyCell: Represents a (gray) cell with no pawns or cards on the board
    - Pawns: Represents a cell with pawns (either 1, 2, or 3) and a color (representing which player currently owns the pawns)
    - GameCard: Represents a game card used to play in the game with a cost, value, and influenceGrid,
      which is a list of Positions relative to the center card position (2,2)
- Player: Represents a model player in the game with a unique hand, deck, and color (red or blue)
- GamePlayer: Represents a type of player, either human or machine, that plays the game 
- PawnsBoardPanel: Represents the GUI view of the game board, including the cells and row score of each player
    - Updates the GUI in response to mouse click events of selecting and deselecting cells
- PlayersHandPanel: Represents the GUI view of the player's hand, which are made up of game cards
    - Updates the GUI in response to mouse click events of selecting and deselecting cards
    - Displays Player 1's hand (red cards) in one frame and Player 2's hand (blue cards) in another frame
    - GameCardPanel: Represents the GUI view of a single game card, including its name, cost, value, and influence grid

SOURCE ORGANIZATION:
The main model interface, QueensBlood, can be found: src/cs3500/pawnsboard/model/QueensBlood.java
The ReadOnlyPawnsBoardModel interface, can be found: src/cs3500/pawnsboard/model/ReadOnlyPawnsBoardModel.java
The main model class, PawnsBoardModel, can be found: src/cs3500/pawnsboard/model/PawnsBoardModel.java

The Cell interface can be found: src/cs3500/pawnsboard/model/Cell.java
The ReadOnlyCell interface can be found: src/cs3500/pawnsboard/model/ReadOnlyCell.java
The EmptyCell class can be found: src/cs3500/pawnsboard/model/EmptyCell.java
The Pawns class can be found: src/cs3500/pawnsboard/model/Pawns.java

The Card interface can be found: src/cs3500/pawnsboard/model/Cell.java
The ReadOnlyGameCard interface can be found: src/cs3500/pawnsboard/model/ReadOnlyGameCard.java
The GameCard class can be found: src/cs3500/pawnsboard/model/GameCard.java

The DeckConfiguration interface can be found: src/cs3500/pawnsboard/controller/DeckConfiguration.java
The PawnsBoardDeckConfig class can be found: src/cs3500/pawnsboard/controller/PawnsBoardDeckConfig.java

The Player class can be found: src/cs3500/pawnsboard/model/Player.java

The Position class can be found: src/cs3500/pawnsboard/model/Position.java

The textual view interface, QueensBloodTextualView can be found: src/cs3500/pawnsboard/view/QueensBloodTextualView.java
The textual view class PawnsBoardTextualView can be found: src/cs3500/pawnsboard/view/PawnsBoardTextualView.java

The PawnsBoardView can be found: src/cs3500/pawnsboard/view/PawnsBoardView.java
The ViewActions interface can be found: src/cs3500/pawnsboard/view/ViewActions.java
The PawnsBoardFrame class can be found: src/cs3500/pawnsboard/view/PawnsBoardFrame.java

The IntPawnsBoardPanel interface can be found: src/cs3500/pawnsboard/view/IntPawnsBoardPanel.java
The PawnsBoardPanel class can be found: src/cs3500/pawnsboard/view/PawnsBoardPanel.java

The IntPlayersHandPanel interface can be found: src/cs3500/pawnsboard/view/IntPlayersHandPanel.java
The PlayersHandPanel class can be found: src/cs3500/pawnsboard/view/PlayersHandPanel.java
The GameCardPanel class can be found: src/cs3500/pawnsboard/view/GameCardPanel.java

The GameOverFrame class can be found: src/cs3500/pawnsboard/view/GameOverFrame.java

The PawnsBoardController interface can be found: src/cs3500/pawnsboard/controller/PawnsBoardController.java
The PawnsBoardPlayerController class can be found: src/cs3500/pawnsboard/controller/PawnsBoardPlayerController.java

The Strategy interface can be found: src/cs3500/pawnsboard/strategy/Strategy.java
The FillFirst class can be found: src/cs3500/pawnsboard/strategy/FillFirst.java
The MaxRowScore class can be found: src/cs3500/pawnsboard/strategy/MaxRowScore.java
The ControlBoard class can be found: src/cs3500/pawnsboard/strategy/ControlBoard.java
The BlockOpponent class can be found: src/cs3500/pawnsboard/strategy/BlockOpponent.java
The Move class can be found: src/cs3500/pawnsboard/strategy/Move.java

The ModelActions interface can be found: src/cs3500/pawnsboard/model/ModelActions.java

The GamePlayer interface can be found: src/cs3500/pawnsboard/player/GamePlayer.java
The HumanPlayer class can be found: src/cs3500/pawnsboard/player/HumanPlayer.java
The MachinePlayer class can be found: src/cs3500/pawnsboard/player/MachinePlayer.java

The PawnsBoardBuilder class can be found: src/cs3500/pawnsboard/model/PawnsBoardBuilder.java

The PawnsBoard (textual view main method) can be found: src/cs3500/pawnsboard/PawnsBoard.java
The PawnsBoardGame (GUI view main method) can be found: src/cs3500/pawnsboard/PawnsBoardGame.java

CHANGES FROM ASSIGNMENT 5:
In Model:
- Refactored model interface by making original interface (QueensBlood) extend new model interface (ReadonlyPawnsBoardModel)
  - ReadOnlyPawnsBoardModel interface contains immutable observation methods of pawns board model
  - QueensBlood interface contains mutator methods of pawns board model
  
- Refactored cell interface by making original interface (Cell) extend new model interface (ReadOnlyCell)
  - ReadOnlyCell interface contains immutable observation methods of cell
  - Cell interface contains mutator methods of cell
  
- Added new interface ReadOnlyGameCard that represents the new immutable observation methods of game card:
  - isCellInfluencedAt: checks if a cell in the influence grid is influenceable based on the given row and col (necessary to implement strategy ControlBoard)
  - getName: returns the name of the game card (necessary to draw the GUI of the card)
  
- Added new interface Card that extends Cell and ReadOnlyGameCard
  - Represents the mutator methods of a game card 
  
- Added new behaviors to PawnsBoardModel (ReadOnlyPawnsBoardModel):
  - getBoard: returns a copy of the board
  - getOwnerOfCell: returns the Player that owns the cell
  - getCurrentPlayerID: returns the current player's ID (necessary to keep track of to display player's hand GUI view)
  - getPlayerTotalScore: returns the current score of the given player
  - getPlayerColor: returns the player color based on the given player ID
  - getPlayerByColor: returns the Player based on the given color
  
- Added new behaviors to Cell (ReadOnlyCell):
  - getCellColor: returns the color of the cell (necessary to display the board GUI view)

In Controller:
- Moved DeckConfiguration and PawnsBoardDeckConfig to this package from the model package since the controller should be reading the files

In Strategy: 
- Implemented all four strategies, FillFirst (1), MaxRowScore (2), ControlBoard (3), and BlockOpponent (4)
  - ControlBoard finds the cell and card that allows the current player to obtain the most net influenced cells:
    - Either adding a pawn to an empty cell or taking ownership of an opponent's pawns
  - BlockOpponent takes in a list of any combination of strategies and looks for the move that allows the current player to block the opponent's best move
- Strategy implementation can be found: src/cs3500/pawnsboard/strategy 
- Tests for strategies can be found: test/cs3500/pawnsboard/StrategyTest.java

CHANGES FROM ASSIGNMENT 6:
In View:
- Added new behavior to ViewActions:
  - isViewEnabled: returns whether the view should be playable (cells and cards clickable), necessary to distinguish machine vs. human player
- Added new behaviors to PawnsBoardView:
  - reset: resets the selected cells on the GUI view board
  - displayMessage: displays an error message in the GUI view game board as a message dialog
  - displayGameOverMessage: displays the game over message in the GUI view game board

In Model:
- Added a new interface ModelActions that notifies the controller whenever the model updates the game state:
  - itsYourTurn: notifies the player controller it's their turn and to choose a move 
  - refreshView: notifies the player controller to refresh their view 
  - processGameOver: notifies the player controller that the game is over


