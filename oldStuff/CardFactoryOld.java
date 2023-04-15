package idiot.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CardFactoryOld {

    public Node createCard() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/idiot/Card.fxml"));
        CardController cardController = new CardController();
        fxmlLoader.setController(cardController);
        Pane card;
        try {
            card = fxmlLoader.load();
            cardController.setCardFront('S', 13);
            return card;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        

        // Parent root = fxmlLoader.load();
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);

        // // Set the suit symbol and face value for a card
        // cardController.setCard("H", "1");

        // try {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/idiot/Card.fxml"));
        //     Pane card = loader.load();
        //     return card;
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     return null;
        // }
    }
}
