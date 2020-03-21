
package model;

import java.util.Stack;

public class DrawPile {
    
    private Stack<Card> drawPile;

    public DrawPile() {
        drawPile = new Stack<>();
    }
    
    public DrawPile(Deck deck){
        drawPile = new Stack<>();
        while (!deck.getDeck().isEmpty()) {
            Card tempCard = (Card)deck.getDeck().remove(deck.getDeck().size()-1);
            drawPile.push(tempCard);
        }
    }

    public void addCard(Card card) {
        drawPile.push(card);
    }

    public Card peek() {
        return drawPile.peek();
    }

    public Stack<Card> getDrawPile() {
        return drawPile;
    }
    
    public Card drawCard(DiscardPile discardPile){
        if(drawPile.isEmpty()){
            Card card = discardPile.drawCard();
            discardPile.shuffle();
            while(!discardPile.getDiscardPile().isEmpty()) {
                Card tempCard = discardPile.drawCard();
                drawPile.push(tempCard);
            }
            discardPile.addCard(card);
        }
        return drawPile.pop();
           
    }
    
}
