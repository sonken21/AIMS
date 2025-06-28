package com.aims.son.repositories;

import com.aims.son.controller.repositories.ITransactionRepository;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.repositories.DbConnection.SQLiteConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRepository implements ITransactionRepository {

    private final Connection connection;
    public TransactionRepository() throws SQLException{
        connection = SQLiteConnectionManager.getConnection();
    }

    public void save(PaymentTransaction transaction) throws SQLException {
        String query = "INSERT INTO \"Transaction\" (orderID, createAt, content) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, transaction.getOrderID());
            preparedStatement.setDate(2, new java.sql.Date(transaction.getCreatedAt().getTime()));
            preparedStatement.setString(3, transaction.getTransactionContent());
            preparedStatement.executeUpdate();
        }
    }

    public int checkPaymentByOrderId(int orderId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Transaction WHERE orderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }
}
