package com.aims.son.entity.order;

import com.aims.son.utils.Configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    private int shippingFees;
    private int rushShippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;
    private Integer id;

    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice() * om.getQuantity();
        }
//        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
        return (int) amount;
    }

    public int getAmountWithoutRushItems(){
        int remainAmount = getAmount();
        for(Object object : lstOrderMedia){
            OrderMedia om = (OrderMedia) object;
            if(om.isRushDelivery()){
                remainAmount -= om.getQuantity() * om.getPrice();
            }
        }
        return remainAmount;
    }

    public void setRushShippingFees(int rushShippingFees) {
        this.rushShippingFees = rushShippingFees;
    }
    public int getRushShippingFees(){
        return rushShippingFees;
    }

    public int getAmountWithVat(){
        int amount = getAmount();
        return (int)(amount + (Configs.PERCENT_VAT / 100) * amount);
    }
}
