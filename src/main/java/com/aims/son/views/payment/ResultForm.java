package com.aims.son.views.payment;

import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.utils.Configs;
import com.aims.son.views.BaseForm;
import com.aims.son.views.home.HomeForm;
import com.aims.son.views.invoice.PaymentInvoiceForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultForm extends BaseForm {
	public ResultForm(Stage stage, String screenPath, Invoice invoice, PaymentTransaction transaction) throws IOException {
		super(stage, screenPath);
		// Set the result and message
		if(transaction.isSuccess()){
			resultLabel.setText("SUCCESS");
			resultLabel.setStyle("-fx-text-fill: green;");
		}else {
			resultLabel.setText("FAILURE");
			resultLabel.setStyle("-fx-text-fill: red;");
			showInvoiceBtn.setVisible(false);

		}
		resultLabel.setText(transaction.isSuccess() ? "SUCCESS" : "FAILURE");
		messageLabel.setText(transaction.getMessage());

		okButton.setOnMouseClicked(e -> {
			if (homeScreenHandler != null) {
				homeScreenHandler.show();
			} else {
                try {
					stage.close();
                    homeScreenHandler = new HomeForm(stage, Configs.HOME_PATH);
					homeScreenHandler.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
		});
		showInvoiceBtn.setOnMouseClicked(e ->{
            try {
				BaseForm InvoiceScreenHandler = new PaymentInvoiceForm(this.stage, Configs.PAYMENT_INVOICE_SCREEN_PATH, invoice, transaction);
				InvoiceScreenHandler.setPreviousScreen(this);
				InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
				InvoiceScreenHandler.setScreenTitle("Invoice Screen");
				InvoiceScreenHandler.setBController(getBController());
				InvoiceScreenHandler.show();
			} catch (IOException ex) {
                throw new RuntimeException(ex);
            }

		});
	}

	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Button okButton;
	@FXML
	private Button showInvoiceBtn;
	@FXML
	private Label messageLabel;

}
