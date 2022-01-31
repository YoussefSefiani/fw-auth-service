package fw.authservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Influencer {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String ibanNumber;

    private List<SocialMedia> socialMedia;

    private String headTitle;

    private String description;

    private List<Language> languages;

    private List<Country> countries;

    private List<Sector> sectors;

    private String offers;

    private String partnerships;

    public User getUser() {
        return user;
    }
}
