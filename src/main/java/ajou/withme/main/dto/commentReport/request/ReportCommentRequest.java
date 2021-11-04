package ajou.withme.main.dto.commentReport.request;

import lombok.Data;

@Data
public class ReportCommentRequest {
    Long id;
    int type;
    String msg;
}
