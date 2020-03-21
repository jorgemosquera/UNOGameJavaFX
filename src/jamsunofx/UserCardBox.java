/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamsunofx;

import controller.Round;
import controller.utils;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Card;
import model.CardColour;
import model.Player;

/**
 *
 * @author j_a_m
 */
public class UserCardBox extends VBox {

    ObservableList<CardImageView> userCards = FXCollections.observableArrayList();
    HBox cardsHBox = new HBox();
    HBox imageBox = new HBox();
    Player player;
    StackPane dealerPane;
    OptionsPane optionsBox;
    ColourBox colourBox;

    public UserCardBox(Player player) {

        this.player = player;
        Image image = new Image("images/avatar/avatar1.png");
        ImageView imageView = new ImageView(image);
        imageBox.getChildren().add(imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        this.getChildren().addAll(imageBox, cardsHBox);
        this.setAlignment(Pos.CENTER);

    }

    public void setDealerPane() {
        dealerPane = new DealerImagePane();
        Platform.runLater(() -> {
            imageBox.getChildren().add(dealerPane);
        });
    }

    public void removeDealerPane() {
        Platform.runLater(() -> {
            imageBox.getChildren().remove(dealerPane);
        });
    }

    public void askPlayerOptions(Round round, UserCardBox userCardBox, CardBoxCenter cardBoxCenter, Card card, BooleanValue runAction, BooleanValue cardPlayed, BooleanValue wildOption) {
        optionsBox = new OptionsPane(round, userCardBox, cardBoxCenter, card, runAction, cardPlayed, wildOption);
        Platform.runLater(() -> {
            imageBox.getChildren().add(optionsBox);
        });
        System.out.println("Finished adding option box");

    }

    public void removePlayerOptions() {
        Platform.runLater(() -> {
            imageBox.getChildren().remove(optionsBox);
        });
    }

    public void askWildColour(Round round, UserCardBox userCardBox, CardBoxCenter cardBoxCenter, Card card, BooleanValue runAction, BooleanValue cardPlayed, BooleanValue wildOption) {

        colourBox = new ColourBox(round, userCardBox, cardBoxCenter, card, runAction, cardPlayed, wildOption);
        Platform.runLater(() -> {
            imageBox.getChildren().add(colourBox);
        });

        System.out.println("I finished adding Colour Box");
    }

    public void removeAskWildColours() {
        Platform.runLater(() -> {
            imageBox.getChildren().remove(colourBox);
        });
    }

    public void setCards() {
        for (Card card : this.player.getPlayerCards()) {
            Image image = new Image("images/" + card.getColour().toImage() + "-" + card.getValue().toImage() + ".png");
            CardImageView cardImageView = new CardImageView(image, card);
            cardImageView.setFitWidth(68);
            cardImageView.setFitHeight(98);
            this.userCards.add(cardImageView);
        }

        cardsHBox.getChildren().clear();
        cardsHBox.getChildren().addAll(userCards);
        cardsHBox.setAlignment(Pos.CENTER);
    }

    public void removeCard(Card card) {
        for (CardImageView cardImageView : userCards) {
            if (cardImageView.getCard().equals(card)) {
                userCards.remove(cardImageView);
                Platform.runLater(() -> {
                    cardsHBox.getChildren().clear();
                    cardsHBox.getChildren().addAll(userCards);
                });
                break;
            }
        }
    }

    public void addCard(Card card) {
        Image image = new Image("images/" + card.getColour().toImage() + "-" + card.getValue().toImage() + ".png");
        CardImageView cardImageView = new CardImageView(image, card);
        cardImageView.setFitWidth(68);
        cardImageView.setFitHeight(98);
        this.userCards.add(cardImageView);
        Platform.runLater(() -> {
            cardsHBox.getChildren().clear();
            cardsHBox.getChildren().addAll(userCards);
        });
    }

    public void activateHandler(UserEventHandler userEventHandler) {

        for (ImageView imageView : userCards) {
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, userEventHandler);
        }
        
    }
    
    public void removeHandler(UserEventHandler userEventHandler){
        for (ImageView imageView : userCards) {
            imageView.removeEventHandler(MouseEvent.MOUSE_CLICKED, userEventHandler);
        }
    }

}

class OptionsPane extends HBox {

    //UserOption True for Play Card, False to pass
    public OptionsPane(Round round, UserCardBox userCardBox, CardBoxCenter cardBoxCenter, Card card, BooleanValue runAction, BooleanValue cardPlayed, BooleanValue wildOption) {
        Button playCardButton = new Button("Play Card");
        Button passButton = new Button("Pass");
        this.getChildren().addAll(playCardButton, passButton);
        this.setAlignment(Pos.CENTER);

        passButton.setOnAction(e -> {

            runAction.setValue(false);
            cardPlayed.setValue(false);

        });

        playCardButton.setOnAction(e -> {
            if (card.getColour() == CardColour.WILD || card.getColour() == CardColour.WILD_FOUR) {
                userCardBox.askWildColour(round, userCardBox, cardBoxCenter, card, runAction, cardPlayed, wildOption);
            } else {
                round.getCurrentPlayer().play(card, round.getDiscardPile());
                Platform.runLater(() -> {
                    userCardBox.removeCard(card);
                    cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                    cardBoxCenter.clearWildColourLabel();
                });
                runAction.setValue(false);
                cardPlayed.setValue(true);
//                System.out.println("I want to play");
            }

        });
    }
}

class ColourBox extends HBox {

    public ColourBox(Round round, UserCardBox userCardBox, CardBoxCenter cardBoxCenter, Card card, BooleanValue runAction, BooleanValue cardPlayed, BooleanValue wildOption) {
        Button blueButton = new Button("Blue");
        Button greenButton = new Button("Green");
        Button redButton = new Button("Red");
        Button yellowButton = new Button("Yellow");

        Platform.runLater(() -> {
            this.getChildren().addAll(blueButton, greenButton, redButton, yellowButton);
            this.setAlignment(Pos.CENTER);
        });

        blueButton.setOnAction(e -> {
            round.setWildColour(CardColour.BLUE);
            runAction.setValue(false);
            cardPlayed.setValue(true);
            wildOption.setValue(true);

            round.getCurrentPlayer().play(card, round.getDiscardPile());
            Platform.runLater(() -> {
                userCardBox.removeCard(card);
                cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                cardBoxCenter.setWildColourLabel("Blue");
            });
//            System.out.println("I want to play");

        });

        greenButton.setOnAction(e -> {
            round.setWildColour(CardColour.GREEN);
            runAction.setValue(false);
            cardPlayed.setValue(true);
            wildOption.setValue(true);
            userCardBox.player.play(card, round.getDiscardPile());
//            System.out.println("player played card");
            Platform.runLater(() -> {
                userCardBox.removeCard(card);
                cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                cardBoxCenter.setWildColourLabel("Green");
            });
//            System.out.println("I want to play");
        });

        redButton.setOnAction(e -> {
            round.setWildColour(CardColour.RED);
            runAction.setValue(false);
            cardPlayed.setValue(true);
            wildOption.setValue(true);
            round.getCurrentPlayer().play(card, round.getDiscardPile());
            Platform.runLater(() -> {
                userCardBox.removeCard(card);
                cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                cardBoxCenter.setWildColourLabel("Red");
            });
//            System.out.println("I want to play");
        });

        yellowButton.setOnAction(e -> {
            round.setWildColour(CardColour.YELLOW);
            runAction.setValue(false);
            cardPlayed.setValue(true);
            wildOption.setValue(true);
            round.getCurrentPlayer().play(card, round.getDiscardPile());
            Platform.runLater(() -> {
                userCardBox.removeCard(card);
                cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                cardBoxCenter.setWildColourLabel("Yellow");
            });
//            System.out.println("I want to play");
        });
    }
}

class CardColourWrapper {

    CardColour cardColour;

    public void setValue(CardColour cardColour) {
        this.cardColour = cardColour;
    }

    public CardColour getValue() {
        return this.cardColour;
    }
}
