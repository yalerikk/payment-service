package dev.yalerikk.paymentservice.domain;

import dev.yalerikk.paymentservice.api.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PaymentMapper mapper;

    public UserMapper(PaymentMapper mapper) {
        this.mapper = mapper;
    }

    public UserDto toDomain(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                mapper.mapListToDto(entity.getPayments())
        );
    }
}
