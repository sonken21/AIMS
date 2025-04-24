package com.aims.group19.controller;

import com.aims.group19.entity.cart.Cart;
import com.aims.group19.entity.cart.CartMedia;
import com.aims.group19.entity.invoice.Invoice;
import com.aims.group19.entity.order.Order;
import com.aims.group19.entity.order.OrderMedia;
import com.aims.group19.exception.InvalidDeliveryInfoException;
import com.aims.group19.shipping.calculator.IShippingCalculator;
import com.aims.group19.service.CartService;  // Đảm bảo CartService được import
import com.aims.group19.utils.Utils;
import com.aims.group19.validators.DeliveryFormValidatorContext;
import com.aims.group19.validators.IFormValidator;
import com.aims.group19.validators.ValidatorResult;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.aims.group19.utils.Configs.SPECIAL_PROVINCE;

public class PlaceOrderController extends BaseController {

    private static Logger LOGGER = Utils.getLogger(PlaceOrderController.class.getName());

    private IFormValidator deliveryFormValidator;
    private IShippingCalculator shippingCalculator;
    public PlaceOrderController(IFormValidator deliveryFormValidator, IShippingCalculator shippingCalculator) {
        this.deliveryFormValidator = deliveryFormValidator;
        this.shippingCalculator = shippingCalculator;
    }

    public void placeOrder() throws SQLException {

        CartService cartService = new CartService();
        cartService.validateCartInventory(Cart.getCart());
    }

    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    public Invoice createInvoice(Order order) {
        return new Invoice(order); // Sửa kiểu trả về thành Invoice.
    }

    public void processDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());

        ValidatorResult result = deliveryFormValidator.validate(info);
        if (!result.isSuccess()) { // Sử dụng phương thức isSuccess() để kiểm tra trạng thái
            throw new InvalidDeliveryInfoException(result.getErrorMessage()); // Truyền thông báo lỗi vào ngoại lệ nếu cần
        }
    }

    public void calculateOrderShippingFee(Order order){
        LOGGER.info("Calculate Order Shipping Fee");
        shippingCalculator.calculateShippingFee(order);
    }
}
