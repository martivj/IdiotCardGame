package idiot.gui;

import java.io.IOException;

import idiot.game.elements.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CardPaneOld extends Pane {

    @FXML
    private ImageView suitSymbol1, suitSymbol2, suitSymbol3, cardBack;

    @FXML
    private Label faceValue1, faceValue2;

    private Card card;

    public CardPaneOld(Card card) {
        this.card = card;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/idiot/Card.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        initialize();
    }

    @FXML
    void initialize() {
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/idiot/CardOld.fxml"));
        // fxmlLoader.setRoot(this);
        // fxmlLoader.setController(this);
        // try {
        //     fxmlLoader.load();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        setCardFront();
        setFaceDown(true);
        System.out.println(this.suitSymbol1);
        System.out.println(this.suitSymbol2);
        System.out.println(this.suitSymbol3);
    }

    private void setCardFront() {

        char suit = this.card.getSuit();
        int face = this.card.getFace();

        String imgurl = "/idiot/images/" + suit + ".png";

        this.suitSymbol1.setImage(new Image(getClass().getResourceAsStream(imgurl)));
        this.suitSymbol2.setImage(new Image(getClass().getResourceAsStream(imgurl)));
        this.suitSymbol3.setImage(new Image(getClass().getResourceAsStream(imgurl)));

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
                if (face < 1 || face > 13) 
                throw new IllegalArgumentException("Face value cannot be less than 1 or larger than 13.");
                faceString = Integer.toString(face);
                break;
        }

        this.faceValue1.setText(faceString);
        this.faceValue2.setText(faceString);
    }

    public void setFaceDown(boolean isFaceDown) {
        this.cardBack.setVisible(isFaceDown);
    }

    public Card getCard() {
        return this.card;
    }

}
