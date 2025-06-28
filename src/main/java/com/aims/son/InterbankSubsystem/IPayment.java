package com.aims.son.InterbankSubsystem;

import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.exception.PaymentException;
import com.aims.son.exception.UnrecognizedException;
import com.aims.son.listener.TransactionResultListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * The {@code IPayment} class is used to communicate with the
 * {@link com.aims.son.InterbankSubsystem.vnPay.VnPaySubsystemController InterbankSubsystem} to make transaction.
 *
 * @author hieud
 */
public interface IPayment {
    String payOrder(String orderInfo, TransactionResultListener listener, Invoice invoice) throws IOException;
    static PaymentTransaction processResponse(String response) throws URISyntaxException, ParseException {
        return null;
    }
}
