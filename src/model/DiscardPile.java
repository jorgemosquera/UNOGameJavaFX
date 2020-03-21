package model;

import java.util.Collections;
import java.util.Stack;

public class DiscardPile {
    
    private Stack<Card> discardPile;

    public DiscardPile(Deck deck) {
        discardPile = new Stack<>();
        while (!deck.getDeck().isEmpty()) {
            Card tempCard = (Card)deck.getDeck().remove(deck.getDeck().size()-1);
            discardPile.add(tempCard);
        }
    }
    
    public DiscardPile() {
        discardPile = new Stack<>();
        
    }

    public Card drawCard() {
        return discardPile.pop();
    }

    public Stack<Card> getDiscardPile() {
        return discardPile;
    }
    
    public void addCard(Card card){
        discardPile.push(card);
    }
    
    public Card peekCard(){
        return discardPile.peek();
    }
    
    public void shuffle(){
        Collections.shuffle(discardPile);
    }
    
}
