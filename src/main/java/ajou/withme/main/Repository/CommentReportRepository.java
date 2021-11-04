package ajou.withme.main.Repository;

import ajou.withme.main.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}
