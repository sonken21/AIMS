package com.aims.son.views.invoice;

import com.aims.son.InterbankSubsystem.IPayment;
import com.aims.son.InterbankSubsystem.vnPay.VnPaySubsystemController;
import com.aims.son.controller.PaymentController;
import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.order.OrderMedia;
import com.aims.son.exception.MediaNotAvailableException;
import com.aims.son.exception.PaymentException;
import com.aims.son.exception.ProcessInvoiceException;
import com.aims.son.repositories.OrderRepository;
import com.aims.son.repositories.TransactionRepository;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.views.BaseForm;
import com.aims.son.views.payment.PaymentForm;
import com.aims.son.views.shipping.DeliveryForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

public class InvoiceForm extends BaseForm {

	private static Logger LOGGER = Utils.getLogger(InvoiceForm.class.getName());

	@FXML
	private Label pageTitle;

	@FXML
	private Label name;

	@FXML
	private Label phone;

	@FXML
	private Label province;

	@FXML
	private Label address;

	@FXML
	private Label email;

	@FXML
	private Label subtotal;

	@FXML
	private Label subtotalWithVAT;

	@FXML
	private Label shippingFees;

	@FXML
	private Label total;

	@FXML
	private VBox vboxRegularItems;

	@FXML
	private VBox vboxRushItems;
	@FXML
	private Button btnConfirm;

	@FXML
	private Hyperlink backLink;

	@FXML
	private Label rushShippingFee;

	private Invoice invoice;

	public InvoiceForm(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		setInvoiceInfo();
		btnConfirm.setOnMouseClicked(e -> {
			LOGGER.info("Pay Order button clicked");
			try {
				requestToPayOrder();

			} catch (IOException | SQLException exp) {
				LOGGER.severe("Cannot pay the order, see the logs");
				exp.printStackTrace();
				throw new PaymentException(Arrays.toString(exp.getStackTrace()).replaceAll(", ", "\n"));
			}

		});

		backLink.setOnAction(event -> {
			try {
				DeliveryForm deliveryFormHandler = (DeliveryForm) getPreviousScreen();
				deliveryFormHandler.requestToUpdateOrderDeliveryInfo();
				deliveryFormHandler.show();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void setInvoiceInfo(){
		HashMap<String, String> deliveryInfo = invoice.getOrder().getDeliveryInfo();

		name.setText(deliveryInfo.get("name"));
		province.setText(deliveryInfo.get("province"));
		email.setText(deliveryInfo.get("email"));
		address.setText(deliveryInfo.get("address"));
		phone.setText(deliveryInfo.get("phone"));
		subtotal.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmount()));
		subtotalWithVAT.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmountWithVat()));
		shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().getShippingFees()));
		rushShippingFee.setText(Utils.getCurrencyFormat(invoice.getOrder().getRushShippingFees()));

		int amount = invoice.getOrder().getAmountWithVat() + invoice.getOrder().getShippingFees() + invoice.getOrder().getRushShippingFees();
		total.setText(Utils.getCurrencyFormat(amount));
		invoice.setAmount(amount);

		invoice.getOrder().getlstOrderMedia().forEach(media -> {
			try {
				MediaInvoiceForm mis = new MediaInvoiceForm(Configs.INVOICE_MEDIA_SCREEN_PATH);
				OrderMedia orderMedia = (OrderMedia) media;
				mis.setOrderMedia(orderMedia);
				if(orderMedia.isRushDelivery()){
					vboxRushItems.getChildren().add(mis.getContent());
				}else {
					vboxRegularItems.getChildren().add(mis.getContent());
				}
			} catch (IOException | SQLException e) {
				System.err.println("errors: " + e.getMessage());
				throw new ProcessInvoiceException(e.getMessage());
			}
		});

	}

	public void requestToPayOrder() throws SQLException, IOException {
		try {
			// create PaymentService for VnPay Subsystem by using strategy pattern
			IPayment paymentService = new VnPaySubsystemController();

			TransactionRepository transactionRepo = new TransactionRepository();
			OrderRepository oderRepo = new OrderRepository();
			PaymentController payOrderController = new PaymentController(paymentService, transactionRepo, oderRepo);
			String paymentURL = payOrderController.payOrder("Chuyen tien qua AIMS. So tien " + invoice.getAmount() + " dong", invoice);
			var paymentScreen = new PaymentForm(this.stage, Configs.PAYMENT_SCREEN_PATH, paymentURL, payOrderController, invoice);

			paymentScreen.setPreviousScreen(this);
			paymentScreen.setHomeScreenHandler(homeScreenHandler);
			paymentScreen.setScreenTitle("Payment Screen");
			paymentScreen.setBController(payOrderController);
			paymentScreen.show();
		} catch (MediaNotAvailableException e) {

		}
	}
}
