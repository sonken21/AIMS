package com.aims.son.shipping.calculator;

import com.aims.son.entity.order.Order;

public interface IShippingCalculator {
    int calculateShippingFee(Order order);
}
