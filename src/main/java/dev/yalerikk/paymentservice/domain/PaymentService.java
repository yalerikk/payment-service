package dev.yalerikk.paymentservice.domain;

import dev.yalerikk.paymentservice.api.dto.CreatePaymentRequest;
import dev.yalerikk.paymentservice.api.dto.PaymentDto;
import dev.yalerikk.paymentservice.api.errors.InvalidPaymentStateException;
import dev.yalerikk.paymentservice.api.errors.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper mapper, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public PaymentDto createPayment(CreatePaymentRequest request) {
        if (!userRepository.existsById(request.userId())) {
            throw new ResourceNotFoundException("User", request.userId());
        }
        if (request.amount().compareTo(MAX_AMOUNT) > 0) {
            throw new InvalidPaymentStateException("Amount is too large");
        }
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
        if (payment.getStatus() != PaymentStatus.NEW) {
            throw new InvalidPaymentStateException("Payment status must be NEW");
        }
        payment.setStatus(PaymentStatus.SUCCEEDED);
        PaymentEntity saved = paymentRepository.save(payment);
        LOG.info("Payment has been confirmed: id={}", id);
        return mapper.toDomain(saved);
    }

    private PaymentEntity findPaymentOrThrow(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }
}
