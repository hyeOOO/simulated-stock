package toyproject.simulated_stock.api.exception;

public record ErrorResponse(
        ErrorCode errorCode,
        String message
) {
}
