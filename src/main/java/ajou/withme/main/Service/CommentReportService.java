package ajou.withme.main.Service;

import ajou.withme.main.Repository.CommentReportRepository;
import ajou.withme.main.domain.CommentReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;

    public CommentReport saveCommentReport(CommentReport commentReport) {
        return commentReportRepository.save(commentReport);
    }
}
