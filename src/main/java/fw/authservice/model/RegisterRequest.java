package fw.authservice.model;


import lombok.*;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private Long userId;
    private UserType userType;

}
