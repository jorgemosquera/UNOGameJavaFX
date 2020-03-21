/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamsunofx;

import controller.AgentUtils;
import controller.Round;
import controller.utils;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Card;
import model.CardColour;
import model.CardValue;
import model.DiscardPile;
import model.DrawPile;
import model.Player;

/**
 *
 * @author j_a_m
 */
public class ViewUtils {

    public static void setViewDealer(AgentCardBoxLeft cardBox) {
        Image image = new Image("images/dealer.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        ObservableList list = cardBox.getChildren();

    }

    public static void checkCard(Round round, CardBoxCenter cardBoxCenter, UserCardBox userCardBox, AgentCardBoxLeft agentCardBoxLeft, AgentCardBoxTop agentCardBoxTop, AgentCardBoxRight agentCardBoxRight) {

        if (round.showDrawCard().getValue() == CardValue.SKIP) {
            round.getPlayerList().skip();
        } else if (round.showDrawCard().getValue() == CardValue.REVERSE) {
            round.getPlayerList().reverse();
            Platform.runLater(() -> {
                cardBoxCenter.reverseDirectionImage(round);
            });
            round.getPlayerList().nextNode();

        } else if (round.showDrawCard().getValue() == CardValue.DRAW_TWO) {
            round.getPlayerList().nextNode();
            Player player = round.getCurrentPlayer();
            for (int i = 0; i < 2; i++) {
                Card card = round.getDrawPile().drawCard(round.getDiscardPile());
                player.receiveCard(card);
                if (player.equals(agentCardBoxLeft.player)) {
                    Platform.runLater(() -> {
                        agentCardBoxLeft.addCard(card);
                    });
                } else if (player.equals(agentCardBoxTop.player)) {
                    Platform.runLater(() -> {
                        agentCardBoxTop.addCard(card);
                    });
                } else if (player.equals(agentCardBoxRight.player)) {
                    Platform.runLater(() -> {
                        agentCardBoxRight.addCard(card);
                    });
                } else if (player.equals(userCardBox.player)) {
                    Platform.runLater(() -> {
                        userCardBox.addCard(card);
                    });
                }

            }
            round.getPlayerList().nextNode();
        } else if (round.showDrawCard().getColour() == CardColour.WILD_FOUR) {
            round.getPlayerList().nextNode();
            Player player = round.getCurrentPlayer();
            for (int i = 0; i < 4; i++) {
                Card card = round.getDrawPile().drawCard(round.getDiscardPile());
                player.receiveCard(card);
                if (player.equals(agentCardBoxLeft.player)) {
                    Platform.runLater(() -> {
                        agentCardBoxLeft.addCard(card);
                    });
                } else if (player.equals(agentCardBoxTop.player)) {
                    Platform.runLater(() -> {
                        agentCardBoxTop.addCard(card);
                    });
                } else if (player.equals(agentCardBoxRight.player)) {
                    Platform.runLater(() -> {
                        agentCardBoxRight.addCard(card);
                    });
                } else if (player.equals(userCardBox.player)) {
                    Platform.runLater(() -> {
                        userCardBox.addCard(card);
                    });
                }
            }
            round.getPlayerList().nextNode();
        } else {
            round.getPlayerList().nextNode();
        }
    }

    public static boolean playerPlays(Round round, CardBoxCenter cardBoxCenter, UserCardBox userCardBox, AgentCardBoxLeft agentCardBoxLeft, AgentCardBoxTop agentCardBoxTop, AgentCardBoxRight agentCardBoxRight) {

        Player player = round.getCurrentPlayer();
        Card drawCard = round.showDrawCard();
        CardColour wildColour = round.getWildColour();
        DrawPile drawPile = round.getDrawPile();
        DiscardPile discardPile = round.getDiscardPile();
        int playerIndex = round.getPlayerList().getCurrentIndex();

        boolean result = false;

        Platform.runLater(() -> {
            cardBoxCenter.setGameLabel("Player " + player.getName() + " plays.");
        });

        if (player.getType().equals("agent")) {
            System.out.println("Player " + player.getName() + " plays.");
            System.out.println("Your cards:");
            System.out.println(player.showCards());
            System.out.println("The Draw Card is: " + drawCard.toString());
            if (drawCard.getColour() == CardColour.WILD || drawCard.getColour() == CardColour.WILD_FOUR) {
                System.out.println("The Colour is " + wildColour.toString());
            }
            ArrayList<Card> playCards = AgentUtils.getPlayableCards(player, drawCard, wildColour);
            switch (playCards.size()) {
                case 0:
                    System.out.println("I need to draw a Card");
                    Card card = drawPile.drawCard(discardPile);
                    System.out.println("the card a draw is: " + card.toString());
                    player.receiveCard(card);
                    if (player.equals(agentCardBoxLeft.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxLeft.addCard(card);
                        });
                    } else if (player.equals(agentCardBoxTop.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxTop.addCard(card);
                        });
                    } else if (player.equals(agentCardBoxRight.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxRight.addCard(card);
                        });
                    }
                    if (utils.cardCanPlay(card, drawCard, wildColour)) {
                        System.out.println("Looks like I can play this card");
                        if (card.getColour() == CardColour.WILD || card.getColour() == CardColour.WILD_FOUR) {
                            round.setWildColour(AgentUtils.askPlayerWildColour(player));
                            Platform.runLater(() -> {
                                cardBoxCenter.setWildColourLabel(round.getWildColour().toString());
                            });
                        } else {
                            Platform.runLater(() -> {
                                cardBoxCenter.clearWildColourLabel();
                            });
                        }

                        player.play(card, discardPile);
                        if (player.equals(agentCardBoxLeft.player)) {
                            Platform.runLater(() -> {
                                agentCardBoxLeft.removeCard(card);
                            });
                        } else if (player.equals(agentCardBoxTop.player)) {
                            Platform.runLater(() -> {
                                agentCardBoxTop.removeCard(card);
                            });
                        } else if (player.equals(agentCardBoxRight.player)) {
                            Platform.runLater(() -> {
                                agentCardBoxRight.removeCard(card);
                            });
                        }
                        System.out.println("I played: " + card.toString());
                        Platform.runLater(() -> {
                            cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                        });
                        result = true;
                    } else {
                        result = false;
                        System.out.println(player.getName() + " pass.");
                        Platform.runLater(() -> {
                            cardBoxCenter.setGameLabel(player.getName() + " pass.");
                        });
                    }
                    break;
                case 1:
                    System.out.println("OK I think I have a Card");
                    Card card1 = playCards.get(0);
                    if (card1.getColour() == CardColour.WILD || card1.getColour() == CardColour.WILD_FOUR) {
                        round.setWildColour(AgentUtils.askPlayerWildColour(player));
                        Platform.runLater(() -> {
                            cardBoxCenter.setWildColourLabel(round.getWildColour().toString());
                        });
                    } else {
                        Platform.runLater(() -> {
                            cardBoxCenter.clearWildColourLabel();
                        });
                    }
                    player.play(card1, discardPile);
                    if (player.equals(agentCardBoxLeft.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxLeft.removeCard(card1);
                        });
                    } else if (player.equals(agentCardBoxTop.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxTop.removeCard(card1);
                        });
                    } else if (player.equals(agentCardBoxRight.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxRight.removeCard(card1);
                        });
                    }
                    Platform.runLater(() -> {
                        cardBoxCenter.addCardToDiscardPile(new CardImageView(card1));
                    });
                    System.out.println("I played: " + card1.toString());

                    result = true;
                    break;
                default:
                    System.out.println("OK I have multiple options");
                    Card card2 = playCards.get(AgentUtils.randomCard(playCards));
                    if (card2.getColour() == CardColour.WILD || card2.getColour() == CardColour.WILD_FOUR) {
                        round.setWildColour(AgentUtils.askPlayerWildColour(player));
                        Platform.runLater(() -> {
                            cardBoxCenter.setWildColourLabel(round.getWildColour().toString());
                        });
                    } else {
                        Platform.runLater(() -> {
                            cardBoxCenter.clearWildColourLabel();
                        });
                    }
                    player.play(card2, discardPile);
                    if (player.equals(agentCardBoxLeft.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxLeft.removeCard(card2);
                        });
                    } else if (player.equals(agentCardBoxTop.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxTop.removeCard(card2);
                        });
                    } else if (player.equals(agentCardBoxRight.player)) {
                        Platform.runLater(() -> {
                            agentCardBoxRight.removeCard(card2);
                        });
                    }
                    Platform.runLater(() -> {
                        cardBoxCenter.addCardToDiscardPile(new CardImageView(card2));
                    });
                    System.out.println("I played: " + card2.toString());
                    result = true;
            }

        } else {
            Scanner scan = new Scanner(System.in);
            System.out.println("Player " + player.getName() + " plays.");
            System.out.println("Your cards:");
            System.out.println(player.showCards());
            System.out.println("The Draw Card is: " + drawCard.toString());
            if (drawCard.getColour() == CardColour.WILD || drawCard.getColour() == CardColour.WILD_FOUR) {
                System.out.println("The Colour is " + wildColour.toString());
            }

            BooleanValue runAction = new BooleanValue(true);
            BooleanValue cardPlayed = new BooleanValue(false);
            BooleanValue userOption = new BooleanValue(false);
            BooleanValue wildOption = new BooleanValue(false);

            DrawPileEventHandler drawPileEventHandler = new DrawPileEventHandler(round, userCardBox, cardBoxCenter, runAction, cardPlayed, userOption, wildOption);
            UserEventHandler userEventHandler = new UserEventHandler(round, userCardBox, cardBoxCenter, runAction, cardPlayed, userOption, wildOption, drawPileEventHandler);

            userCardBox.activateHandler(userEventHandler);
            cardBoxCenter.drawPileActivateHandler(drawPileEventHandler);

            do {
                try {

                    System.out.println("Please enter your option:");
                    System.out.println("continue input is: " + runAction.getValue());
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    System.out.println("Option not valid");

                }
            } while (runAction.getValue());
            result = cardPlayed.getValue();
            if (userOption.getValue()) {
                Platform.runLater(() -> {
                    userCardBox.removePlayerOptions();
                });
            }

            if (wildOption.getValue()) {
                Platform.runLater(() -> {
                    userCardBox.removeAskWildColours();
                });
            }

            System.out.println("I finished playing");
        }

        return result;
    }
}
