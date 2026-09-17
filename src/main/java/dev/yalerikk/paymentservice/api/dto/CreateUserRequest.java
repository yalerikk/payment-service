package dev.yalerikk.paymentservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @Schema(description = "users mail", example = "user@mail.ru")
        @NotBlank
        @Email
        String email
) {
}
