package toyproject.simulated_stock.api.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberEditRequest(
        @NotBlank String name
) {
}
