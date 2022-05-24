package fw.authservice.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "User")
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

    @NotBlank(message = "firstname must be not empty")
    private String firstName;

    @NotBlank(message = "lastname must be not empty")
    private String lastName;

   // @Column(unique = true)
    @NotBlank(message = "username must be not empty")
    private String userName;

    @NotBlank(message = "password must be not empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

   // @Column(unique = true)
    @NotBlank(message = "email must be not empty")
    @Email
    private String email;

   // @Column(unique = true)
    private Long phoneNumber;
    private String address;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date birthdate;

    private String profilePicture;
    private int rating;
    private UserType userType;
    private boolean active;
    private String roles;

}
