package com.aims.son.views.shipping;

import com.aims.son.controller.BaseController;
import com.aims.son.controller.PlaceOrderController;
import com.aims.son.controller.ViewCartController;
import com.aims.son.entity.cart.Cart;
import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.order.Order;
import com.aims.son.entity.order.OrderMedia;
import com.aims.son.exception.InvalidDeliveryInfoException;
import com.aims.son.exception.ViewCartException;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.views.BaseForm;
import com.aims.son.views.cart.CartForm;
import com.aims.son.views.home.HomeForm;
import com.aims.son.views.invoice.InvoiceForm;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static com.aims.son.utils.Configs.RUSH_DELIVERY_SUPPORT_PROVINCES;

public class DeliveryForm extends BaseForm implements Initializable {
	public static Logger LOGGER = Utils.getLogger(HomeForm.class.getName());
	@FXML
	private Label screenTitle;

	@FXML
	private TextField name;

	@FXML
	private TextField email;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;

	@FXML
	private RadioButton btnRushDelivery;

	@FXML
	private ComboBox<String> deliveryTime;

	@FXML
	private Label deliveryTimeLabel;

	@FXML
	private Label deliveryInstructionLabel;

	@FXML
	private TextArea deliveryInstruction;
	@FXML
	private Label totalPriceLabel;
	@FXML
	private Label rushDeliveryLabel;

	@FXML
	private Label rushDeliveryLabelForm;

	@FXML
	private  Label rushDeliveryNoteLabel;

	@FXML
	private ComboBox<String> province;

	@FXML
	private VBox mediaCart;

	@FXML
	private Hyperlink cartLink;

	private Order order;

	public DeliveryForm(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
			if(newValue && firstTime.get()){
				content.requestFocus(); // Delegate the focus to container
				firstTime.setValue(false); // Variable value changed for future references
			}
		});
		this.province.getItems().addAll(Configs.PROVINCES);
		rushDeliveryLabelForm.setDisable(true);
		btnRushDelivery.setDisable(true);
		rushDeliveryNoteLabel.setVisible(true);
		province.valueProperty().addListener((observable, oldSelectedProvince, newSelectedProvince) -> {
			if(Arrays.asList(RUSH_DELIVERY_SUPPORT_PROVINCES).contains(newSelectedProvince)){
				rushDeliveryLabelForm.setDisable(false);
				btnRushDelivery.setDisable(false);
				rushDeliveryNoteLabel.setVisible(false);
			}else {
				rushDeliveryLabelForm.setDisable(true);
				btnRushDelivery.setSelected(false);
				btnRushDelivery.setDisable(true);
				rushDeliveryNoteLabel.setVisible(true);
			}
		});
		setupDeliveryControls();
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {
		// add info to messages
		HashMap<String, String> messages = new HashMap<>();
		messages.put("name", name.getText());
		messages.put("email", email.getText());
		messages.put("phone", phone.getText());
		messages.put("address", address.getText());
		messages.put("province", province.getValue());
		messages.put("rushDelivery", String.valueOf(btnRushDelivery.isSelected()));

		if(btnRushDelivery.isSelected()){
			messages.put("deliveryTime", deliveryTime.getValue());
			messages.put("deliveryInstruction", deliveryInstruction.getText());
		}

		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(messages);
		} catch (InvalidDeliveryInfoException e) {
			showAlert("Validation Error", e.getMessage());
			return;
		}
		order.setDeliveryInfo(messages);
		// calculate shipping fees
		LOGGER.info("Submit Delivery Info");
		getBController().calculateOrderShippingFee(order);

		// create invoice screen
		Invoice invoice = getBController().createInvoice(order);
		BaseForm InvoiceScreenHandler = new InvoiceForm(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
		InvoiceScreenHandler.setPreviousScreen(this);
		InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
		InvoiceScreenHandler.setScreenTitle("Invoice Screen");
		InvoiceScreenHandler.setBController((BaseController) getBController());
		InvoiceScreenHandler.show();
	}
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}
	public void notifyError(){
		// TODO: implement later on if we need
	}

	private void setupDeliveryControls() {
		deliveryTime.setVisible(false);
		deliveryTimeLabel.setVisible(false);
		deliveryInstructionLabel.setVisible(false);
		deliveryInstruction.setVisible(false);
		rushDeliveryLabel.setVisible(false);
		totalPriceLabel.setText(String.valueOf(Cart.getCart().calSubtotal()));

		btnRushDelivery.selectedProperty().addListener((observable, oldValue, newValue) -> {
			deliveryInstruction.setVisible(newValue);
			deliveryTime.setVisible(newValue);
			deliveryTimeLabel.setVisible(newValue);
			deliveryInstructionLabel.setVisible(newValue);
			rushDeliveryLabel.setVisible(newValue);

			if (!newValue) {
				deliveryInstruction.clear();
				deliveryTime.setValue(null);
			}
			displayShippingCartMedia(btnRushDelivery.isSelected());
		});

		cartLink.setOnAction(e -> {
			try {
				LOGGER.info("User clicked to view cart");
				CartForm cartScreen = new CartForm(this.stage, Configs.CART_SCREEN_PATH);
				cartScreen.setHomeScreenHandler(homeScreenHandler);
				cartScreen.setBController(new ViewCartController());
				cartScreen.requestToViewCart(this);
			} catch (IOException | SQLException e1) {
				throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
			}
		});
	}

	private void displayShippingCartMedia(boolean rushDelivery) {
		mediaCart.getChildren().clear();
		List lstMedia = order.getlstOrderMedia();

		try {
			for (Object cm : lstMedia) {
				OrderMedia orderMedia = (OrderMedia) cm;
				ShippingMediaForm shippingMedia = new ShippingMediaForm(Configs.SHIPPING_MEDIA_PATH, rushDelivery);
				shippingMedia.setCartMedia(orderMedia);

				mediaCart.getChildren().add(shippingMedia.getContent());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void show(){
		if (order != null) {
			displayShippingCartMedia(btnRushDelivery.isSelected());
		}
		super.show();
	}

	public void requestToUpdateOrderDeliveryInfo() {
		HashMap<String, String> deliveryInfo = order.getDeliveryInfo();
		name.setText(deliveryInfo.get("name"));
		email.setText(deliveryInfo.get("email"));
		phone.setText(deliveryInfo.get("phone"));
		address.setText(deliveryInfo.get("address"));
		province.setValue(deliveryInfo.get("province"));

		if(Boolean.parseBoolean(deliveryInfo.get("rushDelivery"))){
			rushDeliveryLabel.setVisible(true);
			btnRushDelivery.setSelected(true);
			deliveryTime.setValue(deliveryInfo.get("deliveryTime"));
			deliveryInstruction.setText(deliveryInfo.get("deliveryInstruction"));
			displayShippingCartMedia(true);
		}
	}
}