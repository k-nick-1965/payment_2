package exchange;

import lombok.Getter;

@Getter
public class PackException extends Exception {
    ExchangeResult code;

//    public ContainerExeption(String message) {
//        super(message);
//        this.code=ExchangeResult.UNKNOWN_ERROR;
//    }

    public PackException(String message, ExchangeResult er) {
        super(message);
        this.code=er;
    }


}

