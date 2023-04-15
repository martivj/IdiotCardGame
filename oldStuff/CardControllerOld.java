package idiot.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CardControllerOld extends Pane {

    public CardControllerOld() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/idiot/Card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}