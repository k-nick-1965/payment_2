package ibback.clientsbase;

import lombok.Getter;

import java.util.Optional;

import static exchange.ExchangeResult.*;

@Getter
public class AccountItem {
    private Integer clientID = 0;
    private String number = "";
    private Long balance = 0L;

    public AccountItem(Integer clientID, String number, Long balance) {
        this.clientID = clientID;
        this.number = number;
        this.balance = balance;
    }

    public AccountItem checkCurrency(Integer currency) throws AccountCheckException {
        // Проверяем, что валюта соответствует валюте счета.
        if (Integer.parseInt(this.number.substring(5,8)) != currency) throw new AccountCheckException("Код валюты не соответствует валюте счета.",INVALID_CURRENCY);
        return this;
    }

    public AccountItem checkBalance(Long summa) throws AccountCheckException {
        // Проверяем, что на счете имеется требуемая сумма
        if (this.balance < summa) throw new AccountCheckException("Недостаточно средств на счете.",LOW_FUNDS);
        return this;
    }

    public AccountItem checkOwner(Integer clientID) throws AccountCheckException {
        // Проверяем, что счет действительно принадлежит клиенту
        if (!this.clientID.equals(clientID)) throw new AccountCheckException("Ошибка принадлежности счета.",ACCOUNT_OWNER_ERROR);
        return this;
    }

    public AccountItem checkOwner(String clientNumber) throws AccountCheckException {
        // Проверяем, что счет действительно принадлежит клиенту
        Optional<Integer> clntID = ClientItems.giveClientID(clientNumber);
        if (!this.clientID.equals(clntID.orElseThrow(() -> new AccountCheckException("Ошибка. Отсутствующий номер клиента.", MISSING_USER_NUMBER))))
            throw new AccountCheckException("Ошибка принадлежности счета.",ACCOUNT_OWNER_ERROR);
        return this;
    }

    @Override
    public String toString() {
        return "" + clientID + number + balance;
    }

}
