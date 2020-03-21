/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamsunofx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Card;

/**
 *
 * @author j_a_m
 */
public class CardImageView extends ImageView{
    Card card;
    
    public CardImageView(Image image, Card card){
        super(image);
        this.card = card;               
    }
    
    public Card getCard(){
        return card;
    }
    
    public CardImageView(Card card){
        super(new Image("images/" + card.getColour().toImage() + "-" + card.getValue().toImage() + ".png"));
        setFitWidth(68);
        setFitHeight(98);
        this.card = card;
        
    }
}
