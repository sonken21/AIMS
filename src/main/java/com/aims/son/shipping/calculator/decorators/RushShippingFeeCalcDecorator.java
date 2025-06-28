package com.aims.son.shipping.calculator.decorators;

import com.aims.son.entity.order.Order;
import com.aims.son.entity.order.OrderMedia;
import com.aims.son.shipping.calculator.IShippingCalculator;
import com.aims.son.shipping.calculator.ShippingFeeCalcDecorator;

import java.util.HashMap;

public class RushShippingFeeCalcDecorator extends ShippingFeeCalcDecorator {
    public RushShippingFeeCalcDecorator(IShippingCalculator baseCalculator) {
        super(baseCalculator);
    }

    @Override
    public int calculateShippingFee(Order order){
        HashMap<String, String> deliveryInfo = (HashMap<String, String>) order.getDeliveryInfo();
        if(!Boolean.valueOf(deliveryInfo.get("rushDelivery"))) {
            order.setRushShippingFees(0);
            return super.calculateShippingFee(order);
        }
        int rushShippingFee = 0;
        for(Object object : order.getlstOrderMedia()){
            OrderMedia om = (OrderMedia) object;
            if(om.isRushDelivery()){
                rushShippingFee += om.getQuantity() * 10;
            }
        }
        order.setRushShippingFees(rushShippingFee);
        return rushShippingFee + super.calculateShippingFee(order);
    }
}
