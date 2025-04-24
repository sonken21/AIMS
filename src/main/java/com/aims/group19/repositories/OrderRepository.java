package com.aims.group19.repositories;

import com.aims.group19.controller.repositories.IOrderRepository;
import com.aims.group19.entity.order.Order;
import com.aims.group19.repositories.DbConnection.SQLiteConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class OrderRepository implements IOrderRepository {
    private final Connection connection;
    public OrderRepository() throws SQLException{
        connection = SQLiteConnectionManager.getConnection();
    }
    @Override
    public void save(Order order) throws SQLException {
        String query = "INSERT INTO \"Order\" (email, address, phone, userID, shipping_fee) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            HashMap<String, String> deliveryInfo = order.getDeliveryInfo();
            preparedStatement.setString(1, deliveryInfo.get("email"));
            preparedStatement.setString(2, deliveryInfo.get("address"));
            preparedStatement.setString(3, deliveryInfo.get("phone"));
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, order.getShippingFees());
            preparedStatement.executeUpdate();
        }
    }

    public int getLatestOrderId() throws SQLException {
        String sql = "SELECT id FROM `Order` ORDER BY id DESC LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

}
