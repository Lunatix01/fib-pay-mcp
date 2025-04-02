package lunatix.fibpaymcp.mcp;

import lunatix.fibpaymcp.api.payment.CancelPaymentService;
import lunatix.fibpaymcp.api.payment.CheckPaymentService;
import lunatix.fibpaymcp.api.payment.CreatePaymentService;
import lunatix.fibpaymcp.api.payment.RefundPaymentService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class PaymentToolsService {

    private final CreatePaymentService createPaymentService;
    private final CancelPaymentService cancelPaymentService;
    private final CheckPaymentService checkPaymentService;
    private final RefundPaymentService refundPaymentService;

    public PaymentToolsService(CreatePaymentService createPaymentService,
                               CancelPaymentService cancelPaymentService,
                               CheckPaymentService checkPaymentService,
                               RefundPaymentService refundPaymentService) {
        this.createPaymentService = createPaymentService;
        this.cancelPaymentService = cancelPaymentService;
        this.checkPaymentService = checkPaymentService;
        this.refundPaymentService = refundPaymentService;
    }

    @Tool(name = "payment", description = "should create payment")
    public CreatePaymentService.CreatePaymentResponse createPayment(CreatePaymentService.CreatePaymentRequest request) {
        return createPaymentService.execute(request);
    }

    @Tool(name = "cancelPayment", description = "Should cancel a payment")
    public Void cancelPayment(CancelPaymentService.CancelPaymentRequest request) {
        return cancelPaymentService.execute(request);
    }

    @Tool(name = "refundPayment", description = "Should refund a payment by providing it's id")
    public Void refundPayment(RefundPaymentService.RefundPaymentRequest request) {
        return refundPaymentService.execute(request);
    }

    @Tool(name = "checkPayment", description = "Should Check a payment by providing it's id")
    public CheckPaymentService.CheckPaymentStatusResponse checkPayment(CheckPaymentService.CheckPaymentStatusRequest request) {
        return checkPaymentService.execute(request);
    }
}
