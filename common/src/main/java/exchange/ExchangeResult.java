package exchange;

public enum ExchangeResult {
    OK,
    UNKNOWN_ERROR, // Ошибка без расшифровки
    MISSING_USER_NUMBER, // "Ошибка. Отсутствующий номер клиента."
    AUTHENTICATION_ERROR, // "Ошибка аутентификации (Неверный идентификатор или пароль)."
    ACCOUNTS_MISSING, // "Ошибка. У клиента отсутствуют счета."
    ACCOUNT_ERROR, // "Ошибка при обращении к счету."
    INVALID_CURRENCY, //"Код валюты не соответствует валюте счета."
    LOW_FUNDS, // "Недостаточно средств на счете."
    ACCOUNT_OWNER_ERROR, // "Ошибка принадлежности счета"
    PACK_OPENING_ERROR, // "Ошибка открытия пакета."
    PACK_DUPLICATE_ERROR, // "Ошибка. Повтор пакета."
    INTERNAL_ERROR; //"Внутреняя ошибка приложения (не реализована услуга)."
}

//public enum ExchangeErrors {
//    OK (""),
//    MISSING_USER_NUMBER ("Ошибка. Отсутствующий номер клиента."),
//    CONTAINER_OPENIN_ERROR ("Ошибка открытия пакета."),
//    CONTAINER_DUPLICATE_ERROR ("Ошибка. Повтор пакета.");
//
//    private String hint;
//
//    ExchangeErrors(String s) {
//        this.hint=s;
//    }
//}
