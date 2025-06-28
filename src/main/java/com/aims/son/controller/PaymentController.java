package com.aims.son.controller;

import com.aims.son.InterbankSubsystem.IPayment;
import com.aims.son.entity.cart.Cart;
import com.aims.son.entity.invoice.Invoice;
import com.aims.son.entity.order.Order;
import com.aims.son.entity.payment.PaymentTransaction;
import com.aims.son.listener.TransactionResultListener;
import com.aims.son.repositories.OrderRepository;
import com.aims.son.repositories.TransactionRepository;

import java.io.IOException;
import java.sql.SQLException;


/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 *
 */
public class PaymentController extends BaseController implements TransactionResultListener {

	private final IPayment paymentService;
	private final TransactionRepository transactionRepository;
	private final OrderRepository orderRepository;

	public PaymentController(IPayment paymentService, TransactionRepository transactionRepository, OrderRepository orderRepository) throws SQLException {
		this.paymentService = paymentService;
		this.transactionRepository = transactionRepository;
		this.orderRepository = orderRepository;
	}

	public String payOrder(String orderInfo, Invoice invoice) throws IOException {
		// Bắt đầu quy trình thanh toán
		return paymentService.payOrder(orderInfo, this, invoice);
	}

	@Override
	public void onTransactionCompleted(PaymentTransaction transactionResult, Order order) {
		if (transactionResult != null && transactionResult.isSuccess()) {
			try {
				orderRepository.save(order);
				transactionResult.setOrderID(orderRepository.getLatestOrderId());
				transactionRepository.save(transactionResult);// Lưu giao dịch vào cơ sở dữ liệu nếu thành công
				emptyCart(); // Làm trống giỏ hàng
				System.out.println("Lưu thành công");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Giao dịch thất bại: " + (transactionResult != null ? transactionResult.getMessage() : "Lỗi không xác định"));
		}
	}

	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}
