package idiot.util;

import java.io.IOException;

import idiot.game.elements.Card;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class CardFactoryNewerOld {
    
    public static Pane createCard(Card card, boolean isVisible) {

        // get card resource
        FXMLLoader fxmlLoader = new FXMLLoader(CardFactoryNewerOld.class.getResource("/idiot/Card.fxml"));
        
        // set controller
        CardController cardController = new CardController(card, isVisible);
        fxmlLoader.setController(cardController);
        
        // return the custom cardPane root node
        Pane cardPane;
        try {
            cardPane = fxmlLoader.load();
            return cardPane;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
