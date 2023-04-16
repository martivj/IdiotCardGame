package idiot.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import idiot.game.elements.Card;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.game.elements.CardHand;
import idiot.game.elements.CardPile;
import idiot.game.elements.CardStack;

class GameStateConverterTest {

    @Test
    void testCreateGameState() {
        CardContainer[] gameElements = new CardContainer[4];
        gameElements[0] = new CardDeck();
        ((CardDeck) gameElements[0]).clear();
        gameElements[1] = new CardPile();
        gameElements[2] = new CardStack();
        gameElements[3] = new CardHand();

        Card card1 = new Card('H', 1);
        Card card2 = new Card('D', 2);
        Card card3 = new Card('D', 1);
        Card card4 = new Card('C', 3);
        Card card5 = new Card('S', 4);

        gameElements[0].addCard(card1);
        gameElements[1].addCard(card2);
        gameElements[1].addCard(card3);
        gameElements[1].addCard(card4);
        gameElements[3].addCard(card5);

        String gameState = GameStateConverter.createGameState(gameElements);

        assertEquals("H1|D2-D1-C3|-|S4", gameState);
    }

    @Test
    void testCreateGameElements() {
        String gameState = "H1|-|D2-C3|-";
        CardContainer[] gameElements = GameStateConverter.createGameElements(gameState);

        assertTrue(gameElements[0] instanceof CardDeck);
        assertTrue(gameElements[1] instanceof CardPile);
        assertTrue(gameElements[2] instanceof CardPile);
        assertTrue(gameElements[3] instanceof CardHand);

        assertTrue(gameElements[1].isEmpty());
        assertTrue(gameElements[3].isEmpty());

        assertEquals(1, gameElements[0].getCard(0).getFace());
        assertEquals(2, gameElements[2].getCard(0).getFace());
        assertEquals(3, gameElements[2].getCard(1).getFace());
    }
}

