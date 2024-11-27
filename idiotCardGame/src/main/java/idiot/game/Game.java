package idiot.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import idiot.game.elements.Card;
import idiot.game.elements.CardComparator;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.game.elements.CardHand;
import idiot.game.elements.CardPile;
import idiot.game.elements.CardStack;
import idiot.gui.GameController;
import idiot.util.FileHelper;
import idiot.util.GameStateConverter;
import idiot.util.GuiHelper;

public class Game {

    /* initial boolean states */

    private boolean gameOver = false;
    private boolean isSaved = false;

    /* fields that record and keep track of game states */

    private String currentState;
    private List<String> recordedGameStates = new ArrayList<>();

    /* game elements */

    private CardDeck deck = new CardDeck(); // the main deck to draw cards from

    private CardPile mainPile = new CardPile(); // the main pile to play cards to
    private CardPile discardPile = new CardPile(); // pile of cards discarded from the game

    private CardHand player1hand = new CardHand(); // the hand of player 1
    private CardHand player2hand = new CardHand(); // the hand of player 2

    private CardStack player1Stack1 = new CardStack(); // the left-most card stack for player 1
    private CardStack player1Stack2 = new CardStack(); // the middle card stack for player 1
    private CardStack player1Stack3 = new CardStack(); // the right-most card stack for player 1

    private CardStack player2Stack1 = new CardStack(); // the left-most card stack for player 2
    private CardStack player2Stack2 = new CardStack(); // the middle card stack for player 2
    private CardStack player2Stack3 = new CardStack(); // the right-most card stack for player 2

    private CardContainer[] gameElements = new CardContainer[11];

    /* players for the game */

    private Player activePlayer;
    private Player player1 = new Player(this, deck, mainPile, discardPile, player1hand, player1Stack1, player1Stack2,
            player1Stack3);
    private AIPlayer player2 = new AIPlayer(this, deck, mainPile, discardPile, player2hand, player2Stack1,
            player2Stack2, player2Stack3);

    /* connection to gui controller */

    private GameController controller;

    /* constructor */

    public Game(GameController controller) {

        // set controller and load initial set of cards to gui deck
        this.controller = controller;
        this.initializeGuiDeck();

        // grouping game elements
        this.gameElements[0] = this.deck;
        this.gameElements[1] = this.mainPile;
        this.gameElements[2] = this.discardPile;
        this.gameElements[3] = this.player1hand;
        this.gameElements[4] = this.player2hand;
        this.gameElements[5] = this.player1Stack1;
        this.gameElements[6] = this.player1Stack2;
        this.gameElements[7] = this.player1Stack3;
        this.gameElements[8] = this.player2Stack1;
        this.gameElements[9] = this.player2Stack2;
        this.gameElements[10] = this.player2Stack3;

    }

    /* shuffle the deck, distribute cards and start the game */

    public void start() {

        // save initial state
        this.recordGameState();

        // shuffle the deck
        this.deck.randomShuffle();

        // deal into the stacks
        this.deck.deal(player1Stack1, 2);
        this.deck.deal(player1Stack2, 2);
        this.deck.deal(player1Stack3, 2);
        this.deck.deal(player2Stack1, 2);
        this.deck.deal(player2Stack2, 2);
        this.deck.deal(player2Stack3, 2);

        // deal into the hands
        this.deck.deal(player1hand, 3);
        this.deck.deal(player2hand, 3);

        // sort hands
        this.player1hand.sortHand();
        this.player2hand.sortHand();

        // save state after distributing cards
        this.recordGameState();

        // start the first turn
        this.startFirstTurn();
    }

    /* find out who starts and begin turn */

    private void startFirstTurn() {

        // get comparator
        CardComparator comparator = new CardComparator();

        // lowest cards in terms of face value from each player
        Card player1Card = this.player1hand.getLowestCard();
        Card player2Card = this.player2hand.getLowestCard();

        // player 1 start requirement
        if (comparator.compare(player1Card, player2Card) < 0)
            this.activePlayer = player1;

        // player 2 starts otherwise
        else
            this.activePlayer = player2;

        // start turn
        this.controller.handlePlayerSwitch();
        this.activePlayer.beginTurn();

    }

    /* "interface" for the AI player to reach the controller */

    public void playAIMove(Card card) {
        this.recordGameState();
        GuiHelper.SELECTED_CARD = card;
        controller.handlePlayAction(null);
    }

    /* game over */

    public void setGameOver(Player winningPlayer) {
        this.gameOver = true;
        this.controller.handleGameOver();
    }

    public boolean isOver() {
        return this.gameOver;
    }

    /* manage the players' turns */

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public void switchPlayer() {

        // switch active player
        if (this.activePlayer instanceof AIPlayer)
            this.activePlayer = this.player1;
        else
            this.activePlayer = this.player2;

        // update gui label
        this.controller.handlePlayerSwitch();

        // begin new turn
        this.activePlayer.beginTurn();

    }

    /* initialize gui */

    public void initializeGuiDeck() {
        this.controller.initializeGuiDeck(this.deck);
    }

    /* register game state and update gui */

    public void updateState() {

        // record game state
        this.recordGameState();

        // update gui
        this.controller.handleUpdateState(this.gameElements);
    }

    /* game state changed -> record it */

    private void recordGameState() {

        // getting the state in string form "|xx-xx-xx|xx-xx-xx|xx-..."
        String state = GameStateConverter.createGameState(this.gameElements);

        // state unchanged -> do nothing
        if (state.equals(this.currentState))
            return;

        // otherwise save new state
        this.currentState = state;
        this.recordedGameStates.add(this.currentState);
    }

    /* write all recorded game states to file with given path */

    public void saveReplay(String path) {

        // List<String> states = this.recordedGameStates.stream().distinct().toList();

        try {
            FileHelper.writeLines(path, this.recordedGameStates);
            this.isSaved = true;
            System.out.println("Replay file saved to " + path);
        } catch (IOException e) {
            System.out.println("Could not save the replay file: " + e);
        }
    }

    public boolean isSaved() {
        return this.isSaved;
    }
}
