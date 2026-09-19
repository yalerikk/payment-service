package dev.yalerikk.audit_service.domain.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_audit_log")
@Getter
@Setter
public class PaymentAuditLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "event_type", nullable = false, length = 32)
    private String eventType;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @CreationTimestamp
    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    public PaymentAuditLogEntity() {}

    public PaymentAuditLogEntity(UUID eventId, Long paymentId, String eventType,
                                 String status, BigDecimal amount,
                                 LocalDateTime occurredAt, LocalDateTime receivedAt) {
        this.eventId = eventId;
        this.paymentId = paymentId;
        this.eventType = eventType;
        this.status = status;
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.receivedAt = receivedAt;
    }
}
