package ajou.withme.main.Service;

import ajou.withme.main.Repository.UserOptionRepository;
import ajou.withme.main.domain.User;
import ajou.withme.main.domain.UserOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOptionService {

    private final UserOptionRepository userOptionRepository;

    public UserOption saveUserOption(UserOption userOption) {
        return userOptionRepository.save(userOption);
    }


//    public UserOption findUserOptionByUser(Long id) {
//        return userOptionRepository.findByUserId(id);
//    }

    public UserOption findUserOptionByUser(User userByUid) {
        return userOptionRepository.findByUser(userByUid);
    }

    public List<UserOption> findAll() {
        return userOptionRepository.findAll();
    }
}
