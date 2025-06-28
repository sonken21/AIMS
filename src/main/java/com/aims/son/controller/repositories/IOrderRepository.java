package com.aims.son.controller.repositories;

import com.aims.son.entity.order.Order;

import java.sql.SQLException;

public interface IOrderRepository {
    void save(Order order) throws SQLException;
}
