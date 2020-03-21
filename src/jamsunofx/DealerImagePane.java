/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamsunofx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author j_a_m
 */
public class DealerImagePane extends StackPane {

    public DealerImagePane() {
        Image image = new Image("images/dealer.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        this.getChildren().add(imageView);
    }
}
