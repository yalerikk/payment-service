package dev.yalerikk.audit_service.kafka;

import dev.yalerikk.common.events.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentAuditListener {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentAuditListener.class);

    @KafkaListener(topics = "${app.kafka.topics.payments}", groupId = "payment-service-audit")
    public void handle(PaymentEvent event) {
        LOG.info("AUDIT: received event {} for payment {} with status {}",
                event.type(), event.paymentId(), event.status());
    }
}
