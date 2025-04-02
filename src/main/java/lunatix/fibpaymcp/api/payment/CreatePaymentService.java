package lunatix.fibpaymcp.api.payment;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CreatePaymentService {
    private final RestClient authroizedFibRestClient;

    public CreatePaymentService(RestClient authroizedFibRestClient) {
        this.authroizedFibRestClient = authroizedFibRestClient;
    }

    public CreatePaymentResponse execute(CreatePaymentRequest request) {
        return authroizedFibRestClient.post()
                .uri(PaymentEndpoint.CREATE_PAYMENT.getPaymentUrlPath())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        (request1, response) -> {
                            throw new IllegalArgumentException(String.format("Error %s, with status code %s", response.getBody(), response.getStatusCode()));
                        })
                .toEntity(CreatePaymentResponse.class)
                .getBody();
    }

    public record CreatePaymentRequest(
            MonetaryValue monetaryValue,
            URI statusCallbackUrl,
            String description,
            Duration expiresIn,
            PaymentCategory category,
            Duration refundableFor
    ) {
    }

    public record CreatePaymentResponse(
            UUID paymentId,
            String readableCode,
            String qrCode,
            URI personalAppLink,
            URI businessAppLink,
            URI corporateAppLink,
            Instant validUntil
    ) {
    }
}
