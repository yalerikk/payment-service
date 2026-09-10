package dev.yalerikk.paymentservice.domain;

import dev.yalerikk.paymentservice.api.dto.CreatePaymentRequest;
import dev.yalerikk.paymentservice.api.dto.PaymentDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("10000.00");

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
    }

    @Transactional
    public PaymentDto createPayment(CreatePaymentRequest request) {
        // TODO: валидация суммы
        PaymentEntity payment = new PaymentEntity(request.userId(), request.amount(), PaymentStatus.NEW);
        PaymentEntity saved = paymentRepository.save(payment);
        LOG.info("Payment created: id={}", saved.getId());
        return mapper.toDomain(saved);
    }

    public PaymentDto getPayment(Long id) {
        PaymentEntity payment = findPaymentOrThrow(id);
        return mapper.toDomain(payment);
    }

    @Transactional
    public PaymentDto confirmPayment(Long id) {
        PaymentEntity payment = findPaymentOrThrow(id);
        // TODO: проверка статуса NEW
        payment.setStatus(PaymentStatus.SUCCEEDED);
        PaymentEntity saved = paymentRepository.save(payment);
        LOG.info("Payment has been confirmed: id={}", id);
        return mapper.toDomain(saved);
    }

    private PaymentEntity findPaymentOrThrow(Long id) {
        // TODO: выкидывать 404 ошибку
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with id=" + id + " not found"));
    }
}
