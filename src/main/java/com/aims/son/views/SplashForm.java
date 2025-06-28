package com.aims.son.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashForm implements Initializable {

    @FXML
    ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/main/resources/com/aims/son/fxml/images/Logo.png");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);
    }
}