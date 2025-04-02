package lunatix.fibpaymcp.api.auth;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class TokenService {

    private final RestClient unauthorizedFibRestClient;

    @Value("${fib.client.id}")
    private String clientId;
    @Value("${fib.client.secret}")
    private String clientSecret;

    private String accessToken;
    private Instant expiresAt;

    public TokenService(RestClient unauthorizedFibRestClient) {
        this.unauthorizedFibRestClient = unauthorizedFibRestClient;
    }

    public String getAccessToken() {
        refreshTokenIfNecessary();
        return accessToken;
    }


    private boolean isTokenExpired() {
        return accessToken == null || expiresAt.isAfter(Instant.now());
    }

    private void refreshTokenIfNecessary() {
        if (isTokenExpired()) {
            setCredentials();
        }
    }

    private ResponseBody getTokenInformation() {
        return unauthorizedFibRestClient.post()
                .uri("/auth/realms/fib-online-shop/protocol/openid-connect/token")
                .body(createRequestBody())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        (request1, response1) -> {
                            throw new IllegalArgumentException(String.format("Error %s, with status code %s", response1.getBody(), response1.getStatusCode()));
                        })
                .toEntity(ResponseBody.class)
                .getBody();
    }

    private void setCredentials() {
        final var responseBody = getTokenInformation();
        assert responseBody != null;
        this.accessToken = responseBody.accessToken;
        this.expiresAt = Instant.ofEpochMilli(responseBody.expiresIn);
    }

    public record ResponseBody(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("refresh_expires_in") int refreshExpiresIn,
            @JsonProperty("refresh_token") String refreshToken
    ) {
    }

    private MultiValueMap<String, String> createRequestBody() {
        final var bodyPair = new LinkedMultiValueMap<String, String>();
        bodyPair.add("grant_type", "client_credentials");
        bodyPair.add("client_id", this.clientId);
        bodyPair.add("client_secret", this.clientSecret);
        return bodyPair;
    }
}
