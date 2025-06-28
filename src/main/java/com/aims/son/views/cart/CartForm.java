package com.aims.son.views.cart;

import com.aims.son.controller.PlaceOrderController;
import com.aims.son.controller.ViewCartController;
import com.aims.son.entity.cart.CartMedia;
import com.aims.son.entity.order.Order;
import com.aims.son.exception.MediaNotAvailableException;
import com.aims.son.exception.PlaceOrderException;
import com.aims.son.shipping.calculator.concretes.RegularShippingFeeCalculator;
import com.aims.son.shipping.calculator.decorators.RushShippingFeeCalcDecorator;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.validators.DeliveryFormValidatorContext;
import com.aims.son.views.BaseForm;
import com.aims.son.views.popup.PopupForm;
import com.aims.son.views.shipping.DeliveryForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class CartForm extends BaseForm {

	private static Logger LOGGER = Utils.getLogger(CartForm.class.getName());

	// Khai báo các biến controller
	private PlaceOrderController orderController;
	private ViewCartController cartController;

	@FXML
	private ImageView aimsImage;

	@FXML
	private Label pageTitle;

	@FXML
	VBox vboxCart;

	@FXML
	private Label shippingFees;

	@FXML
	private Label labelAmount;

	@FXML
	private Label labelSubtotal;

	@FXML
	private Label labelVAT;

	@FXML
	private Button btnPlaceOrder;

	@FXML
	private Button homeBtn;

	public CartForm(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);

		// Khởi tạo controller
		this.orderController = new PlaceOrderController(new DeliveryFormValidatorContext(), new RushShippingFeeCalcDecorator(new RegularShippingFeeCalculator()));
		this.cartController = new ViewCartController();

		// fix relative image path caused by fxml
		File file = new File("com/aims/son/fxml/images/Logo.png");
		Image im = new Image(file.toURI().toString());
		aimsImage.setImage(im);

		// on mouse clicked, we back to home
		aimsImage.setOnMouseClicked(e -> homeScreenHandler.show());
		homeBtn.setOnMouseClicked(e -> homeScreenHandler.show());

		// on mouse clicked, we start processing place order usecase
		btnPlaceOrder.setOnMouseClicked(e -> {
			LOGGER.info("Place Order button clicked");
			try {
				requestToPlaceOrder();
			} catch (SQLException | IOException exp) {
				LOGGER.severe("Cannot place the order, see the logs");
				exp.printStackTrace();
			} catch (PlaceOrderException ex) {
				LOGGER.severe("PlaceOrderException occurred: " + ex.getMessage());
				ex.printStackTrace();
			}
		});
	}

	public Label getLabelAmount() {
		return labelAmount;
	}

	public Label getLabelSubtotal() {
		return labelSubtotal;
	}

	public ViewCartController getViewCartController() {
		return (ViewCartController) getBController();
	}

	public void requestToViewCart(BaseForm prevScreen) throws SQLException {
		setPreviousScreen(prevScreen);
		setScreenTitle("Cart Screen");
		getViewCartController().checkAvailabilityOfProduct();

		displayCartWithMediaAvailability();
		show();
	}

	public void requestToPlaceOrder() throws SQLException, IOException, PlaceOrderException {
		try {
			// create placeOrderController and process the order
			PlaceOrderController placeOrderController = new PlaceOrderController(new DeliveryFormValidatorContext(),
					new RushShippingFeeCalcDecorator(new RegularShippingFeeCalculator()));
			if (orderController.getListCartMedia().isEmpty()) {
				PopupForm.error("You don't have anything to place");
				return;
			}

			orderController.placeOrder();

			// display available media
			displayCartWithMediaAvailability();

			// create order
			Order order = orderController.createOrder();

			// display shipping form
			DeliveryForm deliveryFormHandler = new DeliveryForm(this.stage, Configs.SHIPPING_SCREEN_PATH, order);
			deliveryFormHandler.setPreviousScreen(this);
			deliveryFormHandler.setHomeScreenHandler(homeScreenHandler);
			deliveryFormHandler.setScreenTitle("Shipping Screen");
			deliveryFormHandler.setBController(orderController);
			deliveryFormHandler.show();

		} catch (MediaNotAvailableException e) {
			displayCartWithMediaAvailability();
		} catch (Exception e) {
			throw new PlaceOrderException("Failed to place the order", e);
		}
	}

	public void updateCart() throws SQLException{
		getViewCartController().checkAvailabilityOfProduct();
		displayCartWithMediaAvailability();
	}

	void updateCartAmount(){
		// calculate subtotal and amount
		int subtotal = getViewCartController().getCartSubtotal();
		int vat = (int)((Configs.PERCENT_VAT/100)*subtotal);
		int amount = subtotal + vat;
		labelSubtotal.setText(Utils.getCurrencyFormat(subtotal));
		labelVAT.setText(Utils.getCurrencyFormat(vat));
		labelAmount.setText(Utils.getCurrencyFormat(amount));
	}
	private void displayCartWithMediaAvailability() {
		vboxCart.getChildren().clear();

		List lstMedia = cartController.getListCartMedia();

		try {
			for (Object cm : lstMedia) {
				CartMedia cartMedia = (CartMedia) cm;
				MediaForm mediaCartScreen = new MediaForm(Configs.CART_MEDIA_PATH, this);
				mediaCartScreen.setCartMedia(cartMedia);

				vboxCart.getChildren().add(mediaCartScreen.getContent());
			}
			updateCartAmount();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
