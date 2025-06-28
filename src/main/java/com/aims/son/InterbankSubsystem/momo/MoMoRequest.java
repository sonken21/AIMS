package com.aims.son.InterbankSubsystem.momo;

import com.aims.son.exception.PaymentException;
import com.aims.son.exception.UnrecognizedException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MoMoRequest {
    int amount;
    String orderInfo;

    MoMoRequest(int amount, String orderInfo){
        this.amount = amount;
        this.orderInfo = orderInfo;
    }

    public String generatePaymentURL() throws PaymentException, UnrecognizedException {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("partnerCode", MoMoConfig.MOMO_PARTNER_CODE);
            params.put("accessKey", MoMoConfig.MOMO_ACCESS_KEY);
            params.put("requestId", UUID.randomUUID().toString());
            params.put("amount", String.valueOf(amount));
            params.put("orderId", UUID.randomUUID().toString());
            params.put("orderInfo", orderInfo);
            params.put("returnUrl", MoMoConfig.MOMO_RETURN_URL);
            params.put("notifyUrl", MoMoConfig.MOMO_RETURN_URL);
            params.put("requestType", "captureWallet");
            params.put("signature", createSignature(params));

            StringBuilder query = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!query.isEmpty()) {
                    query.append("&");
                }
                query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                query.append("=");
                query.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            }

            return MoMoConfig.MOMO_PAY_URL + "?" + query;
        } catch (Exception e) {
            throw new PaymentException("Failed to generate MoMo payment URL" + e);
        }
    }

    private String createSignature(Map<String, String> params) {
        StringBuilder data = new StringBuilder();
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> data.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        data.append("secretKey=").append(MoMoConfig.MOMO_SECRET_KEY);

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create MoMo signature", e);
        }
    }
}
