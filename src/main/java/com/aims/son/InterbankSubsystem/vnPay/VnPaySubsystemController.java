package com.aims.son.InterbankSubsystem.vnPay;

import com.aims.son.InterbankSubsystem.IPayment;
import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.listener.TransactionResultListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class VnPaySubsystemController implements IPayment {


    public VnPaySubsystemController() {
    }

    static Logger logger = Logger.getLogger(VnPaySubsystemController.class.getName());

    @Override
    public String payOrder(String orderInfo, TransactionResultListener listener, Invoice invoice) throws IOException {
        var req = new VNPayRequest(invoice.getAmount(), orderInfo);
        return req.buildQueryURL();
    }

    public static PaymentTransaction processResponse(String vnpReturnURL) throws URISyntaxException, ParseException {
        URI uri = new URI(vnpReturnURL);
        String query = uri.getQuery();
        logger.info(query);
        VNPayResponse response = new VNPayResponse(query);

        // Create Payment transaction
        String errorCode = response.getVnp_TransactionStatus();
        String transactionId = response.getVnp_TransactionNo();
        String transactionContent = response.getVnp_OrderInfo();
        long amount = Long.parseLong(response.getVnp_Amount()) / 100;
        String createdAt = response.getVnp_PayDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Integer orderId = 1;
        Date date = dateFormat.parse(createdAt);

        return new PaymentTransaction(errorCode, transactionId, transactionContent, amount, orderId, date);
    }
}
