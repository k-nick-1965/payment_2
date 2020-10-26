//package ibback.clientsbase;
//
//import lombok.*;
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Clients")
//@Getter
//@Setter
//@NoArgsConstructor
//public class ClientDBItem {
//
//    @Id
//    @Column(name = "Id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer clientID;
//
//    @Column(name = "ClientNumber")
//    private String clientNumber;
//
//    @Column(name = "PassWordHash")
//    private String passWordHash;
//
//    @Column(name = "LastName")
//    private String lastName;
//
//    @Column(name = "FirstName")
//    private String firstName;
//
//    @Column(name = "MiddleName")
//    private String middleName;
//
//}