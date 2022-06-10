package fw.authservice.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandIdWrapper {
    private List<Long> brandIds;
}
