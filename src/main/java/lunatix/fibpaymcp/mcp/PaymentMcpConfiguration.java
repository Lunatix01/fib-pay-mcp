package lunatix.fibpaymcp.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaymentMcpConfiguration {

    @Bean
    public ToolCallbackProvider paymentTools(PaymentToolsService paymentToolsService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(paymentToolsService)
                .build();
    }
}
