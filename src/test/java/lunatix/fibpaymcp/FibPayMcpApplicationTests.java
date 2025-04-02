package lunatix.fibpaymcp;

import java.net.URI;

import lunatix.fibpaymcp.api.payment.CreatePaymentService;
import lunatix.fibpaymcp.api.payment.Currency;
import lunatix.fibpaymcp.api.payment.MonetaryValue;
import lunatix.fibpaymcp.api.payment.PaymentCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FibPayMcpApplicationTests {

    @Test
    void contextLoads(@Autowired CreatePaymentService createPaymentService) {
        final var payment = createPaymentService.execute(new CreatePaymentService.CreatePaymentRequest(
                new MonetaryValue(100, Currency.IQD),
                URI.create("https://localhost"),
                "dsadsada",
                null,
                PaymentCategory.ECOMMERCE,
                null
        ));
        System.out.println(payment.paymentId());
    }

}
