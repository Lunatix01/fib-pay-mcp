package lunatix.fibpaymcp.api.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class PaymentInterceptor implements ClientHttpRequestInterceptor {

    private final TokenService tokenService;

    public PaymentInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final var token = tokenService.getAccessToken();
        final var bearerToken = String.format("Bearer %s", token);
        request.getHeaders().add(HttpHeaders.AUTHORIZATION, bearerToken);
        return execution.execute(request, body);
    }
}
