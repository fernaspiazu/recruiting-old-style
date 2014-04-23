package it.f2informatica.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers(HttpMethod.GET, "/login*").permitAll()
      .antMatchers(HttpMethod.GET, "/static/**").access("isAnonymous() or isAuthenticated()")
      .antMatchers(HttpMethod.GET, "/**").authenticated();

    http.formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/processLogin")
      .usernameParameter("username")
      .passwordParameter("password")
      .defaultSuccessUrl("/home", true)
      .failureUrl("/login_failed");
  }
}
