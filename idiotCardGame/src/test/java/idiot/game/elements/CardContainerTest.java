package idiot.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardContainerTest {

    private CardContainer cardContainer;

    @BeforeEach
    void setUp() {
        cardContainer = new CardStack();
    }

    @Test
    void testGetMaxCardCount() {
        assertEquals(2, cardContainer.getMaxCardCount());
    }

    @Test
    void testGetCardCount() {
        assertEquals(0, cardContainer.getCardCount());
        cardContainer.addCard(new Card('H', 1));
        assertEquals(1, cardContainer.getCardCount());
    }

    @Test
    void testGetCard() {
        Card card1 = new Card('H', 1);
        Card card2 = new Card('S', 7);
        cardContainer.addCard(card1);
        cardContainer.addCard(card2);
        assertEquals(card1, cardContainer.getCard(0));
        assertEquals(card2, cardContainer.getCard(1));
        assertThrows(IllegalArgumentException.class, () -> cardContainer.getCard(-1));
        assertThrows(IllegalArgumentException.class, () -> cardContainer.getCard(2));
    }

    @Test
    void testGetTopCard() {
        Card card1 = new Card('H', 1);
        Card card2 = new Card('S', 7);
        cardContainer.addCard(card1);
        cardContainer.addCard(card2);
        assertEquals(card2, cardContainer.getTopCard());
        cardContainer.removeCard(card2);
        assertEquals(card1, cardContainer.getTopCard());
        cardContainer.removeCard(card1);
        assertThrows(IllegalStateException.class, () -> cardContainer.getTopCard());
    }

    @Test
    void testIsEmpty() {
        assertTrue(cardContainer.isEmpty());
        cardContainer.addCard(new Card('D', 5));
        assertFalse(cardContainer.isEmpty());
    }

    @Test
    void testMoveCard() {
        Card card = new Card('C', 13);
        CardContainer newContainer = new CardPile();
        cardContainer.addCard(card);
        cardContainer.moveCard(card, newContainer);
        assertEquals(0, cardContainer.getCardCount());
        assertEquals(1, newContainer.getCardCount());
        assertEquals(card, newContainer.getCard(0));
    }

    @Test
    void testAddCard() {
        Card card1 = new Card('D', 8);
        Card card2 = new Card('S', 2);
        cardContainer.addCard(card1);
        assertEquals(1, cardContainer.getCardCount());
        assertEquals(card1, cardContainer.getCard(0));
        cardContainer.addCard(card2);
        assertEquals(2, cardContainer.getCardCount());
        assertEquals(card2, cardContainer.getCard(1));
        assertThrows(IllegalStateException.class, () -> {
            cardContainer.addCard(new Card('H', 9));
        });
    }

    @Test
    void testRemoveCard() {
        Card card = new Card('H', 10);
        cardContainer.addCard(card);
        assertEquals(1, cardContainer.getCardCount());
        assertEquals(card, cardContainer.getCard(0));
        cardContainer.removeCard(card);
        assertEquals(0, cardContainer.getCardCount());
        assertThrows(IllegalStateException.class, () -> cardContainer.removeCard(card));
    }
}