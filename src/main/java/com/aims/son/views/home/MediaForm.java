package com.aims.son.views.home;

import com.aims.son.entity.cart.Cart;
import com.aims.son.entity.cart.CartMedia;
import com.aims.son.entity.media.Media;
import com.aims.son.exception.MediaNotAvailableException;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.views.FXMLForm;
import com.aims.son.views.popup.PopupForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MediaForm extends FXMLForm {

    @FXML
    protected ImageView mediaImage;

    @FXML
    protected Label mediaTitle;

    @FXML
    protected Label mediaPrice;

    @FXML
    protected Label mediaAvail;

    @FXML
    protected Spinner<Integer> spinnerChangeNumber;

    @FXML
    protected Button addToCartBtn;

    private static Logger LOGGER = Utils.getLogger(MediaForm.class.getName());
    private Media media;
    private HomeForm home;

    public MediaForm(String screenPath, Media media, HomeForm home) throws IOException {
        super(screenPath);
        this.media = media;
        this.home = home;

        addToCartBtn.setOnMouseClicked(event -> {
            try {
                addToCart();
            } catch (SQLException | IOException e) {
                LOGGER.severe("Error while adding to cart: " + e.getMessage());
                try {
                    PopupForm.error("Error while adding media to cart.");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                   
                    e1.printStackTrace();
                }
            }
        });

        // Cập nhật thông tin media
        try {
            setMediaInfo();
        } catch (SQLException | IOException e) {
            LOGGER.severe("Error setting media info: " + e.getMessage());
            try {
                PopupForm.error("Error setting media information. Please try again.");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
               
                e1.printStackTrace();
            }
        }
    }

    // Phương thức lấy thông tin media
    public Media getMedia(){
        return media;
    }
    // Phương thức thêm media vào giỏ hàng
    private void addToCart() throws SQLException, IOException {
        CartMedia mediaInCart = home.getBController().checkMediaInCart(media);
        int requestedQuantity = spinnerChangeNumber.getValue(); // so luong muon add them
        int mediaQuatityInCart = 0; // so luong media da co trong cart
        if(mediaInCart != null) mediaQuatityInCart = mediaInCart.getQuantity();
        if (requestedQuantity + mediaQuatityInCart > media.getQuantity()) { // neu so luong trong cart + so luong muon them > so luong avail
            PopupForm.error("The quantity requested exceeds the available stock!");
            return;
        }
        Cart cart = Cart.getCart();
        if(mediaQuatityInCart == 0) { // neu chua co trong gio hang
            CartMedia cartMedia = new CartMedia(media, requestedQuantity, media.getPrice());
            cart.getListMedia().add(cartMedia);
            LOGGER.info("Added " + cartMedia.getQuantity() + " " + media.getTitle() + " to cart");
        }else { // neu da co trong gio hang thi tang so luong
            mediaInCart.setQuantity(mediaQuatityInCart + requestedQuantity);
        }

        home.getNumMediaCartLabel().setText(String.valueOf(cart.getTotalMedia() + " media"));
        PopupForm.success("The media " + media.getTitle() + " added to Cart");
    }

    // Phương thức cập nhật thông tin media
    private void setMediaInfo() throws SQLException, IOException {
        // Set hình ảnh media
        File file = new File(Configs.IMAGE_PATH + media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);

        mediaTitle.setText(media.getTitle());
        mediaPrice.setText(Utils.getCurrencyFormat(media.getPrice()));
        mediaAvail.setText(Integer.toString(media.getQuantity()));
        spinnerChangeNumber.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );
    }
    
}
