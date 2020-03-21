package jamsunofx;

import controller.AgentUtils;
import controller.Game;
import controller.Round;
import controller.utils;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Card;
import model.CardColour;
import model.DiscardPile;
import model.DrawPile;
import model.Player;

public class JamsUNOFX extends Application {

    Game game = new Game();
    BorderPane root = new BorderPane();
    AgentCardBoxLeft agentCardBoxLeft;
    AgentCardBoxTop agentCardBoxTop;
    AgentCardBoxRight agentCardBoxRight;
    UserCardBox userCardBox;
    CardBoxCenter cardBoxCenter;

    @Override
    public void start(Stage primaryStage) {

        //Add players
        game.addPlayer("Martin", "agent");
        game.addPlayer("Juanita", "agent");
        game.addPlayer("Paola", "agent");
        game.addPlayer("Jorge", "user");

        int delearPosition = game.defineDealer();
        System.out.println("THE PLAYERS:");
        System.out.println(game.toString());
        System.out.println("the dealer position is : " + delearPosition);
        System.out.println("The dealer is: " + game.getDealer().getName());

        //TODO:esto es de pruba
//            delearPosition = 3;
//            game.setDealer(game.getPlayer(3));
//            game.getRound().setCurrentPlayer(3);
        agentCardBoxLeft = new AgentCardBoxLeft(game.getPlayer(0));
        agentCardBoxTop = new AgentCardBoxTop(game.getPlayer(1));
        agentCardBoxRight = new AgentCardBoxRight(game.getPlayer(2));
        userCardBox = new UserCardBox(game.getPlayer(3));

        switch (delearPosition) {
            case 0:
                agentCardBoxLeft.setDealerPane();
                break;
            case 1:
                agentCardBoxTop.setDealerPane();
                break;
            case 2:
                agentCardBoxRight.setDealerPane();
                break;
            case 3:
                userCardBox.setDealerPane();
                break;
        }

        game.setupRound();
        cardBoxCenter = new CardBoxCenter(game.getRound().getDrawPile(), game.getRound().getDiscardPile());
        cardBoxCenter.setGameLabel("Let's Start a new Round");
        agentCardBoxLeft.setCards();
        agentCardBoxTop.setCards();
        agentCardBoxRight.setCards();
        userCardBox.setCards();

        //TODO: estos son activaciones de prueba
//            userCardBox.activateHandler(game.getRound(),cardBoxCenter);
//            cardBoxCenter.activateHandler();
//        ViewUtils.playerPlays(game.getRound(), cardBoxCenter, userCardBox, agentCardBoxLeft, agentCardBoxTop, agentCardBoxRight);
        root.setBottom(userCardBox);
        root.setTop(agentCardBoxTop);
        root.setRight(agentCardBoxRight);
        root.setLeft(agentCardBoxLeft);
        root.setCenter(cardBoxCenter);

        startTask();
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("Uno Game");
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    public void startTask() {
        // Create a Runnable
        Runnable task = new Runnable() {
            @Override
            public void run() {
                runTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    public void runTask() {

        while (game.getRound().isContinuePlaying()) {
            boolean cardPlayed = false;
            try {
                cardPlayed = ViewUtils.playerPlays(game.getRound(), cardBoxCenter, userCardBox, agentCardBoxLeft, agentCardBoxTop, agentCardBoxRight);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cardPlayed) {
                if (game.getRound().getCurrentPlayer().wonGame()) {
                    game.getRound().checkFinalCard();
                    System.out.println(game.getRound().getCurrentPlayer().getName() + " won the round.");
                    game.getRound().getCurrentPlayer().setPoints(game.getRound().calculatePoints(game.getRound().getCurrentPlayer()));
                    game.getRound().setContinuePlaying(false);
                    game.getRound().clearPlayerCards();
                    game.setDealer(game.getRound().getCurrentPlayer()); //set the dealer for the next round
                } else {
                    ViewUtils.checkCard(game.getRound(), cardBoxCenter, userCardBox, agentCardBoxLeft, agentCardBoxTop, agentCardBoxRight);
                }
            } else {
                game.getRound().getPlayerList().nextNode();

            }

        }
    }

}
