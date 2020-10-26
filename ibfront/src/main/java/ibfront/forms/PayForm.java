package ibfront.forms;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class PayForm {
    private String phoneNumber;
    private ArrayList<String> accounts;
    private String account;
    private String summa;
    private String currency;

}
