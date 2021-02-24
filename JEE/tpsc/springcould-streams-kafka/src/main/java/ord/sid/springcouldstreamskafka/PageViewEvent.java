package ord.sid.springcouldstreamskafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PageViewEvent {
    private String userId,page;
    private long duration;
}
