package jamsunofx;

import controller.Round;
import controller.utils;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Card;
import model.CardColour;
import model.DiscardPile;
import model.DrawPile;

/**
 *
 * @author j_a_m
 */
public class CardBoxCenter extends VBox {

    ObservableList<CardImageView> discardPileImages = FXCollections.observableArrayList();
    ObservableList<CardImageView> drawPileImages = FXCollections.observableArrayList();
    HBox pileBox = new HBox();
    HBox labelBox = new HBox();
    HBox wildLabelBox = new HBox();
    Label gameLabel = new Label();
    Label wildColourLabel = new Label();
    StackPane drawPilePane = new StackPane();
    StackPane discardPilePane = new StackPane();
    DrawPileEventHandler drawPileEventHandler;

    public CardBoxCenter(DrawPile drawPile, DiscardPile discardPile) {

        Image image;
        CardImageView cardImageView;

        for (Card card : drawPile.getDrawPile()) {

            image = new Image("images/UNO-Back_1.png");
            cardImageView = new CardImageView(image, card);

            cardImageView.setFitWidth(68);
            cardImageView.setFitHeight(98);
            drawPileImages.add(cardImageView);
        }

        drawPilePane.getChildren().addAll(drawPileImages);

        for (Card card : discardPile.getDiscardPile()) {
            if (card == discardPile.getDiscardPile().lastElement()) {
                image = new Image("images/" + card.getColour().toImage() + "-" + card.getValue().toImage() + ".png");
                cardImageView = new CardImageView(image, card);
            } else {
                image = new Image("images/UNO-Back_1.png");
                cardImageView = new CardImageView(image, card);
            }

            cardImageView.setFitWidth(68);
            cardImageView.setFitHeight(98);
            discardPileImages.add(cardImageView);
        }

        discardPilePane.getChildren().addAll(discardPileImages);

        labelBox.getChildren().add(gameLabel);
        labelBox.setAlignment(Pos.CENTER);
        pileBox.getChildren().addAll(drawPilePane, discardPilePane);
        pileBox.setAlignment(Pos.CENTER);
        wildLabelBox.getChildren().add(wildColourLabel);
        wildLabelBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(labelBox, pileBox, wildLabelBox);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(230, 0, 230, 0));
        this.setStyle("-fx-background-repeat: no-repeat;"
                + "-fx-background-position: center;"
                + "-fx-background-color: white;"
                + "-fx-background-size: contain;"
                + "-fx-background-image: url('images/clockwise.png');"
                + "-fx-bacground-size: 200px 200px;");

        this.setMaxSize(400, 400);
    }

    public void reverseDirectionImage(Round round) {
        if (round.getPlayerList().isClockWise()) {
            this.setStyle("-fx-background-repeat: no-repeat;"
                    + "-fx-background-position: center;"
                    + "-fx-background-color: white;"
                    + "-fx-background-size: contain;"
                    + "-fx-background-image: url('images/clockwise.png');"
                    + "-fx-bacground-size: 200px 200px;");
        } else {
            this.setStyle("-fx-background-repeat: no-repeat;"
                    + "-fx-background-position: center;"
                    + "-fx-background-color: white;"
                    + "-fx-background-size: contain;"
                    + "-fx-background-image: url('images/counterclockwise.png');"
                    + "-fx-bacground-size: 200px 200px;");
        }
    }

    public void setGameLabel(String text) {
        gameLabel.setText(text);
    }

    public void clearGameLabel() {
        gameLabel.setVisible(false);
    }

    public void setWildColourLabel(String text) {
        wildColourLabel.setVisible(true);
        wildColourLabel.setText("Wild Colour is: " + text);
    }

    public void clearWildColourLabel() {
        wildColourLabel.setVisible(false);
    }

    public void addCardToDiscardPile(CardImageView selectedCard) {
        this.discardPilePane.getChildren().clear();
        this.pileBox.getChildren().clear();
        this.discardPileImages.add(selectedCard);
        this.discardPilePane.getChildren().addAll(discardPileImages);
        this.pileBox.getChildren().addAll(drawPilePane, discardPilePane);
    }

    public void drawPileActivateHandler(DrawPileEventHandler drawPileEventHandler) {

        drawPilePane.addEventHandler(MouseEvent.MOUSE_CLICKED, drawPileEventHandler);
        System.out.println("Drawpile eventhandler activated");
    }
    
    public void drawPileRemoveEventHandler(DrawPileEventHandler drawPileEventHandler){
        drawPilePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, drawPileEventHandler);
    }
}
