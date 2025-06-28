package com.aims.son.entity.cart;

import com.aims.son.entity.media.Media;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartMedia> lstCartMedia;
    private static Cart cartInstance;

    public static Cart getCart(){
        if(cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    private Cart(){
        lstCartMedia = new ArrayList<>();
    }

    public void addCartMedia(CartMedia cm) {
        CartMedia existingCartMedia = checkMediaInCart(cm.getMedia());
        if (existingCartMedia != null) {
            // Cập nhật số lượng cho sản phẩm đã có trong giỏ hàng
            existingCartMedia.setQuantity(existingCartMedia.getQuantity() + cm.getQuantity());
        } else {
            // Thêm sản phẩm mới vào giỏ hàng
            lstCartMedia.add(cm);
        }
    }

    public void removeCartMedia(CartMedia cm){
        lstCartMedia.remove(cm);
    }

    public List<CartMedia> getListMedia(){
        return lstCartMedia;
    }

    public void emptyCart(){
        lstCartMedia.clear();
    }

    public int getTotalMedia(){
        int total = 0;
        for (CartMedia obj : lstCartMedia) {
            total += obj.getQuantity();
        }
        return total;
    }

    public int calSubtotal(){
        int total = 0;
        for (CartMedia obj : lstCartMedia) {
            total += obj.getPrice() * obj.getQuantity();
        }
        return total;
    }

    public CartMedia checkMediaInCart(Media media){
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) return cartMedia;
        }
        return null;
    }
}
