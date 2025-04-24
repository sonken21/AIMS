package com.aims.group19.controller.repositories;

import com.aims.group19.entity.payment.PaymentTransaction;

import java.sql.SQLException;

public interface ITransactionRepository {
    void save(PaymentTransaction paymentTransaction)throws SQLException;
    int checkPaymentByOrderId(int orderId) throws SQLException;
}
