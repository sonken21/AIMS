package com.aims.son.views.shipping;

import com.aims.son.entity.order.OrderMedia;
import com.aims.son.utils.Configs;
import com.aims.son.views.FXMLForm;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class ShippingMediaForm extends FXMLForm {
    @FXML
    ImageView imageView;

    @FXML
    Label nameLabel;

    @FXML
    Label quantityLabel;

    @FXML
    Label priceLabel;

    @FXML
    CheckBox rushDeliveryBox;

    private Boolean isRushDelivery;

    public ShippingMediaForm(String screenPath, Boolean rushDelivery) throws IOException {
        super(screenPath);
        this.isRushDelivery = rushDelivery;
    }

    public void setCartMedia(OrderMedia orderMedia) {
        File file = new File(Configs.IMAGE_PATH + orderMedia.getMedia().getImageURL());
        Image im = new Image(file.toURI().toString());
        imageView.setImage(im);
        nameLabel.setText(orderMedia.getMedia().getTitle());
        quantityLabel.setText(String.valueOf(orderMedia.getQuantity()));
        priceLabel.setText(String.valueOf(orderMedia.getPrice() * orderMedia.getQuantity()));
        rushDeliveryBox.setVisible(isRushDelivery);
        rushDeliveryBox.setSelected(orderMedia.isRushDelivery());
        if(!isRushDelivery){
            orderMedia.setRushDelivery(false);
        }
        rushDeliveryBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            orderMedia.setRushDelivery(newValue);
        });
    }
}
