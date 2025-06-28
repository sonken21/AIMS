package com.aims.son.listener;

import com.aims.son.entity.order.Order;
import com.aims.son.entity.payment.PaymentTransaction;

public interface TransactionResultListener {
    void onTransactionCompleted(PaymentTransaction transactionResult, Order order);
}

