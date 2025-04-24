package com.aims.group19.InterbankSubsystem;

import com.aims.group19.entity.invoice.Invoice;
import com.aims.group19.entity.payment.PaymentTransaction;
import com.aims.group19.exception.PaymentException;
import com.aims.group19.exception.UnrecognizedException;
import com.aims.group19.listener.TransactionResultListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * The {@code IPayment} class is used to communicate with the
 * {@link com.aims.group19.InterbankSubsystem.vnPay.VnPaySubsystemController InterbankSubsystem} to make transaction.
 *
 * @author hieud
 */
public interface IPayment {
    String payOrder(String orderInfo, TransactionResultListener listener, Invoice invoice) throws IOException;
    static PaymentTransaction processResponse(String response) throws URISyntaxException, ParseException {
        return null;
    }
}
