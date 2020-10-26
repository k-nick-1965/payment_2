package ibfront.controllers;

import exchange.*;
import ibfront.services.IBClient;
import ibfront.forms.*;
import ibfront.inputs.PaymentCurrency;
import ibfront.inputs.PaymentSumma;
import ibfront.inputs.PhoneNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainController {
    private IBClient ibclient = new IBClient();
    private ArrayList<String> accounts;
    private String clientNumber;

    public MainController() throws IOException {
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showLogin(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "/index";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public String authentic(Model model, @ModelAttribute("loginForm") LoginForm loginForm) throws IOException {
        ClientAuthenticPack clNumPack = new ClientAuthenticPack(loginForm.getClientNumber(), loginForm.getPassWord());
        ServerAccntPack serverAccntPack = ibclient.giveAccountsFromServer(clNumPack);
        if (serverAccntPack.getCode()==ExchangeResult.OK) {
            accounts = serverAccntPack.getClientAccounts();
            clientNumber = serverAccntPack.getClientNumber();
            return "redirect:/phonepay";
        } else {
            model.addAttribute("errorMessage", serverAccntPack.getHint());
            return "/index";
        }
    }

    @ModelAttribute("allAccounts")
    public ArrayList<String> allAccounts() {
        ArrayList<String> accntList= accounts;
        return accntList;
    }

    @RequestMapping(value = {"/phonepay"}, method = RequestMethod.GET)
    public String givePay(Model model) {
        PayForm payForm = new PayForm();
        payForm.setAccounts(accounts);
        payForm.setCurrency("810");
        model.addAttribute("payForm", payForm);

        return "/phonepay";
    }

    @RequestMapping(value = {"/phonepay"}, method = RequestMethod.POST)
    public String sendPay(Model model, @ModelAttribute("payForm") PayForm payForm) throws IOException {
        PaymentSumma paymentSumma = new PaymentSumma(payForm.getSumma());
        PaymentCurrency paymentCurrency = new PaymentCurrency(payForm.getCurrency());
        PhoneNumber phoneNumber = new PhoneNumber(payForm.getPhoneNumber());

        if (!paymentSumma.check()) {
            model.addAttribute("errorMessage", "Некорректное значение суммы.");
            return "/phonepay";
        }
        if (!paymentCurrency.check()) {
            model.addAttribute("errorMessage", "Некорректное значение валюты.");
            return "/phonepay";
        }
        if (!phoneNumber.check()) {
            model.addAttribute("errorMessage", "Некорректный номер телефона.");
            return "/phonepay";
        }

        ClientPaymentPack payPack = new ClientPaymentPack( clientNumber+"",
                payForm.getAccount(),
                paymentSumma.conversion(),
                paymentCurrency.conversion(),
                phoneNumber.conversion());

        ServerResultPack serverResultPack = ibclient.sendPaymentToServer(payPack);

        model.addAttribute("errorMessage", serverResultPack.getHint());
        return "/phonepay";
    }



    @GetMapping("/result")
    public String about() {
        return "/result";
    }


}

