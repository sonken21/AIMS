package com.aims.son.views.invoice;

import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.order.OrderMedia;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.exception.ProcessInvoiceException;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.views.BaseForm;
import com.aims.son.views.home.HomeForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class PaymentInvoiceForm extends BaseForm
{

    @FXML
    private Label pageTitle;

    @FXML
    private Label transactionIdLabel;

    @FXML
    private Label transactionContentLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label hourLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label provinceLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private VBox vboxItems;
    @FXML
    private Button okButton;

    private Invoice invoice;
    private PaymentTransaction transaction;
    public PaymentInvoiceForm(Stage stage, String screenPath, Invoice invoice, PaymentTransaction transaction) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        this.transaction = transaction;
        setInvoiceInfo();
        okButton.setOnMouseClicked(e -> {
            BaseForm prev = getPreviousScreen();
            if (prev != null) {
                prev.show();
            } else {
                System.out.println("prev is null");
            }
        });
    }

    private void setInvoiceInfo(){
        HashMap<String, String> deliveryInfo = invoice.getOrder().getDeliveryInfo();
        transactionIdLabel.setText(transaction.getTransactionId());
        transactionContentLabel.setText(transaction.getTransactionContent().replace("+", " "));
        dateLabel.setText(new java.text.SimpleDateFormat("yyyy-MM-dd").format(transaction.getCreatedAt()));
        hourLabel.setText(new java.text.SimpleDateFormat("HH:mm:ss").format(transaction.getCreatedAt()));
        nameLabel.setText(deliveryInfo.get("name"));
        phoneLabel.setText(deliveryInfo.get("phone"));
        provinceLabel.setText(deliveryInfo.get("province"));
        addressLabel.setText(deliveryInfo.get("address"));
        totalAmountLabel.setText(invoice.getAmount() + "đ");
        invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceForm mis = new MediaInvoiceForm(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia((OrderMedia) orderMedia);
                vboxItems.getChildren().add(mis.getContent());
            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }

        });
    }

    @FXML
    void returnHome(MouseEvent event) throws IOException {
        homeScreenHandler.show();
    }
}
