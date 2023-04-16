package idiot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idiot.game.elements.Card;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.gui.CardPane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


public class GuiHelper {

    /* selected card in the gui */

    public static Card SELECTED_CARD;
    
    /* initialize gui deck with card panes added from the game deck */

    public static void initializeGuiDeck(CardDeck gameDeck, StackPane guiDeck) {

        // going through the cards of the given game deck
        for (Card gameCard : gameDeck) {
            
            // create a new card pane using the corresponding game card
            CardPane guiCard = new CardPane(gameCard);
            guiCard.setVisible(false);
            guiCard.setFaceDown(true);
            
            if (guiCard != null)
            guiDeck.getChildren().add(guiCard);

            // show only the top card
            if (gameCard.isTopCard())
            guiCard.setVisible(true);

        }
    }

    /* updates given gui nodes (stackpanes and hboxes) based on the state of given game elements */

    public static void updateGui(CardContainer[] gameElements, Node[] guiElements, List<Card> legalMoves, boolean isReplay) {

        // linking card panes in the scene to the string representation of the card it is based on
        Map<String, CardPane> cardLink = new HashMap<>();
        GuiHelper.getAllCardPanes(guiElements).stream().forEach(x -> cardLink.put(x.getCard().toString(), x));

        int parentCount = gameElements.length;

        // looping through the parents
        for (int i = 0; i < parentCount; i++) {

            // assigning variable names
            CardContainer container = gameElements[i];
            int childCount = container.getCardCount();
            Node node = guiElements[i];
            
            // removing old content
            if (node instanceof StackPane)
            ((StackPane) node).getChildren().clear();
            else if (node instanceof HBox)
            ((HBox) node).getChildren().clear();

            // looping through the children
            for (int j = 0; j < childCount; j++) {

                /*  
                    we now have the following parameters:

                    1: the parent index (i) 
                    2: the child index (j)

                    The mapping is defined as follows:

                    (i = 0) -> deck 
                    (i = 1) -> mainPile 
                    (i = 2) -> discardPile 
                    (i = 3) -> player1Hand 
                    (i = 4) -> player2Hand 
                    (i > 4) -> stack

                    (j = 0) -> bottom
                    (j = childCount -1) -> top

                */

                // getting card and corresponding card pane at node (i) in position (j)
                Card card = container.getCard(j);
                CardPane cardPane = cardLink.get(card.toString());

                // set on-click event handlers
                if ((legalMoves != null && legalMoves.contains(card)) || (card.getOwner() instanceof CardDeck && card.isTopCard())) {
                    cardPane.setOnMouseClicked(event -> {
                        SELECTED_CARD = card;
                    });
                } else {
                    cardPane.setOnMouseClicked(event -> {
                    });
                }

                // card panes can be shown face down, face up or not at all
                boolean isFaceDown = false;
                boolean isVisible = true;

                // cases where the card pane should be face down
                if (i == 0 || i == 2 || (i == 4 && !isReplay) || (i > 4 && j == 0))
                isFaceDown = true;

                // cases where the card pane should not be visible
                if ((i == 0 || i == 1 || i == 2) && (j < childCount - 1))
                isVisible = false;

                // setting appropriate properties
                cardPane.setFaceDown(isFaceDown);
                cardPane.setVisible(isVisible);

                // adding card pane to node
                if (node instanceof StackPane)
                ((StackPane) node).getChildren().add(cardPane);
                else if (node instanceof HBox)
                ((HBox) node).getChildren().add(cardPane);

            }

            // center the cards in hand only the scrollpane does not overfill
            if (node instanceof HBox) {
                ScrollPane scrollPane = (ScrollPane) ((HBox) node).getParent().getParent().getParent();
                if (((HBox) node).getChildren().size() > 7) {
                    scrollPane.setFitToWidth(false);
                } else {
                    scrollPane.setFitToWidth(true);
                }
            }
        }
    }

    /* helping method: find all card panes within the gui elements */

    private static List<CardPane> getAllCardPanes(Node[] guiElements) {

        List<CardPane> allCardPanes = new ArrayList<>();

        for (Node node : guiElements) {
            if (node instanceof StackPane) {
                allCardPanes.addAll(((StackPane) node).getChildren().stream().map(x -> (CardPane) x).toList());
            } else if (node instanceof HBox) {
                allCardPanes.addAll(((HBox) node).getChildren().stream().map(x -> (CardPane) x).toList());
            }
        }
        
        return allCardPanes;
    }
}
