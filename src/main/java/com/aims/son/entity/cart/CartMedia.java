package com.aims.son.entity.cart;

import com.aims.son.entity.media.Media;

public class CartMedia {

    private Media media;
    private int quantity;
    private int price;

    // Constructor với tất cả các tham số cần thiết
    public CartMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter và Setter cho các thuộc tính
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Phương thức toString để hiển thị thông tin CartMedia
    @Override
    public String toString() {
        return String.format("CartMedia{media=%s, quantity=%d, price=%d}", media, quantity, price);
    }
}