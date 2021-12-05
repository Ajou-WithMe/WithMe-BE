package ajou.withme.main.Repository;

import ajou.withme.main.domain.Post;
import ajou.withme.main.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Slice<Post> findByLocation(String location, Pageable pageable);

    List<Post> findByGuardian(User userByUid);

    Page<Post> findByLocationAndState(String location, int state, Pageable pageable);
}
