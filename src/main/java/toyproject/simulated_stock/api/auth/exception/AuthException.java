package toyproject.simulated_stock.api.auth.exception;

import toyproject.simulated_stock.api.exception.CustomException;
import toyproject.simulated_stock.api.exception.ErrorCode;

public class AuthException extends CustomException {
    public AuthException(ErrorCode errorCode){
        super(errorCode);
    }
}
