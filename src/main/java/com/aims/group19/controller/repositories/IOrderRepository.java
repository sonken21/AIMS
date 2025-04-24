package com.aims.group19.controller.repositories;

import com.aims.group19.entity.order.Order;

import java.sql.SQLException;

public interface IOrderRepository {
    void save(Order order) throws SQLException;
}
