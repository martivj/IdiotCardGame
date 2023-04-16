package idiot.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import idiot.game.elements.*;

public class PlayerTest {

    private Game game;
    private CardDeck deck;
    private CardPile mainPile;
    private CardPile discardPile;
    private CardHand hand;
    private CardStack stack1;
    private CardStack stack2;
    private CardStack stack3;
    private CardStack stack4;
    private Player player;

    @BeforeEach
    void setUp() {
        deck = new CardDeck();
        deck.randomShuffle();

        mainPile = new CardPile();

        discardPile = new CardPile();

        hand = new CardHand();

        stack1 = new CardStack();
        stack2 = new CardStack();
        stack3 = new CardStack();
        stack4 = new CardStack();

        player = new Player(game, deck, mainPile, discardPile, hand, stack1, stack2, stack3, stack4);
    }

    @Test
    void testBeginTurnWithLegalMoves() {
        deck.deal(hand, 1);
        mainPile.addCard(new Card('D', 5));
        mainPile.addCard(new Card('C', 2));
        mainPile.addCard(new Card('H', 4));
        mainPile.addCard(new Card('S', 3));

        player.beginTurn();

        assertFalse(player.getLegalMoves().isEmpty());
    }

    @Test
    void testBeginTurnWithNoLegalMovesAndEmptyMainPile() {
        player.beginTurn();

        assertTrue(mainPile.isEmpty());
    }

    @Test
    void testDrawFromDeckWithNonEmptyDeckAndNotFullHand() {
        deck.deal(hand, 1);

        player.drawFromDeck();

        assertFalse(player.getLegalMoves().isEmpty());
    }

    @Test
    void testDrawFromDeckAndEndTurn() {
        mainPile.flushCards(hand);

        player.drawFromDeck();

        assertTrue(mainPile.isEmpty());
    }

    @Test
    void testDrawFromPileWithEmptyMainPile() {
        player.drawFromPile();

        assertTrue(mainPile.isEmpty());
    }

}