package ibback.clientsbase;

import exchange.ExchangeResult;
import lombok.Getter;

@Getter
public class AccountCheckException extends Exception {
    ExchangeResult code;

    public AccountCheckException(String message) {
        super(message);
        code= ExchangeResult.UNKNOWN_ERROR;
    }

    public AccountCheckException(String message, ExchangeResult er) {
        super(message);
        code=er;
    }


}

