package com.aims.son.shipping.calculator.concretes;

import com.aims.son.entity.order.Order;
import com.aims.son.entity.order.OrderMedia;
import com.aims.son.shipping.calculator.IShippingCalculator;
import com.aims.son.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.aims.son.utils.Configs.SPECIAL_PROVINCE;

public class RegularShippingFeeCalculator implements IShippingCalculator {
    private static Logger LOGGER = Utils.getLogger(RegularShippingFeeCalculator.class.getName());

    @Override
    public int calculateShippingFee(Order order) {
        int shippingFees;
        int totalAmount = order.getAmount();
        LOGGER.info("Total Order Value: " + totalAmount);
        HashMap<String, String> deliveryInfo = (HashMap<String, String>) order.getDeliveryInfo();
        boolean isRushDelivery = Boolean.valueOf(deliveryInfo.get("rushDelivery"));
        if(isRushDelivery){
            totalAmount = order.getAmountWithoutRushItems();
            LOGGER.info("Total Order Value without Rush Item: " + totalAmount);
        }
        List<OrderMedia> lstOrderMedia = (List<OrderMedia>) order.getlstOrderMedia();
        OrderMedia heaviestOrderMedia = lstOrderMedia.get(0);
        for(OrderMedia om : lstOrderMedia){
            if(om.isRushDelivery() && isRushDelivery) continue;
            double omWeight = om.getMedia().getWeight();
            if(omWeight > heaviestOrderMedia.getMedia().getWeight()){
                heaviestOrderMedia = om;
            }
        }
        LOGGER.info("=========> The heaviest item in Order is " + heaviestOrderMedia.getMedia().getTitle() + " with the weigth is " + heaviestOrderMedia.getMedia().getWeight());
        double totalHeaviestMediaQuantityWeight = heaviestOrderMedia.getMedia().getWeight() * heaviestOrderMedia.getQuantity();
        LOGGER.info("=========> Total heaviest weight is " + totalHeaviestMediaQuantityWeight);
        String city = deliveryInfo.get("province");
        LOGGER.info("=========> The city is " + city);
        if(Arrays.asList(SPECIAL_PROVINCE).contains(city)){
            shippingFees = 22;
            LOGGER.info("=========> For special provice, Initial shipping fee is : " + shippingFees);
            if(totalHeaviestMediaQuantityWeight > 3){
                shippingFees += (int) ((totalHeaviestMediaQuantityWeight - 3) * 2 * 2.5);
                LOGGER.info("=========> Total heaviest weight is greater than 3kg, so the new shipping fee is " + shippingFees);
            }
        }else {
            shippingFees = 30;
            LOGGER.info("=========> For elsewhere in VietNam, Initial shipping fee is : " + shippingFees);
            if(totalHeaviestMediaQuantityWeight > 0.5){
                shippingFees += (int) ((totalHeaviestMediaQuantityWeight - 0.5) * 2 * 2.5);
                LOGGER.info("=========> Total heaviest weight is greater than 0.5kg, so the new shipping fee is " + shippingFees);
            }
        }
        if(totalAmount > 100){
            shippingFees -= 25;
            LOGGER.info("=========> Total Amount is greater than 100k, shipping fee will be reduced by 25k, so the new shipping fee is " + shippingFees);
        }
        if(shippingFees < 0){
            shippingFees = 0;
            LOGGER.info("=========> Because shipping fee is less than 0, the order will be qualified for free shipping");
        }
        order.setShippingFees(shippingFees);
        return shippingFees;
    }
}
