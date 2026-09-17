package dev.yalerikk.paymentservice.domain;

import dev.yalerikk.paymentservice.api.dto.CreateUserRequest;
import dev.yalerikk.paymentservice.api.dto.UserDto;
import dev.yalerikk.paymentservice.api.errors.ResourceNotFoundException;
import dev.yalerikk.paymentservice.api.errors.UserAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        boolean exists = userRepository.existsByEmailIgnoreCase(request.email());
        if (exists) {
            throw new UserAlreadyExistsException(request.email());
        }
        UserEntity user = new UserEntity(request.email());
        UserEntity saved = userRepository.save(user);
        LOG.info("User created: id={}", saved.getId());
        return mapper.toDomain(saved);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        // TODO: N+1 problem
        return userRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getUserOrThrow(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return mapper.toDomain(user);
    }
}
