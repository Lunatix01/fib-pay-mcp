package lunatix.fibpaymcp.api.payment;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RefundPaymentService {
    private final RestClient authroizedFibRestClient;

    public RefundPaymentService(RestClient authroizedFibRestClient) {
        this.authroizedFibRestClient = authroizedFibRestClient;
    }

    public Void execute(RefundPaymentRequest request) {
        return authroizedFibRestClient.post()
                .uri(String.format(PaymentEndpoint.REFUND_PAYMENT.getPaymentUrlPath(), request.paymentId))
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        (request1, response) -> {
                            throw new IllegalArgumentException(String.format("Error %s, with status code %s", response.getBody(), response.getStatusCode()));
                        })
                .toBodilessEntity()
                .getBody();
    }


    public record RefundPaymentRequest(
            UUID paymentId
    ) {}
}
