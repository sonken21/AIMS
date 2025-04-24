package com.aims.group19.shipping.calculator;

import com.aims.group19.entity.order.Order;

public abstract class ShippingFeeCalcDecorator implements IShippingCalculator {
    private IShippingCalculator baseCalculator;

    public ShippingFeeCalcDecorator(IShippingCalculator baseCalculator) {
        this.baseCalculator = baseCalculator;
    }

    @Override
    public int calculateShippingFee(Order order){
        return baseCalculator.calculateShippingFee(order);
    }
}
