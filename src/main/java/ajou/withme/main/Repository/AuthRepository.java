package ajou.withme.main.Repository;

import ajou.withme.main.domain.Auth;
import ajou.withme.main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    void deleteByUser(User user);
}
