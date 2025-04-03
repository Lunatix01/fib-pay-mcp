package lunatix.fibpaymcp.api.payment;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
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
            @NotNull MonetaryValue monetaryValue,
            URI statusCallbackUrl,
            @Nullable @Size(max = 50) String description,
            @Nullable Duration expiresIn,
            @Nullable PaymentCategory category,
            @Nullable Duration refundableFor
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
