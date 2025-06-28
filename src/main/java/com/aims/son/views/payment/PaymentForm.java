package com.aims.son.views.payment;


import com.aims.son.InterbankSubsystem.momo.MoMoConfig;
import com.aims.son.InterbankSubsystem.vnPay.VnPayConfig;
import com.aims.son.InterbankSubsystem.vnPay.VnPaySubsystemController;
import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.listener.TransactionResultListener;
import com.aims.son.utils.Configs;
import com.aims.son.views.BaseForm;
import com.aims.son.views.home.HomeForm;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class PaymentForm extends BaseForm {

	private final Invoice invoice;
    @FXML
    private VBox vBox;
    private PaymentTransaction transactionResult;
    private final TransactionResultListener listener;

    public PaymentForm(Stage stage, String screenPath, String paymentURL, TransactionResultListener listener, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.listener = listener;
        this.invoice = invoice;
        WebView paymentView = new WebView();
        WebEngine webEngine = paymentView.getEngine();
        webEngine.load(paymentURL);

        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Xử lý khi URL thay đổi
            handleUrlChanged(newValue);
        });
        vBox.getChildren().clear();
        vBox.getChildren().add(paymentView);
    }

    private void handleUrlChanged(String newValue) {
        if (newValue.contains(VnPayConfig.vnp_ReturnUrl)) {
            try {
                // Xử lý giao dịch và lưu kết quả
                transactionResult = VnPaySubsystemController.processResponse(newValue);

                if(!transactionResult.isSuccess()){
                   returnToPreviousScreen();
                   return;
                }
                if (listener != null) {
                    listener.onTransactionCompleted(transactionResult, invoice.getOrder());
                } else System.out.println("NULL");

                if (transactionResult != null) {
                    homeScreenHandler = new HomeForm(stage, Configs.HOME_PATH);
                    showResultScreen();
                }

            } catch (URISyntaxException | ParseException | IOException e) {
                e.printStackTrace();
            }
        }
        else if(newValue.contains(MoMoConfig.MOMO_RETURN_URL)){

        }
    }


    private void showResultScreen() throws IOException {
        BaseForm resultScreen = new ResultForm(this.stage, Configs.RESULT_SCREEN_PATH, invoice, transactionResult);
        resultScreen.setPreviousScreen(this);
        resultScreen.setHomeScreenHandler(homeScreenHandler);
        resultScreen.setScreenTitle("Result Screen");
        resultScreen.setBController(getBController());
        resultScreen.show();
    }

    public void returnToPreviousScreen(){
        getPreviousScreen().show();
    }
}
