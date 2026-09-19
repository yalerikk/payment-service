package dev.yalerikk.audit_service.kafka;

import dev.yalerikk.audit_service.domain.db.PaymentAuditLogEntity;
import dev.yalerikk.audit_service.domain.db.PaymentAuditLogRepository;
import dev.yalerikk.audit_service.domain.db.ProcessedEventRepository;
import dev.yalerikk.common.events.PaymentEvent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentAuditListener {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentAuditListener.class);

    private final ProcessedEventRepository processedEventRepository;
    private final PaymentAuditLogRepository auditLogRepository;

    public PaymentAuditListener(ProcessedEventRepository processedEventRepository, PaymentAuditLogRepository auditLogRepository) {
        this.processedEventRepository = processedEventRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @KafkaListener(topics = "${app.kafka.topics.payments}", groupId = "payment-service-audit")
    @Transactional
    public void handle(PaymentEvent event) {
        int inserted = processedEventRepository.insertIfNotExists(event.eventId());

        if (inserted == 0) {
            LOG.info("Event {} already processed, skipping", event.eventId());
            return;
        }

        PaymentAuditLogEntity log = new PaymentAuditLogEntity(
                event.eventId(),
                event.paymentId(),
                event.type().name(),
                event.status(),
                event.amount(),
                event.occurredAt(),
                LocalDateTime.now()
        );
        auditLogRepository.save(log);

        LOG.info("AUDIT: received event {} for payment {} with status {}",
                event.type(), event.paymentId(), event.status());
    }
}
