package fw.authservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "user_id")
    private User user;
    private String test;

    public User getUser() {
        return user;
    }

}
