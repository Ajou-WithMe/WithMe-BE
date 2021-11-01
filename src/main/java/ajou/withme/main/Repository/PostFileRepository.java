package ajou.withme.main.Repository;

import ajou.withme.main.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
