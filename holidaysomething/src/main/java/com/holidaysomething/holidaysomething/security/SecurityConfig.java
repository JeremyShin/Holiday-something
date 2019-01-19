package com.holidaysomething.holidaysomething.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author choijaeyong on 18/01/2019.
 * @project holidaysomething
 * @description
 */

//@EnableAutoConfiguration
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  //private final UserDetailsService userDetailsService;

//  private final BCryptPasswordEncoder passwordEncoder;

//  @Bean
//  public static PasswordEncoder passwordEncoder() {
//    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  ;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return new BCryptPasswordEncoder();
  }

  /*
    인증에 대한 처리를 아예 무시할 경로를 설정.
    ex> http://localhost:8080/logo.gif
    AntPathRequestMatcher : ant 문법으로 path를 지정. ant :빌드도구
    /css/** , /js/**, /images/**, /webjars/**, ** /favicon.ico
     */

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(
            "/resoures/**",
            "/static/**", "/css/**", "/js/**", "/img/**", "/webjars/**");
  }

  /*
    http://localhost:8080/logout - 로그아웃처리
    http://localhost:8080/ - 모두 접근가능
    http://localhost:8080/admin/** - admin권한 사용자만 접근 가능.
    http://localhost:8080/members/login - 아무나 접근할 수 있다.
    http://localhost:8080/admin/** - member권한 사용자만 접근 가능
    GET http://localhost:8080/members/login - 로그인 화면
     */

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    log.info("=================== 성공 ");

    http
        .authorizeRequests()
        .antMatchers("/**").permitAll()
//        .antMatchers("/user/add").permitAll()
//        .antMatchers("/user/login").permitAll()
//        .antMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().fullyAuthenticated();
        //.antMatchers("/members/**").hasRole("USER") // 로그인시 마이페이지 같은거 접근할때

    http
        .formLogin()
        .loginPage("/user/login")
        .loginProcessingUrl("/user/authenticate")
        .usernameParameter("loginId").passwordParameter("password")
        .defaultSuccessUrl("/user/info");
//        .failureUrl("/user/login.html?error=true");

    http
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutSuccessUrl("/")
        .permitAll();

    log.info("======= 끝");

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    log.info("======= AuthenticationManagerBuilder");
    auth
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }

//  @Bean
//  public DaoAuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//    authenticationProvider.setUserDetailsService(userDetailsService);
//    authenticationProvider.setPasswordEncoder(passwordEncoder());
//    return authenticationProvider;
//  }
}
