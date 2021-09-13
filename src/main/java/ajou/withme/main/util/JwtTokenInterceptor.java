package ajou.withme.main.util;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.domain.Auth;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public JwtTokenInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String accessToken = request.getHeader("AccessToken");
        String refreshToken = request.getHeader("RefreshToken");

        if (accessToken != null) {
            if (authService.isValidToken(accessToken)) {
                return true;
            } else {
                String uid = authService.getExpiredSubject(accessToken);
                Auth auth = authService.findAuthByRefreshToken(refreshToken);

                if (uid.equals(authService.getSubject(authService.getClaimsByToken(auth.getRefreshToken())))) {
                    String newAccessToken = authService.createToken(uid, (long) (2 * 60 * 1000));
                    String newRefreshToken = authService.createToken(uid, (long) (2 * 24 * 60 * 60 * 1000));

                    authService.deleteAuthByRefreshToken(refreshToken);
                    Auth newAuth = authService.createAuth(newRefreshToken, auth.getUser());
                    authService.saveAuth(newAuth);

                    System.out.println("14");

                    response.setHeader("AccessToken", newAccessToken);
                    response.setHeader("RefreshToken", newRefreshToken);
                }
                System.out.println("15");

                return true;
            }
        } else {
            response.setContentType("application/json");
            response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
            return false;
        }
    }
}
