package toyproject.simulated_stock.api.exception;

import lombok.Getter;

public enum ExceptionCode {
    // 400 BAD REQUEST 잘못된 요청
    NOT_ENOUGH_STOCK(400, "Your total stock holdings are not enough"),
    NOT_ENOUGH_MONEY(400, "Your money is not enough"),

    // 401 UNAUTHORIZED 인증되지 않음
    LOGIN_REQUIRED(401, "You need to login"),
    INVALID_TOKEN(401, "Your access-token is invalid"),

    // 404 NOT FOUND 찾을 수 없음
    MEMBER_NOT_FOUND(404, "Member not found"),
    HAVE_NO_STOCK(404, "You don't have this stock."),
    CANNOT_FOUND_STOCK_DATA(404, "No data in database"),

    // 409 CONFLICT 중복된 리소스
    MEMBER_EXISTS(409, "Member exists"),
    SIGNUP_EXISTS_EMAIL(409, "This is a registered email"),
    SIGNUP_EXISTS_NICKNAME(409, "This is a registered nickname"),

    // 503 SERVICE_UNAVAILABLE 서버 과부하
    UNABLE_TO_REQUEST_AGAIN(503, "Your requests are exceeded");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
