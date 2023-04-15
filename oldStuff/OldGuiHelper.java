// package idiot.util;

// import java.io.IOException;
// import java.util.List;

// import idiot.game.elements.Card;
// import idiot.game.elements.CardContainer;
// import idiot.game.elements.CardDeck;
// import idiot.gui.CardPane;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Node;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Pane;
// import javafx.scene.layout.StackPane;


// public class OldGuiHelper {

//     /* selected card in the gui */

//     public static Card SELECTED_CARD;
    
//     /* updates given gui nodes based on the state of given game elements */

//     private static CardPane createCard(Card card, boolean isFaceDown, boolean isVisible) {

//         // get card resource
//         FXMLLoader fxmlLoader = new FXMLLoader(CardPane.class.getResource("/idiot/Card.fxml"));
        
//         // set controller
//         CardPane cardPane = new CardPane(card, isFaceDown);
//         fxmlLoader.setController(cardPane);
//         cardPane.setVisible(isVisible);
        
//         // load the cardPane with FXMLLoader
//         try {
//             cardPane = fxmlLoader.load();
//             return cardPane;
//         } catch (IOException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }

//     /* initialize gui deck with card panes added from the game deck*/

//     private static void initializeGuiDeck(CardDeck gameDeck, StackPane guiDeck) {
//         for (Card gameCard : gameDeck) {
            
//             // add card panes to gui deck
//             CardPane guiCard = new CardPane(gameCard, true);
//             guiDeck.getChildren().add(guiCard);

//             // only show top card
//             if (!gameCard.isTopCard())
//             guiCard.setVisible(false);
//         }
//     }

//     /* updates given gui nodes based on the state of given game elements */

//     public static void updateGui(CardContainer[] gameElements, List<Card> legalMoves, Node[] guiElements, boolean isReplay) {

//         for (int i = 0; i < gameElements.length; i++) {
            
//             CardContainer container = gameElements[i];
//             int cardAmount = container.getCardCount();
//             Node node = guiElements[i];

//             // remove old content from nodes
//             if (node instanceof StackPane)
//             ((StackPane) node).getChildren().clear();

//             else if (node instanceof HBox)
//             ((HBox) node).getChildren().clear();

//             // adding every card from the container to the corresponding node

//             for (int j = 0; j < cardAmount; j++) {

//                 /*  
//                     we now have the following parameters:

//                     1: the card's container (i) 
//                     2: the position within the container (j)

//                     The mapping is defined as follows:

//                     (i = 0) -> deck 
//                     (i = 1) -> mainPile 
//                     (i = 2) -> discardPile 
//                     (i = 3) -> player1Hand 
//                     (i = 4) -> player2Hand 
//                     (i > 4) -> stack

//                     (j = 0) -> bottom

//                 */

//                 // not all cards are rendered
//                 if ((i == 0 || i == 1 || i == 2) && (j < cardAmount - 2))
//                 continue;

//                 // card in container (i) at position (j)
//                 Card card = container.getCard(j);

//                 // cards can either be invisible (face down) or not (face up)
//                 boolean isFaceDown = true;
                
//                 // cases where the card should be face down
//                 if (i == 0 || i == 2 || (i == 4 && !isReplay) || (i > 4 && j == 0))
//                 isFaceDown = false;

//                 // creating card pane
//                 //Pane cardPane = GuiHelper.createCard(card, isFaceDown);
//                 CardPane cardPane = new CardPane(card, isFaceDown);

//                 // adding event handler for clicks
//                 if (legalMoves != null && legalMoves.contains(card)) {
//                     cardPane.setOnMouseClicked(event -> {
//                         System.out.println("Valid: " + card.toString());
//                         SELECTED_CARD = card;
//                     });
//                 }

//                 // casting to stackpane and adding cards
//                 if (node instanceof StackPane) {
//                     ((StackPane) node).getChildren().add(cardPane);
//                 }

//                 // casting to hbox and adding cards
//                 else if (node instanceof HBox) {
//                     ((HBox) node).getChildren().add(cardPane);
//                 }
//             }

//             // center the cards in hand only the scrollpane does not overfill
//             if (node instanceof HBox) {
//                 ScrollPane scrollPane = (ScrollPane) ((HBox) node).getParent().getParent().getParent();
//                 if (((HBox) node).getChildren().size() > 7) {
//                     scrollPane.setFitToWidth(false);
//                 } else {
//                     scrollPane.setFitToWidth(true);
//                 }
//             }
//         }
//     }
// }
