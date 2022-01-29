package fw.authservice.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

   // @Column(unique = true)
    @NotBlank(message = "username must be not empty")
    private String username;

    @NotBlank(message = "password must be not empty")
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

   // @Column(unique = true)
    @NotBlank(message = "email must be not empty")
    @Email
    private String email;

   // @Column(unique = true)
    private Long phoneNumber;

    private String address;

    private LocalDate birthdate;

    //TODO: Missing types

}
