package lunatix.fibpaymcp.api;

import java.net.URI;

import lunatix.fibpaymcp.api.auth.PaymentInterceptor;
import lunatix.fibpaymcp.api.auth.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class RestClientConfiguration {

    @Value("${fib.base.url}")
    private URI baseUrl;

    RestClient unauthorizedFibRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    TokenService tokenService() {
        return new TokenService(unauthorizedFibRestClient());
    }

    @Bean
    RestClient authroizedFibRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor(new PaymentInterceptor(tokenService()))
                .build();
    }
}
