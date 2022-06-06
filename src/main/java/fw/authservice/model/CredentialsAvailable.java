package fw.authservice.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsAvailable {
    private String email;
    private String userName;
}
