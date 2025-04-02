package lunatix.fibpaymcp.api.payment;

public record MonetaryValue(
        Integer amount,
        Currency currency
) {
}
