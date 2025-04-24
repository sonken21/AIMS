package com.aims.group19.service;

import com.aims.group19.entity.cart.Cart;
import com.aims.group19.entity.cart.CartMedia;
import com.aims.group19.exception.MediaNotAvailableException;

import java.sql.SQLException;

/**
 * This class handles inventory-related checks for the application.
 */
public class CartService {

    /**
     * Validates the availability of all products in the cart.
     *
     * @param cart The Cart object to check.
     * @throws MediaNotAvailableException If any product is unavailable.
     * @throws SQLException If a database error occurs.
     */
    public void validateCartInventory(Cart cart) throws SQLException {
        for (CartMedia cartMedia : cart.getListMedia()) {
            int requiredQuantity = cartMedia.getQuantity(); // Quantity requested
            int availableQuantity = cartMedia.getMedia().getQuantity(); // Quantity in stock

            // Check if required quantity exceeds available quantity
            if (requiredQuantity > availableQuantity) {
                throw new MediaNotAvailableException(
                        "Product " + cartMedia.getMedia().getTitle() + " is not available in the required quantity."
                );
            }
        }
    }
}
