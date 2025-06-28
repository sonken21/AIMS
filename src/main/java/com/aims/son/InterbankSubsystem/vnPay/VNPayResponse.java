package com.aims.son.InterbankSubsystem.vnPay;

import com.aims.son.exception.*;

import java.util.HashMap;
import java.util.Map;

public class VNPayResponse {
    private final String vnp_BankCode;
    private final String vnp_PayDate;
    private final String vnp_TransactionNo;
    private final String vnp_TmnCode;
    private final String vnp_SecureHash;
    private final String vnp_OrderInfo;
    private final String vnp_TxnRef;
    private final String vnp_Amount;
    private final String vnp_CardType;
    private final String vnp_TransactionStatus;
    private final String vnp_BankTranNo;
    private final String vnp_ResponseCode;

    public VNPayResponse(String query) {
        Map<String, String> parseQuery = parseQueryString(query);
        this.vnp_ResponseCode = parseQuery.get("vnp_ResponseCode");
        this.vnp_TransactionStatus = parseQuery.get("vnp_TransactionStatus");
        handleException();
        this.vnp_BankCode = parseQuery.get("vnp_BankCode");
        this.vnp_PayDate = parseQuery.get("vnp_PayDate");
        this.vnp_TransactionNo = parseQuery.get("vnp_TransactionNo");
        this.vnp_TmnCode = parseQuery.get("vnp_TmnCode");
        this.vnp_SecureHash = parseQuery.get("vnp_SecureHash");
        this.vnp_OrderInfo = parseQuery.get("vnp_OrderInfo");
        this.vnp_TxnRef = parseQuery.get("vnp_TxnRef");
        this.vnp_Amount = parseQuery.get("vnp_Amount");
        this.vnp_CardType = parseQuery.get("vnp_CardType");
        this.vnp_BankTranNo = parseQuery.get("vnp_BankTranNo");
    }

    public String getVnp_BankCode() {
        return vnp_BankCode;
    }

    public String getVnp_PayDate() {
        return vnp_PayDate;
    }

    public String getVnp_TransactionNo() {
        return vnp_TransactionNo;
    }

    public String getVnp_TmnCode() {
        return vnp_TmnCode;
    }

    public String getVnp_SecureHash() {
        return vnp_SecureHash;
    }

    public String getVnp_OrderInfo() {
        return vnp_OrderInfo;
    }

    public String getVnp_TxnRef() {
        return vnp_TxnRef;
    }

    public String getVnp_Amount() {
        return vnp_Amount;
    }

    public String getVnp_CardType() {
        return vnp_CardType;
    }

    public String getVnp_TransactionStatus() {
        return vnp_TransactionStatus;
    }

    public String getVnp_BankTranNo() {
        return vnp_BankTranNo;
    }

    public String getVnp_ResponseCode() {
        return vnp_ResponseCode;
    }

    private Map<String, String> parseQueryString(String query) {
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

    private void handleException() throws TransactionNotDoneException, TransactionFailedException, TransactionReverseException, UnrecognizedException {
        switch (this.vnp_ResponseCode) {
            case "00", "24": break;
            case "02": throw new InvalidConnectionIdentifierException();
            case "03": throw new WrongDataFormatException();
            case "91": throw new RequestTransactionNotFoundException();
            case "94": throw new DuplicateRequestException();
            case "97": throw new InvalidChecksumException();
            default: throw new UnrecognizedException();
        }
//        switch (this.vnp_TransactionStatus){
//            case "00": break;
//            case "01": throw new TransactionNotDoneException();
//            case "02": throw new TransactionFailedException();
//            case "04": throw new TransactionReverseException();
//            case "05": throw new ProcessingException();
//            case "06": throw new SendToBankException();
//            case "07": throw new AnonymousTransactionException();
//            case "09": throw new RejectedTransactionException();
//            default: throw new UnrecognizedException();
//        }
    }
}
