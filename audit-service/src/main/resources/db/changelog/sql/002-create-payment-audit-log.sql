CREATE TABLE payment_audit_log
(
    id          BIGSERIAL PRIMARY KEY,
    event_id    UUID           NOT NULL,
    payment_id  BIGINT         NOT NULL,
    event_type  VARCHAR(32)    NOT NULL,
    status      VARCHAR(32)    NOT NULL,
    amount      NUMERIC(19, 2) NOT NULL,
    occurred_at TIMESTAMP      NOT NULL,
    received_at TIMESTAMP      NOT NULL DEFAULT now()
);

CREATE INDEX idx_audit_payment_id ON payment_audit_log (payment_id);