package ibback.clientsbase;

import lombok.Getter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Getter
public class ClientItem {
    private String clientNumber;
    private String passWordHash;
    private FullName fullName;

    public ClientItem(String clientNumber, String passWord, FullName fullName) {
        this.clientNumber = clientNumber;
        this.passWordHash = makePassWordHash(clientNumber, passWord);
        this.fullName = fullName;
    }

    static String makePassWordHash (String clientNumber, String passWord) {
        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        // добавляем соль - clientNumber
        digester.update(clientNumber.getBytes());
        // хэшируем
        byte[] passWordHashByte = digester.digest(passWord.getBytes());
        // преобразуем хэш в строку шестнадцатиричных символов длиной 32 символа
        String passWordHash = "";
        for (int i = 0; i < passWordHashByte.length; i++) {
            String s = Integer.toHexString(0xFF & passWordHashByte[i]);
            passWordHash = passWordHash + ((s.length() == 1) ? "0" + s : s);
        }
        return passWordHash;
    }

        @Override
    public String toString() {
        return clientNumber + passWordHash + fullName.toString();
    }
}

