package ajou.withme.main.controller.protection;

import ajou.withme.main.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protection")
@RequiredArgsConstructor
public class ProtectionController {
    private final UserService userService;




}
