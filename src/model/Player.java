
package model;

import java.util.ArrayList;

public class Player {
    
    private String name;
    private int points;
    private ArrayList<Card> playerCards;
    private String type;

    public Player(String name, String type) {
        this.name = name;
        this.points = 0;
        this.playerCards = new ArrayList<>();
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }

    public int calculatePoints(){
        int cardPoints = 0;
        for(Card card:playerCards){
            cardPoints = cardPoints + card.getCardPoints();
        }
        return cardPoints;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points){
        this.points = this.points + points;
    }

    @Override
    public String toString() {
        return name + " " + points + "\n";
    }

    public void receiveCard(Card card) {
        playerCards.add(card);
    }

    
    public String showCards() {
        String result = "";
        for(Card card : playerCards){
            result += card.toString() +" - ";
        }
        return result;
    }

    public ArrayList<Card> getPlayerCards(){
        return playerCards;
    }
    
    //TODO: cohesion -- verificar esta funcion. 
    public void play(int cardSelection, DiscardPile discardPile){
         discardPile.addCard(playerCards.remove(cardSelection-1));
    }
    
    //TODO: cohesion -- verificar esta funcion. 
    public void play(Card card, DiscardPile discardPile){
        int index = playerCards.indexOf(card);
//        System.out.println("the index is: "+ index);
        Card card2 = playerCards.remove(index);
//        System.out.println("card removed is: " +card.toString());
        discardPile.addCard(card2);
//        System.out.println("card added to discardpile");
        
    }

    public boolean wonGame() {
        return this.playerCards.isEmpty();
    }   
}
