package ibback.controllers;

import ibback.services.IBServerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;

import exchange.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class IBServerController {

    public IBServerController() throws IOException {
    }

    @RequestMapping(value = {"/accounts"}, method = RequestMethod.POST)
    public ServerAccntPack getPacket(@RequestBody ClientAuthenticPack pack) throws IOException {
        IBServerService service = new IBServerService();
        return service.performAuthentication(pack);
    }

    @RequestMapping(value = {"/payment"}, method = RequestMethod.POST)
    public ServerResultPack getPacket(@RequestBody ClientPaymentPack pack) throws IOException {
        IBServerService service = new IBServerService();
        return service.performPayment(pack);
    }

//    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
//    public Pack getPacket(@RequestBody Pack pack) throws IOException {
//        IBServerService service = new IBServerService();
//
//        switch (pack.getPackType()) {
//            case AUTHENTIC:
//                return (Pack) service.performAuthentication((ClientAuthenticPack) pack);
//            case PAYMENT:
//                return (Pack) service.performPayment((ClientPaymentPack) pack);
//        }
//
//        return pack;
//    }
//

}