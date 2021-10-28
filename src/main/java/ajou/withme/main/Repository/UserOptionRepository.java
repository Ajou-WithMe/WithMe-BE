package ajou.withme.main.Repository;

import ajou.withme.main.domain.User;
import ajou.withme.main.domain.UserOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserOptionRepository extends JpaRepository<UserOption, Long> {

    @Query(value = "select s from UserOption s where s.user.id = ?1 ")
    UserOption findByUserId(Long id);

    UserOption findByUser(User userByUid);
}
