package ibback.clientsbase;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.security.*;

import static ibback.clientsbase.ClientItem.makePassWordHash;

public class ClientItems {
    // Это должна быть таблица с полями ID, ClientNumber, PassWordHash, Lastname, Firstname, Middlename;
    private static final Map<Integer, ClientItem> clientTable = new HashMap<>();
    private static Integer clientTableID = 0;

    static {  // Помню, что это нехорошо, но, т.к. это эмуляция БД, которую не требуется заполнять, то пойдет.
        clientTable.put(++clientTableID, new ClientItem("4545", "4545", new FullName("Петров", "Петр", "Петрович")));
        clientTable.put(++clientTableID, new ClientItem("5656", "5656", new FullName("Иванов", "Иван", "Иванович")));
        clientTable.put(++clientTableID, new ClientItem("0000", "0001", new FullName("Хунта", "Кристобаль", "Хозевич")));
    }

    public static Optional<ClientItem> giveClient(Integer clientID) {
        if (clientTable.containsKey(clientID)) {return Optional.of(clientTable.get(clientID));}
        else {return Optional.empty();}
    }

    public static Optional<ClientItem> giveClient(String clientNumber) {
        return clientTable.values().stream()
                .filter(x -> x.getClientNumber().equals(clientNumber))
                .findFirst();
    }

    public static Optional<Integer> giveClientID(String clientNumber) {
        return clientTable.entrySet().stream()
                .filter(x -> x.getValue().getClientNumber().equals(clientNumber))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public static Optional<Integer> authentication (String clientNumber, String passWord) {
        // сравниваем полученный и хранящийся хэши
        Optional<ClientItem> opt;
        if (!(opt=giveClient(clientNumber)).isPresent()) return Optional.empty();
        else if (!opt.get().getPassWordHash().equals(makePassWordHash(clientNumber, passWord))) return Optional.empty();
        else return giveClientID(clientNumber);
    }

}
