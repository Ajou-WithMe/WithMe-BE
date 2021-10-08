package ajou.withme.main.Repository;

import ajou.withme.main.domain.UserOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionRepository extends JpaRepository<UserOption, Long> {
}
