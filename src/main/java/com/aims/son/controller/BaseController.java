package com.aims.son.controller;

import com.aims.son.entity.cart.Cart;
import com.aims.son.entity.cart.CartMedia;
import com.aims.son.entity.media.Media;

import java.util.List;

/**
 * This class is the base controller for our AIMS project
 * @author nguyenlm
 */
public class BaseController {

    /**
     * The method checks whether the Media in Cart, if it were in, we will return the CartMedia else return null
     * @param media
     * @return CartMedia or null
     */
    public CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart
     * @return List[CartMedia]
     */
    @SuppressWarnings("rawtypes")
    public List getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}