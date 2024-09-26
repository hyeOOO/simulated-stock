package toyproject.simulated_stock.api.auth.exception;


import toyproject.simulated_stock.api.exception.CustomException;
import toyproject.simulated_stock.api.exception.ErrorCode;

public class TokenException extends CustomException {
    public TokenException(ErrorCode errorCode){
        super(errorCode);
    }
}

