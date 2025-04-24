package com.aims.group19.listener;

import com.aims.group19.entity.order.Order;
import com.aims.group19.entity.payment.PaymentTransaction;

public interface TransactionResultListener {
    void onTransactionCompleted(PaymentTransaction transactionResult, Order order);
}

