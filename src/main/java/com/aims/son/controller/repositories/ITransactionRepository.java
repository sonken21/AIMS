package com.aims.son.controller.repositories;

import com.aims.son.entity.payment.PaymentTransaction;

import java.sql.SQLException;

public interface ITransactionRepository {
    void save(PaymentTransaction paymentTransaction)throws SQLException;
    int checkPaymentByOrderId(int orderId) throws SQLException;
}
