package lunatix.fibpaymcp;

import java.net.URI;

import lunatix.fibpaymcp.api.payment.CreatePaymentService;
import lunatix.fibpaymcp.api.payment.Currency;
import lunatix.fibpaymcp.api.payment.MonetaryValue;
import lunatix.fibpaymcp.api.payment.PaymentCategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class check implements CommandLineRunner {
    private final CreatePaymentService createPaymentService;

    public check(CreatePaymentService createPaymentService) {
        this.createPaymentService = createPaymentService;
    }

    @Override
    public void run(String... args) throws Exception {
//        final var payment = createPaymentService.execute(new CreatePaymentService.CreatePaymentRequest(
//                new MonetaryValue(100, Currency.IQD),
//                URI.create("https://localhost"),
//                "dsadsada",
//                null,
//                PaymentCategory.ECOMMERCE,
//                null
//        ));
//        System.out.println(payment.paymentId());
    }
}
