package it.f2informatica.webapp.security;

import com.google.common.base.Optional;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.core.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private static final Logger log = Logger.getLogger(CustomAuthenticationSuccessHandler.class);

  @Autowired
  private UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
    String username = authentication.getName();
    Optional<UserModel> user = userService.findByUsername(username);
    if (user.isPresent()) {
      request.getSession(true).setAttribute("user", user.get());
      log.info("User in session: [username: " + user.get().getUsername() + ", id: " + user.get().getUserId() + "]");
    }
    super.handle(request, response, authentication);
  }

}
