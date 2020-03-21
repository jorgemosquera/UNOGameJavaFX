package jamsunofx;

import controller.Round;
import controller.utils;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Card;

public class DrawPileEventHandler implements EventHandler<MouseEvent> {

    Round round;
    UserCardBox userCardBox;
    CardBoxCenter cardBoxCenter;
    BooleanValue runAction;
    BooleanValue cardPlayed;
    BooleanValue userOption;
    BooleanValue wildOption;

    public DrawPileEventHandler(Round round, UserCardBox userCardBox, CardBoxCenter cardBoxCenter, BooleanValue runAction, BooleanValue cardPlayed, BooleanValue userOption, BooleanValue wildOption) {
        this.round = round;
        this.userCardBox = userCardBox;
        this.cardBoxCenter = cardBoxCenter;
        this.runAction = runAction;
        this.cardPlayed = cardPlayed;
        this.userOption = userOption;
        this.wildOption = wildOption;
    }

    @Override
    public void handle(MouseEvent event) {
        Card card = round.getDrawPile().drawCard(round.getDiscardPile());
        userCardBox.player.receiveCard(card);
        Platform.runLater(() -> {
            userCardBox.addCard(card);
        });

        if (utils.cardCanPlay(card, round.showDrawCard(), round.getWildColour())) {
//                    System.out.println("The new card can be played");
            userOption.setValue(true);
            Platform.runLater(() -> {
                userCardBox.askPlayerOptions(round, userCardBox, cardBoxCenter, card, runAction, cardPlayed, wildOption);
            });
            cardBoxCenter.drawPilePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            System.out.println("Drawpile eventHandler remove");

        } else {
            userOption.setValue(false);
            runAction.setValue(false);
            cardPlayed.setValue(false);
//                    System.out.println("I pass");
            cardBoxCenter.drawPilePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            System.out.println("Drawpile eventHandler remove");
        }
    }

}
