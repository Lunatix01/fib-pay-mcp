package lunatix.fibpaymcp.api.payment;

public enum PaymentEndpoint {
    CREATE_PAYMENT("/protected/v1/payments"),
    CHECK_PAYMENT_STATUS("protected/v1/payments/%s/status"),
    CANCEL_PAYMENT("protected/v1/payments/%s/cancel"),
    REFUND_PAYMENT("protected/v1/payments/%s/refund");


    private final String paymentUrlPath;

    PaymentEndpoint(String paymentUrlPath) {
        this.paymentUrlPath = paymentUrlPath;
    }

    public String getPaymentUrlPath() {
        return paymentUrlPath;
    }
}
