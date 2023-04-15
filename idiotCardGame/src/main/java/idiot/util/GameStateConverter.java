package idiot.util;

import idiot.game.elements.Card;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.game.elements.CardHand;
import idiot.game.elements.CardPile;
import idiot.game.elements.CardStack;

public class GameStateConverter {

    /* takes in an array of game elements and creates a new game state  */

    public static String createGameState(CardContainer[] gameElements) {

        // building string on the format "xx-xx-xx|xx-xx-xx-xx|xx-xx-..."
        StringBuilder stateBuilder = new StringBuilder();
        for (CardContainer container : gameElements) {
            for (Card card : container) {
                stateBuilder.append(card.toString()).append("-");
            }
            if (container.isEmpty()) {
                stateBuilder.append("-"); // add empty delimiter if container is empty
            }
            else {
                stateBuilder.deleteCharAt(stateBuilder.length() - 1); // remove the last delimiter
            }
            stateBuilder.append("|");
        }
        stateBuilder.deleteCharAt(stateBuilder.length() - 1); // remove the last delimiter
        return stateBuilder.toString();
    }
    
    /* takes in a game state and creates new game elements */

    public static CardContainer[] createGameElements(String state) {

        CardDeck deck = new CardDeck(); // the main deck to draw cards from
        deck.clear(); // initalizes with a full deck, we remove these

        CardPile mainPile = new CardPile(); // the main pile to play cards to
        CardPile discardPile = new CardPile(); // pile of cards discarded from the game

        CardHand player1hand = new CardHand(); // the hand of player 1
        CardHand player2hand = new CardHand(); // the hand of player 2

        CardStack player1LeftStack = new CardStack(); // the left-most card stack for player 1
        CardStack player1MiddleStack = new CardStack(); // the middle card stack for player 1
        CardStack player1RightStack = new CardStack(); // the right-most card stack for player 1

        CardStack player2LeftStack = new CardStack(); // the left-most card stack for player 2
        CardStack player2MiddleStack = new CardStack(); // the middle card stack for player 2
        CardStack player2RightStack = new CardStack(); // the right-most card stack for player 2

        CardContainer[] gameElements = new CardContainer[11];

        gameElements[0] = deck; 
        gameElements[1] = mainPile; 
        gameElements[2] = discardPile; 
        gameElements[3] = player1hand; 
        gameElements[4] = player2hand; 
        gameElements[5] = player1LeftStack; 
        gameElements[6] = player1MiddleStack; 
        gameElements[7] = player1RightStack;
        gameElements[8] = player2LeftStack; 
        gameElements[9] = player2MiddleStack; 
        gameElements[10] = player2RightStack;

        // splitting into states of each container
        String[] containerStrings = state.split("\\|");

        // loop through all containers
        for (int i = 0; i < containerStrings.length; i++) {

            // assign a container to add cards into
            CardContainer container = gameElements[i];
            
            // add cards based on the string within each container
            for (String cardString : containerStrings[i].split("-")) {
                char suit = cardString.charAt(0);
                int value = Integer.parseInt(cardString.substring(1));
                Card card = new Card(suit, value);
                container.addCard(card);
                
            }
        }

        return gameElements;
        
    }
}
