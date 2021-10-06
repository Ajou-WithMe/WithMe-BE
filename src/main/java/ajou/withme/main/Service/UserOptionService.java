package ajou.withme.main.Service;

import ajou.withme.main.Repository.UserOptionRepository;
import ajou.withme.main.domain.UserOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOptionService {

    private final UserOptionRepository userOptionRepository;

    public UserOption saveUserOption(UserOption userOption) {
        return userOptionRepository.save(userOption);
    }
}
