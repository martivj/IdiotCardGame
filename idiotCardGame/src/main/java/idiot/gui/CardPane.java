package idiot.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

import idiot.game.elements.Card;

public class CardPane extends Pane {

    /* state attributes */

    @FXML private ImageView suitSymbol1, suitSymbol2, suitSymbol3, cardBack;
    
    private Card card;

    /* constructor */

    public CardPane(Card card) {
        this.card = card;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/idiot/Card.fxml"));
        fxmlLoader.setController(this);

        try {
            Pane pane = fxmlLoader.load();
            this.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setCardFront();
    }

    /* getters */

    public Card getCard() {
        return this.card;
    }

     /* setters */

    public void setFaceDown(boolean isFaceDown) {
        this.cardBack.setVisible(isFaceDown);
        this.suitSymbol1.setVisible(!isFaceDown);
        this.suitSymbol2.setVisible(!isFaceDown);
        this.suitSymbol3.setVisible(!isFaceDown);
    }

    public void setCardFront() {
        char suit = this.card.getSuit();
        int face = this.card.getFace();

        String imgurl = "/idiot/images/" + suit + ".png";

        suitSymbol1.setImage(new Image(getClass().getResourceAsStream(imgurl)));
        suitSymbol2.setImage(new Image(getClass().getResourceAsStream(imgurl)));
        suitSymbol3.setImage(new Image(getClass().getResourceAsStream(imgurl)));

        String faceString;

        switch (face) {
            case 1:
                faceString = "A";
                break;
            case 11:
                faceString = "J";
                break;
            case 12:
                faceString = "Q";
                break;
            case 13:
                faceString = "K";
                break;
            default:
                faceString = Integer.toString(face);
                break;
        }

        ((javafx.scene.control.Label) lookup("#faceValue1")).setText(faceString);
        ((javafx.scene.control.Label) lookup("#faceValue2")).setText(faceString);
    }
}
