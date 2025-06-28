package com.aims.son.views.popup;

import com.aims.son.utils.Configs;
import com.aims.son.views.BaseForm;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;


public class PopupForm extends BaseForm {


    @FXML
    ImageView tickicon;

    @FXML
    Label message;


    public PopupForm(Stage stage) throws IOException{
        super(stage, Configs.POPUP_PATH);
    }

    private static PopupForm popup(String message, String imagePath, Boolean undecorated) throws IOException{
        PopupForm popup = new PopupForm(new Stage());
        if (undecorated) popup.stage.initStyle(StageStyle.UNDECORATED);
        popup.message.setText(message);
        popup.setImage(imagePath);
        return popup;
    }

    public static void success(String message) throws IOException{
        popup(message, Configs.IMAGE_PATH_ICON + "/" + "tickgreen.png", true).show(true);
    }

    public static void error(String message) throws IOException{
        popup(message, Configs.IMAGE_PATH_ICON + "/" + "tickerror.png", false).show(false);
    }

    public static PopupForm loading(String message) throws IOException{
        return popup(message, Configs.IMAGE_PATH_ICON + "/" + "loading.gif", true);
    }

    public void setImage(String path) {
        super.setImage(tickicon, path);
    }

    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    public void show(double time) {
        super.show();
        close(time);
    }

    public void close(double time){
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }
}
