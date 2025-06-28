package com.aims.son.InterbankSubsystem.momo;

import com.aims.son.InterbankSubsystem.IPayment;
import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.exception.PaymentException;
import com.aims.son.exception.UnrecognizedException;
import com.aims.son.listener.TransactionResultListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MoMoSubsystemController implements IPayment {


    public MoMoSubsystemController() {
    }

    @Override
    public String payOrder(String orderInfo, TransactionResultListener listener, Invoice invoice) throws IOException {
        try {
            var req = new MoMoRequest(invoice.getAmount(),orderInfo);
            return req.generatePaymentURL();
        } catch (PaymentException | UnrecognizedException e) {
            throw new IOException("Failed to process MoMo payment", e);
        }
    }

    public static PaymentTransaction processResponse(String response) throws URISyntaxException, ParseException {
        var res = new MoMoResponse(response);
        String errorCode = res.getResultCode()+"";
        String transactionId = res.getOrderId();
        String transactionContent = res.getMessage();
        long amount = res.getAmount();
        String createdAt = res.getResponseTime()+"";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = dateFormat.parse(createdAt);

        return new PaymentTransaction(errorCode, transactionId, transactionContent, amount, 1, date); // orderId có thể được sinh động
    }
}
