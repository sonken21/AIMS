package com.aims.group19.shipping.calculator;

import com.aims.group19.entity.order.Order;

public interface IShippingCalculator {
    int calculateShippingFee(Order order);
}
