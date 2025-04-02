package lunatix.fibpaymcp.api.payment;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CheckPaymentService {

    private final RestClient authroizedFibRestClient;

    public CheckPaymentService(RestClient authroizedFibRestClient) {
        this.authroizedFibRestClient = authroizedFibRestClient;
    }

    public CheckPaymentStatusResponse execute(CheckPaymentStatusRequest request) {
        return authroizedFibRestClient.get()
                .uri(String.format(PaymentEndpoint.CHECK_PAYMENT_STATUS.getPaymentUrlPath(), request.paymentId))
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        (request1, response) -> {
                            throw new IllegalArgumentException(String.format("Error %s, with status code %s", response.getBody(), response.getStatusCode()));
                        })
                .toEntity(CheckPaymentStatusResponse.class)
                .getBody();
    }

    public record CheckPaymentStatusRequest(
            UUID paymentId
    ) {}

    public record CheckPaymentStatusResponse(
            UUID paymentId,
            PaymentStatus status,
            Instant paidAt,
            MonetaryValue amount,
            DecliningReason decliningReason,
            Instant declinedAt,
            PaidByResponse paidBy
    ) {}

    public record PaidByResponse(
            String name,
            String iban
    ) {}
}
