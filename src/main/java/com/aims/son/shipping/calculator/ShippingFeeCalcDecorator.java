package com.aims.son.shipping.calculator;

import com.aims.son.entity.order.Order;

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
