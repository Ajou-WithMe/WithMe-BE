package ajou.withme.main.Repository;

import ajou.withme.main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUid(String uid);

    @Query("select o from User o where o.name=?1 and  o.phone=?2")
    User findByNamePhone(String name, String phone);
}
