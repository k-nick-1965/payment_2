package ibback.services;

import exchange.*;
import ibback.clientsbase.AccountCheckException;
import ibback.clientsbase.AccountItem;
import ibback.clientsbase.AccountItems;
import ibback.clientsbase.ClientItems;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class IBServerService {
    private static HashSet<Long> usedUNs = new HashSet<>();  // использованные уникальные номера

    public ServerAccntPack performAuthentication (ClientAuthenticPack clientAuthenticPack) throws IOException {
        // проверка наличия клиента по его номеру
        // возврат списка счетов клиента
        Optional<Integer> optClntID;
        Optional<ArrayList<String>> optAcnts;

        // проверка уникального номера
        if  (usedUNs.contains(clientAuthenticPack.viewUniqueNumber())) { // такой ID уже был. Это ошибка
            return new ServerAccntPack("", ExchangeResult.PACK_DUPLICATE_ERROR, "Ошибка. Повтор пакета.");
        }
        else usedUNs.add(clientAuthenticPack.viewUniqueNumber()); // такого ID не было, добавляем ID пакета в Set
        // проводим аутентификацию по номеру клиента и паролю - при положительном результате возвращается ID клиента
        if (!((optClntID = ClientItems.authentication(clientAuthenticPack.getClientNumber(), clientAuthenticPack.getPassWord())).isPresent())) {
            return new ServerAccntPack("", ExchangeResult.AUTHENTICATION_ERROR, "Ошибка аутентификации (Неверный идентификатор или пароль).");
        }
        // По ID клиента пытаемся получить список счетов клиента
        if (!(( optAcnts = AccountItems.giveClientAccounts(optClntID.get())).isPresent())) {
            return new ServerAccntPack("",ExchangeResult.ACCOUNTS_MISSING,"Ошибка. У клиента отсутствуют счета.");
        }
        // Выгружаем список счетов в пакет
        if (optAcnts.get().isEmpty()) {
            return new ServerAccntPack("", ExchangeResult.ACCOUNTS_MISSING,"Ошибка. У клиента отсутствуют счета.");
        }
        return new ServerAccntPack(clientAuthenticPack.getClientNumber(), optAcnts.get());
    }


    public ServerResultPack performPayment(ClientPaymentPack clientPaymentPack) throws IOException {
        // проверка реквизитов платежа
        // возврат подтверждения/ошибки клиенту
        AccountItem accntItem = null;

        // проверка уникального номера
        if  (usedUNs.contains(clientPaymentPack.viewUniqueNumber())) { // такой ID уже был. Это ошибка
            return new ServerResultPack("", ExchangeResult.PACK_DUPLICATE_ERROR, "Ошибка. Повтор пакета.");
        }
        else usedUNs.add(clientPaymentPack.viewUniqueNumber()); // такого ID не было, добавляем ID пакета в Set
         // для проверки реквизитов платежа нужно получить реквизиты счета
        try {
            accntItem = AccountItems.giveAccountItem(clientPaymentPack.getAccount()).orElseThrow(() -> new AccountCheckException( "Ошибка при обращении к счету.", ExchangeResult.ACCOUNT_ERROR));
        } catch (AccountCheckException ace) {
            return new ServerResultPack("",ace.getCode(),ace.getMessage());
        }
        // проверяем реквизиты платежа (наличие клиента, принадлежность счета, остаток и валюту)
        try {
            accntItem.checkOwner(clientPaymentPack.getClientNumber())
                    .checkCurrency(clientPaymentPack.getCurrency())
                    .checkBalance(clientPaymentPack.getSumma());
        } catch (AccountCheckException ace) {
            return new ServerResultPack("",ace.getCode(),ace.getMessage());
        }

        // Отправка платежа получателю
        sendPaymetToTheMoon(clientPaymentPack);

        // Инфорирование клиента об исполнении платежа
        return (new ServerResultPack(clientPaymentPack.getClientNumber(),ExchangeResult.OK,"Платеж отправлен. Надейтесь на лучшее."));
    }

    public void sendPaymetToTheMoon(ClientPaymentPack clientPaymentPack) {}

}
