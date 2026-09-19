package dev.yalerikk.audit_service.kafka;

import dev.yalerikk.audit_service.domain.db.ProcessedEventRepository;
import dev.yalerikk.common.events.PaymentEvent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentAuditListener {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentAuditListener.class);

    private final ProcessedEventRepository processedEventRepository;

    public PaymentAuditListener(ProcessedEventRepository processedEventRepository) {
        this.processedEventRepository = processedEventRepository;
    }

    @KafkaListener(topics = "${app.kafka.topics.payments}", groupId = "payment-service-audit")
    @Transactional
    public void handle(PaymentEvent event) {
        int inserted = processedEventRepository.insertIfNotExists(event.eventId());

        if (inserted == 0) {
            LOG.info("Event {} already processed, skipping", event.eventId());
            return;
        }

        LOG.info("AUDIT: received event {} for payment {} with status {}",
                event.type(), event.paymentId(), event.status());
    }
}
