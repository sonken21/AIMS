package com.aims.son.InterbankSubsystem.momo;
import java.util.*;

public class MoMoResponse {
    private final String partnerCode;
    private final String orderId;
    private final String requestId;
    private final long amount;
    private final String orderInfo;
    private final String partnerUserId;
    private final String orderType;
    private final long transId;
    private final int resultCode;
    private final String message;
    private final String payType;
    private final long responseTime;
    private final String extraData;
    private final String signature;

    MoMoResponse(String response){
        Map<String, String> params = parseQueryString(response);
        this.partnerCode = params.get("partnerCode");
        this.orderId = params.get("orderId");
        this.requestId = params.get("requestId");
        this.amount = Integer.parseInt(params.get("amount"));
        this.orderInfo = params.get("orderInfo");
        this.partnerUserId = params.get("partnerUserId");
        this.orderType = params.get("orderType");
        this.transId = Integer.parseInt(params.get("transId"));
        this.resultCode = Integer.parseInt(params.get("resultCode"));
        this.message = params.get("message");
        this.payType = params.get("payType");
        this.responseTime = Integer.parseInt(params.get("responseTime"));
        this.extraData = params.get("extraData");
        this.signature = params.get("signature");
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRequestId() {
        return requestId;
    }

    public long getAmount() {
        return amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public String getOrderType() {
        return orderType;
    }

    public long getTransId() {
        return transId;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public String getPayType() {
        return payType;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public String getExtraData() {
        return extraData;
    }

    public String getSignature() {
        return signature;
    }

    private static Map<String, String> parseQueryString(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}
