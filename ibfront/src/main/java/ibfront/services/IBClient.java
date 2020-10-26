package ibfront.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static java.lang.Thread.sleep;

@Service
public class IBClient /*implements ExchWithServer*/ {
    private String addrHost;
    private Integer port;

    public IBClient() throws IOException {
        readIni();
    }

    public ServerAccntPack giveAccountsFromServer(@NotNull ClientAuthenticPack pack) throws IOException {
        // запрашиваем счета с сервера
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://" + addrHost + ":" + port + "/accounts", pack, ServerAccntPack.class);
    }

    public ServerResultPack sendPaymentToServer(@NotNull ClientPaymentPack pack) throws IOException {
        // отправляем платеж на сервер
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://" + addrHost + ":" + port + "/payment", pack, ServerResultPack.class);
    }

    private void readIni() throws IOException {
        File file = new File("PayProperties.ini");
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        this.addrHost = properties.getProperty("addrHost");
        this.port = Integer.parseInt(properties.getProperty("port"));
    }


}
