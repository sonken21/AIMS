package com.aims.son.controller;

import com.aims.son.entity.cart.Cart;
import com.aims.son.service.CartService;
import java.sql.SQLException;
import java.util.List;

/**
 * This class controls the flow of events when users view the Cart
 * @author nguyenlm
 */

public class ViewCartController extends BaseController {

    private final CartService cartService;  // Đổi tên biến thành cartService

    /**
     * Constructor for ViewCartController
     */
    public ViewCartController() {
        this.cartService = new CartService(); // Khởi tạo CartService thay vì InventoryChecker
    }

    /**
     * This method checks the available products in Cart
     * @throws SQLException if a database error occurs
     */

    public void checkAvailabilityOfProduct() throws SQLException {
        cartService.validateCartInventory(Cart.getCart()); // Gọi phương thức từ CartService
    }

    /**
     * This method calculates the cart subtotal
     * @return subtotal
     */

    public int getCartSubtotal() {
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }
}

