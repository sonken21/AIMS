package com.aims.son.entity.order;


import com.aims.son.entity.media.Media;

public class OrderMedia {
    
    private Media media;
    private int price;
    private int quantity;
    private boolean rushDelivery;

    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
        this.rushDelivery = false;
    }
    
    @Override
    public String toString() {
        return "{" +
            "  media='" + media + "'" +
            ", quantity='" + quantity + "'" +
            ", price='" + price + "'" +
            "}";
    }
    
    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRushDelivery(boolean rushDelivery) {this.rushDelivery = rushDelivery;}
    public boolean isRushDelivery() { return this.rushDelivery; }
}
