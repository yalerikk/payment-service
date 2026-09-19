package dev.yalerikk.audit_service.api;

import dev.yalerikk.audit_service.api.dto.PaymentAuditDto;
import dev.yalerikk.audit_service.domain.db.PaymentAuditLogEntity;
import dev.yalerikk.audit_service.domain.db.PaymentAuditLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final PaymentAuditLogRepository auditLogRepository;

    public AuditController(PaymentAuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping("/{paymentId}")
    public List<PaymentAuditDto> getAudit(@PathVariable Long paymentId) {
        return auditLogRepository.findByPaymentId(paymentId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PaymentAuditDto toDto(PaymentAuditLogEntity e) {
        return new PaymentAuditDto(
                e.getId(), e.getEventId(), e.getPaymentId(),
                e.getEventType(), e.getStatus(), e.getAmount(),
                e.getOccurredAt(), e.getReceivedAt()
        );
    }
}
