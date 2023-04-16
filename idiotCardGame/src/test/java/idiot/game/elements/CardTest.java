package idiot.game.elements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {

    private Card card;
    private CardContainer container;

    @BeforeEach
    void setUp() {
        card = new Card('S', 7);
        container = new CardStack();
    }

    @Test
    void testConstructorWithValidInput() {
        assertEquals('S', card.getSuit());
        assertEquals(7, card.getFace());
    }

    @Test
    void testConstructorWithInvalidSuit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card('X', 7);
        });
    }

    @Test
    void testConstructorWithInvalidFace() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card('S', 14);
        });
    }

    @Test
    void testGetOwner() {
        assertNull(card.getOwner());
    }

    @Test
    void testSetOwner() {
        card.setOwner(container);
        assertEquals(container, card.getOwner());
    }

    @Test
    void testSetOwnerWithNull() {
        card.setOwner(container);
        card.setOwner(null);
        assertNull(card.getOwner());
    }

    @Test
    void testIsTopCard() {
        container.addCard(card);
        assertTrue(card.isTopCard());
    }

    @Test
    void testIsTopCardWhenNotTopCard() {
        Card anotherCard = new Card('H', 10);
        container.addCard(card);
        container.addCard(anotherCard);
        assertFalse(card.isTopCard());
    }

    @Test
    void testToString() {
        assertEquals("S7", card.toString());
    }

}
