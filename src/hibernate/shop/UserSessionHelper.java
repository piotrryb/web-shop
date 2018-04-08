package hibernate.shop;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;

public class UserSessionHelper {
    public static User getUserFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            Optional<Cookie> emailCookie = Arrays.stream(cookies).filter(x -> x.getName().equals("email")).findFirst();
            if (emailCookie.isPresent()) {
                Optional<User> byEmail = UserRepository.findByEmail(emailCookie.get().getValue());
                return byEmail.orElse(null);
            }
        }
        return null;
    }
}
