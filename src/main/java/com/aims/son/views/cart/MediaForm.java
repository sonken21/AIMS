package com.aims.son.views.cart;

import com.aims.son.entity.cart.Cart;
import com.aims.son.entity.cart.CartMedia;
import com.aims.son.exception.MediaUpdateException;
import com.aims.son.exception.ViewCartException;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.views.FXMLForm;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class MediaForm extends FXMLForm {

	private static Logger LOGGER = Utils.getLogger(MediaForm.class.getName());

	@FXML
	protected HBox hboxMedia;

	@FXML
	protected ImageView image;

	@FXML
	protected VBox description;

	@FXML
	protected Label labelOutOfStock;

	@FXML
	protected VBox spinnerFX;

	@FXML
	protected Label title;

	@FXML
	protected Label price;

	@FXML
	protected Label currency;

	@FXML
	protected Button btnDelete;

	private CartMedia cartMedia;
	private Spinner<Integer> spinner;
	private CartForm cartScreen;

	public MediaForm(String screenPath, CartForm cartScreen) throws IOException {
		super(screenPath);
		this.cartScreen = cartScreen;
		hboxMedia.setAlignment(Pos.CENTER);
	}

	public void setCartMedia(CartMedia cartMedia) {
		this.cartMedia = cartMedia;
		setMediaInfo();
	}

	private void setMediaInfo() {
		title.setText(cartMedia.getMedia().getTitle());
		price.setText(Utils.getCurrencyFormat(cartMedia.getPrice() * cartMedia.getQuantity()));
		File file = new File(Configs.IMAGE_PATH + cartMedia.getMedia().getImageURL());
		Image im = new Image(file.toURI().toString());
		image.setImage(im);
		image.setPreserveRatio(false);
		image.setFitHeight(110);
		image.setFitWidth(92);

		// add delete button
		btnDelete.setFont(Configs.REGULAR_FONT);
		btnDelete.setOnMouseClicked(e -> {
			try {
				Cart.getCart().removeCartMedia(cartMedia); // update user cart
				cartScreen.updateCart(); // re-display user cart
				LOGGER.info("Deleted " + cartMedia.getMedia().getTitle() + " from the cart");
			} catch (SQLException exp) {
				exp.printStackTrace();
				throw new ViewCartException();
			}
		});

		initializeSpinner();
	}

	private void initializeSpinner(){
		SpinnerValueFactory<Integer> valueFactory = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, cartMedia.getQuantity());
		spinner = new Spinner<Integer>(valueFactory);
		spinner.setOnMouseClicked( e -> {
			try {
				int numOfProd = this.spinner.getValue();
				int remainQuantity = cartMedia.getMedia().getQuantity();
				LOGGER.info("NumOfProd: " + numOfProd + " -- remainOfProd: " + remainQuantity);
				if (numOfProd > remainQuantity){
					LOGGER.info("product " + cartMedia.getMedia().getTitle() + " only remains " + remainQuantity + " (required " + numOfProd + ")");
					labelOutOfStock.setText("Sorry, Only " + remainQuantity + " remain in stock");
					spinner.getValueFactory().setValue(remainQuantity);
					numOfProd = remainQuantity;
				}

				// update quantity of mediaCart in useCart
				cartMedia.setQuantity(numOfProd);

				// update the total of mediaCart
				price.setText(Utils.getCurrencyFormat(numOfProd*cartMedia.getPrice()));

				// update subtotal and amount of Cart
				cartScreen.updateCartAmount();

			} catch (SQLException e1) {
				throw new MediaUpdateException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
			}

		});
		spinnerFX.setAlignment(Pos.CENTER);
		spinnerFX.getChildren().add(this.spinner);
	}
}