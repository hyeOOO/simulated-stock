package toyproject.simulated_stock.api.member.exception;


import toyproject.simulated_stock.api.exception.CustomException;
import toyproject.simulated_stock.api.exception.ErrorCode;

public class MemberException extends CustomException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
