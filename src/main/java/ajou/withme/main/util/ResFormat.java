package ajou.withme.main.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ResFormat {
    private boolean success;
    private Long status;
    private String data;
}
