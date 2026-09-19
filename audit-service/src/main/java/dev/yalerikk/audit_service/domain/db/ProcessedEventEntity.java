package dev.yalerikk.audit_service.domain.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_events")
@Getter
@Setter
public class ProcessedEventEntity {
    @Id
    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @CreationTimestamp
    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    public ProcessedEventEntity() {}

    public ProcessedEventEntity(UUID eventId, LocalDateTime processedAt) {
        this.eventId = eventId;
        this.processedAt = processedAt;
    }
}
